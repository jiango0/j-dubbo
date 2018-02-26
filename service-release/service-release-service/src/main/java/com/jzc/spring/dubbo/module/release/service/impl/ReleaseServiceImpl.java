package com.jzc.spring.dubbo.module.release.service.impl;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.constant.ReleaseConstant;
import com.jzc.spring.dubbo.module.release.dto.SearchDto;
import com.jzc.spring.dubbo.module.release.es.EsClient;
import com.jzc.spring.dubbo.module.release.service.ReleaseService;
import com.jzc.spring.dubbo.web.ResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    /**
     * 查询
     * */
    public <T> PageList<T> list(Class<T> t, SearchDto searchDto) {
        QueryBuilder baseQuryBuilder = getBaseQuryBuilder(searchDto);
        PageList<T> pageList =  EsClient.searchAllDocsByPage(ReleaseConstant.INDEX, ReleaseConstant.TYPE, t, baseQuryBuilder,
                searchDto.getCurrentPage(), searchDto.getPageSize(), getSortBuilder(searchDto.getSorts()));
        return pageList;
    }


    /**
     * 根据关键字搜索
     * @param searchDto
     * @return
     */
    private QueryBuilder getBaseQuryBuilder(SearchDto searchDto) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //组装必须匹配的字段
        if(null != searchDto.getMustMatchMap()){
            Map<String,Object> map = searchDto.getMustMatchMap();
            Set<Map.Entry<String,Object>> set = map.entrySet();
            for(Map.Entry<String,Object> entry : set){
                qb.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
            }
        }
        //组装模糊匹配的字段
        if (StringUtils.isNotEmpty(searchDto.getKeyWord())) {
            BoolQueryBuilder qb1 = QueryBuilders.boolQuery();
            List<String> shouldMatchValues = searchDto.getShouldMatchValues();
            for(String matchFiled : shouldMatchValues){
                qb1.should(QueryBuilders.wildcardQuery(matchFiled,"*"+searchDto.getKeyWord()+"*"));
            }
            qb.must(qb1);
        }

        return qb;
    }

    /**
     * 根据审核时间排序(倒序)
     * @return
     */
    private List<FieldSortBuilder> getSortBuilder(List<String> sorts) {
        List<FieldSortBuilder> sortBuilders = new ArrayList<>();
        if(!CollectionUtils.isEmpty(sorts)) {
            for(String sort1 : sorts){
                FieldSortBuilder sort = SortBuilders.fieldSort(sort1).order(SortOrder.DESC);
                sortBuilders.add(sort);
            }
        }

        return sortBuilders;
    }

}
