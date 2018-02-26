
package com.jzc.spring.dubbo.module.release.es;

import com.alibaba.fastjson.JSON;
import com.jzc.spring.dubbo.entity.GenericEntity;
import com.jzc.spring.dubbo.entity.PageList;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EsClient {
	private static final Logger logger = LoggerFactory.getLogger(EsClient.class);



	private static int defaultSize = 3;
	
    /**
	 * 搜索数据,增加排序
	 * 
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param o
	 *            返回类型
	 * @param queryBuilder
	 *            查询条件
	 * @return
	 */
	public static <T> List<T> searchDocs(String index, String type, Class<T> o, QueryBuilder queryBuilder,List<FieldSortBuilder> sorts) {
		Client client = null;
		T obj = null;
		List<T> list = new ArrayList<T>();
		SearchResponse response = null;
		try {
			client = ClientHelper.getClient();
			SearchRequestBuilder searchRequest = client.prepareSearch(index).setSearchType(SearchType.DEFAULT)
					.setScroll(new TimeValue(60000)).setTypes(type);
			if (null != queryBuilder) {
				searchRequest.setQuery(queryBuilder);
				searchRequest.setSize(defaultSize);
			} else {
				return list;
			}
			for(FieldSortBuilder sort : sorts){
				searchRequest.addSort(sort);
			}
			searchRequest.setExplain(true);
			response = searchRequest.get();

			for (SearchHit hit : response.getHits().getHits()) {
				Map<String, Object> map = hit.getSource();
				map.put("id", hit.getId());
				obj = JSON.parseObject(JSON.toJSONString(map), o);
				list.add(obj);
			}
		} catch (Exception e) {
			logger.error("search es:" ,e);
		} finally {
/*			if (client != null) {
				client.close();
			}*/
		}
		return list;
	}


	/**
	 * 保存数据
	 * @param index
	 * @param type
	 * @param list
	 */
	@SuppressWarnings("deprecation")
	public static <T> void saveDocs(String index, String type, List<T> list) {
		if(null == list || list.size() == 0){
			return;
		}
		Client client = null;
		try {
			client = ClientHelper.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (T t : list) {
				bulkRequest.add(client.prepareIndex(index, type, null).setSource(JSON.toJSONString(t)));
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				throw new RuntimeException("save docs error:" + bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			logger.error("es save:" ,e);
		}finally {
/*			if (client != null){
				client.close();
			}	*/
		}
	}

	/**
	 * 修改数据
	 * @param index
	 * @param type
	 * @param list
	 */
	@SuppressWarnings("deprecation")
	public static <T> void updateDocs(String index, String type,List<T> list) {
		if(null == list || list.size() == 0){
			return;
		}
		Client client = null;
		try {
			client = ClientHelper.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (T t : list) {
				String json = JSON.toJSONString(t);
				GenericEntity entity = JSON.parseObject(json, GenericEntity.class);
				bulkRequest.add(client.prepareUpdate(index, type, entity.getKid().toString()).setDoc(json));
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				throw new RuntimeException("update docs error:" + bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			logger.error("es update:" ,e);
		}finally {
			/*if (client != null){
				client.close();
			}	*/
		}
	}

	/**
	 * 删除数据
	 * @param index
	 * @param type
	 * @param id
	 */
	public static void deleteDocs(String index, String type, String id) {
		if (StringUtils.isBlank(id))
			return;
		Client client = null;
		try {
			client = ClientHelper.getClient();
			DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
			logger.info("es delete id:" + response.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
/*			if (client != null){
				client.close();
			}	*/
		}
		if (logger.isDebugEnabled()) {
			logger.info("es delete " , id);
		}
	}

	/**
	 * 查询数据(不分页)
	 * @param index
	 * @param type
	 * @param o
	 * @param queryBuilder
	 * @return
	 */
	public static <T> List<T> searchDocs(String index, String type, Class<T> o, QueryBuilder queryBuilder) {
		Client client = null;
		T obj = null;
		List<T> list = new ArrayList<T>();
		SearchResponse response = null;
		try {
			client = ClientHelper.getClient();
			SearchRequestBuilder searchRequest = client.prepareSearch(index)
			        .setSearchType(SearchType.DEFAULT)
			        .setScroll(new  TimeValue(60000)).setTypes(type);
			if (null != queryBuilder) {
				searchRequest.setQuery(queryBuilder);
				searchRequest.setSize(defaultSize);
			}else{
				//全文匹配100条数据
				searchRequest.setSize(100);
			}

			response = searchRequest.get();
			while(true) {
			    for(SearchHit hit : response.getHits().getHits()) {
					Map<String,Object> map = hit.getSource();
					map.put("id", hit.getId());
					//client.prepareDelete(index, type, hit.getId()).get();
					obj = JSON.parseObject(JSON.toJSONString(map), o);
					list.add(obj);
			    }
			    //不是全文检索，关键字匹配
			    if (null != queryBuilder) {
					break;
				}
			    response = client.prepareSearchScroll(response.getScrollId()).setScroll(new  TimeValue(60000)).execute().actionGet();
			    if  (response.getHits().getHits().length == 0) {
			        break;
			    }
			}
		} catch (Exception e) {
			logger.error("search es:",e);
		} finally {
/*			if (client != null){
				client.close();
			}	*/
		}
		return list;
	}
	
	/**
	 * 查询所有数据(不分页)
	 * @param index
	 * @param type
	 * @param o
	 * @param queryBuilder
	 * @param flag
	 * @return
	 */
	public static <T> List<T> searchAllDocs(String index, String type, Class<T> o, QueryBuilder queryBuilder,boolean flag) {
		Client client = null;
		T obj = null;
		List<T> list = new ArrayList<T>();
		SearchResponse response = null;
		try {
			client = ClientHelper.getClient();
			SearchRequestBuilder searchRequest = client.prepareSearch(index)
			        .setSearchType(SearchType.DEFAULT)
			        .setScroll(new  TimeValue(60000)).setTypes(type);
			searchRequest.setQuery(queryBuilder);
			//全文匹配100条数据
			searchRequest.setSize(100);
			
			searchRequest.setExplain(true);
			response = searchRequest.get();
			while(true) {
			    for(SearchHit hit : response.getHits().getHits()) {
					Map<String,Object> map = hit.getSource();
					map.put("id", hit.getId());
					//client.prepareDelete(index, type, hit.getId()).get();
					obj = JSON.parseObject(JSON.toJSONString(map), o);
					list.add(obj);
			    }
			    //不是全文检索，关键字匹配
			    if (!flag) {
					break;
				}
			    response = client.prepareSearchScroll(response.getScrollId()).setScroll(new  TimeValue(60000)).execute().actionGet();
			    if  (response.getHits().getHits().length == 0) {
			        break;
			    }
			}
		} catch (Exception e) {
			logger.error("search es:",e);
		} finally {
/*			if (client != null){
				client.close();
			}	*/
		}
		return list;
	}
	
	/**
	 * 分页查询数据
	 * @param index
	 * @param type
	 * @param o
	 * @param queryBuilder
	 * @param pageNo
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	public static <T extends Object> PageList<T> searchAllDocsByPage(String index, String type, Class<T> o, QueryBuilder queryBuilder, int pageNo, int pageSize, List<FieldSortBuilder> sorts) {
		Client client = null;
		T obj = null;
		List<T> list = new ArrayList<T>();
		PageList<T> entity = new PageList<>();
		SearchResponse response = null;
		try {
			client = ClientHelper.getClient();
		    SearchRequestBuilder searchRequest = client.prepareSearch(index);
		    searchRequest.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		    searchRequest.setTypes(type);
		    searchRequest.setQuery(queryBuilder);
		    searchRequest.setFrom((pageNo - 1) * pageSize).setSize(pageSize).setExplain(true);
			//全文匹配100条数据
			//searchRequest.setSize(100);
			for(FieldSortBuilder sort : sorts){
				searchRequest.addSort(sort);
			}
			searchRequest.setExplain(true);
			response = searchRequest.get();
			
			int total = (int) response.getHits().getTotalHits();
			entity.setCount((long)total);
			for(SearchHit hit : response.getHits().getHits()) {
					Map<String,Object> map = hit.getSource();
					map.put("kid", hit.getId());
					obj = JSON.parseObject(JSON.toJSONString(map), o);
					list.add(obj);
			 }
			
		} catch (Exception e) {
			logger.error("search es:",e);
			
		} finally {
/*			if (client != null){
				client.close();
			}	*/
		}
		entity.setCurrentPage(pageNo);
		entity.setPageSize(pageSize);
		entity.setEntities(list);
		return entity;
	} 

	public static void main(String[] args) throws Exception {
		//deleteDocs("cyq","test","AVtH1m1ifOYaqlXPLzMi");
	}
	
}
