package com.jzc.spring.dubbo.module.follow.dao.mongo;

import com.jzc.spring.dubbo.module.follow.entity.Fans;
import com.jzc.spring.dubbo.mongodb.AbsBaseMongoDAO;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FansMongo extends AbsBaseMongoDAO<Fans> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public long count(Query query) {
        return mongoTemplate.count(query, Fans.class);
    }

    public List<Fans> findPage(Integer currentPage, Integer pageSize, Query query) {
        query.skip((currentPage - 1) * pageSize).limit(pageSize);
        return mongoTemplate.find(query, Fans.class);
    }

    public Integer remove(Query query) {
        WriteResult writeResult = mongoTemplate.remove(query, Fans.class);
//        if(writeResult != null) {
//            return writeResult.getN();
//        }

        return 0;
    }

    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
