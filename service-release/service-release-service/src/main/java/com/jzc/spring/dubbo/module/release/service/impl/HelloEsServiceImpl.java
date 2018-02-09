package com.jzc.spring.dubbo.module.release.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.module.release.service.HelloEsService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Service
public class HelloEsServiceImpl implements HelloEsService {

    private static final Logger logger = LoggerFactory.getLogger(HelloEsServiceImpl.class);

    public PageList<HelloInfo> hello(Integer pageNo, Integer pageSize) {
        try {
            Settings.Builder settingsBuilder = Settings.builder();
            settingsBuilder.put("cluster.name", "mo.yryz.com");
            settingsBuilder.put("client.transport.sniff", "true");

            TransportClient client = new PreBuiltTransportClient(settingsBuilder.build())
                    .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("192.168.30.35"),9300));

            logger.debug("Elasticsearch connect info:" + client.toString());

            List<HelloInfo> list = new ArrayList<>();
            BoolQueryBuilder qb = QueryBuilders.boolQuery();

            SearchRequestBuilder search = client.prepareSearch("yryz_zgzyq_new");
            search.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            search.setTypes("job_new");
            search.setQuery(qb);
            search.setFrom((pageNo - 1) * pageSize).setSize(pageSize).setExplain(true);
            search.setExplain(true);
            SearchResponse searchResponse = search.get();

            PageList<HelloInfo> pageList = new PageList<>();
            pageList.setCount(searchResponse.getHits().getTotalHits());
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                HelloInfo helloInfo = JSON.parseObject(JSON.toJSONString(hit.getSource()), new TypeReference<HelloInfo>(){});
                list.add(helloInfo);
            }
            pageList.setCurrentPage(pageNo);
            pageList.setPageSize(pageSize);
            pageList.setEntities(list);
            client.close();

            return pageList;
        } catch (Exception e) {
            logger.error("transportClient error", e);
        }

        return null;
    }

}
