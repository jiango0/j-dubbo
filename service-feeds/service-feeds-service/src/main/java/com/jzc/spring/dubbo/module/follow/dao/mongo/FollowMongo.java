package com.jzc.spring.dubbo.module.follow.dao.mongo;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.follow.entity.Follow;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;
import com.jzc.spring.dubbo.mongodb.AbsBaseMongoDAO;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowMongo extends AbsBaseMongoDAO<Follow> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean exists(Query query) {
        return mongoTemplate.exists(query, Follow.class);
    }

    public void upsert(Query query, Update update) {
        mongoTemplate.upsert(query, update, Follow.class);
    }

    public long count(Query query) {
        return mongoTemplate.count(query, Follow.class);
    }

    public List<Follow> findPage(Integer currentPage, Integer pageSize, Query query) {
        query.skip((currentPage - 1) * pageSize).limit(pageSize);
        return mongoTemplate.find(query, Follow.class);
    }

    public Integer remove(Query query) {
        WriteResult writeResult = mongoTemplate.remove(query, Follow.class);
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
