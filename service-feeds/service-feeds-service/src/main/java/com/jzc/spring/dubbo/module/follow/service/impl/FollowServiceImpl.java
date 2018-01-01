package com.jzc.spring.dubbo.module.follow.service.impl;

import com.alibaba.fastjson.JSON;
import com.jzc.spring.dubbo.constant.EventReportConstant;
import com.jzc.spring.dubbo.constant.ExceptionEnum;
import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.exception.QsourceException;
import com.jzc.spring.dubbo.module.feeds.mq.FeedsSenderManager;
import com.jzc.spring.dubbo.module.follow.constant.FollowConstant;
import com.jzc.spring.dubbo.module.follow.dao.mongo.FansMongo;
import com.jzc.spring.dubbo.module.follow.dao.mongo.FollowMongo;
import com.jzc.spring.dubbo.module.follow.dto.FollowDto;
import com.jzc.spring.dubbo.module.follow.entity.Fans;
import com.jzc.spring.dubbo.module.follow.entity.Follow;
import com.jzc.spring.dubbo.module.follow.service.FollowService;
import com.jzc.spring.dubbo.module.follow.vo.FollowUserVo;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;
import com.jzc.spring.dubbo.redis.utils.JedisUtils;
import com.jzc.spring.dubbo.redis.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMongo followMongo;

    @Autowired
    private FansMongo fansMongo;

    @Autowired
    private FeedsSenderManager feedsSenderManager;

    /**
     * 好友关系列表
     * @param followDto
     * @return
     * */
    public PageList<FollowVo> list(FollowDto followDto) {
       PageList<FollowVo> resultList;
       if(Integer.valueOf(1).equals(followDto.getFollowType())){
           resultList = this.followList(followDto);
       } else {
           resultList = this.fansList(followDto);
       }

       return resultList;
    }

    /**
     * 关注列表
     * @param   followDto
     * @return
     * */
    private PageList<FollowVo> followList(FollowDto followDto) {
        List<FollowVo> resultList = new ArrayList<>();
        Query query = new Query();
        Integer currentPage = followDto.getCurrentPage() == null ? 1 : followDto.getCurrentPage();
        Integer pageSize = followDto.getPageSize() == null ? 10 : followDto.getPageSize();
        query.addCriteria(Criteria.where("userId").is(followDto.getCreateUserId()));
        long count = followMongo.count(query);
        if(count > 0) {
            query.with(new Sort(Sort.Direction.DESC, "createDateLong"));
            List<Follow> followList = followMongo.findPage(currentPage, pageSize, query);
            if(!CollectionUtils.isEmpty(followList)) {
                resultList = followList.stream().map(follow -> {
                    FollowVo followVo = new FollowVo();
                    BeanUtils.copyProperties(follow, followVo);
                    followVo.setUserId(follow.getFollowUserId());
                    return followVo;
                }).collect(Collectors.toList());
            }
        }

        return new PageList<>(currentPage, pageSize, resultList, count);
    }

    /**
     * 获取用户信息
     * @param   createUserId    当前登录用户id
     * @param   userId          个人主页用户id
     * */
    public FollowUserVo followDetail(String createUserId, String userId) {
        Assert.hasText(userId, "userId不能为空");
        FollowUserVo followUserVo = new FollowUserVo();
        FollowDto followDto = new FollowDto();
        followDto.setCreateUserId(userId);
        String key = FollowConstant.getHashKey(followDto);
        //获取关注数量
        long attentionCount;
        if(!JedisUtils.exists(key + FollowConstant.FOLLOW_ALL)) {
            Set<String> attentionSet = this.getFollowAll(userId);
            attentionCount = attentionSet.size();
        } else {
            attentionCount = JedisUtils.setScard(key + FollowConstant.FOLLOW_ALL);
        }
        //获取粉丝数量
        long fansCount;
        if(!JedisUtils.exists(key + FollowConstant.FANS_ALL)){
            Set<String> fansSet = this.getFansAll(userId);
            fansCount = fansSet.size();
        } else {
            fansCount = JedisUtils.setScard(key + FollowConstant.FANS_ALL);
        }
        followUserVo.setAttentionCount(attentionCount);//关注数
        followUserVo.setFansCount(fansCount);//粉丝数
        followUserVo.setUserId(userId);
        //如果登录用户不等于空
        if(StringUtils.isNotBlank(createUserId)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(createUserId));
            query.addCriteria(Criteria.where("followUserId").is(userId));
            Follow follow = followMongo.findOne(query);
            if(follow != null) {
                followUserVo.setAlias(follow.getAlias());
                followUserVo.setAttentionFlag(1);
            } else {
                followUserVo.setAttentionFlag(0);
            }
        }

        return followUserVo;
    }

    /**
     * 粉丝列表
     * @param   followDto
     * @return
     * */
    private PageList<FollowVo> fansList(FollowDto followDto) {
        List<FollowVo> resultList = new ArrayList<>();
        Query query = new Query();
        Integer currentPage = followDto.getCurrentPage() == null ? 1 : followDto.getCurrentPage();
        Integer pageSize = followDto.getPageSize() == null ? 10 : followDto.getPageSize();
        query.addCriteria(Criteria.where("userId").is(followDto.getCreateUserId()));
        long count = fansMongo.count(query);
        if(count > 0) {
            query.with(new Sort(Sort.Direction.DESC, "createDateLong"));
            List<Fans> fansList = fansMongo.findPage(currentPage, pageSize, query);
            if(!CollectionUtils.isEmpty(fansList)) {
                resultList = fansList.stream().map(fans -> {
                    FollowVo followVo = new FollowVo();
                    BeanUtils.copyProperties(fans, followVo);
                    followVo.setUserId(fans.getFansUserId());
                    return followVo;
                }).collect(Collectors.toList());
            }
        }

        return new PageList<>(currentPage, pageSize, resultList, count);
    }

    /**
     * 获取全部关注列表
     * @param   userId
     * @return
     * */
    public Set<String> getFollowAll(String userId) {
        Set<String> set = new HashSet<>();
        FollowDto followDto = new FollowDto();
        followDto.setCreateUserId(userId);
        String key = FollowConstant.getHashKey(followDto) + FollowConstant.FOLLOW_ALL;
        if(!JedisUtils.exists(key)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userId));
            query.with(new Sort(Sort.Direction.DESC, "createDateLong"));
            List<Follow> list = followMongo.find(query);
            if(!CollectionUtils.isEmpty(list)) {
                list.stream().forEach(follow -> set.add(follow.getFollowUserId()));
            }
            if(set.size() > 0){
                JedisUtils.setSet(key, set, 0);
            }

            return set;
        }

        return JedisUtils.getSet(key);
    }

    /**
     * 获取全部粉丝列表
     * @param   userId
     * @return
     * */
    public Set<String> getFansAll(String userId) {
        Set<String> set = new HashSet<>();
        FollowDto followDto = new FollowDto();
        followDto.setCreateUserId(userId);
        String key = FollowConstant.getHashKey(followDto) + FollowConstant.FANS_ALL;
        if(!JedisUtils.exists(key)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userId));
            query.with(new Sort(Sort.Direction.DESC, "createDateLong"));
            List<Fans> list = fansMongo.find(query);
            if(!CollectionUtils.isEmpty(list)) {
                list.stream().forEach(fans -> set.add(fans.getFansUserId()));
            }
            if(set.size() > 0){
                JedisUtils.setSet(key, set, 0);
            }

            return set;
        }

        return JedisUtils.getSet(key);
    }

    /**
     * 关注/取消关注
     * @param followDto
     * @return
     * */
    public Integer doFollow(FollowDto followDto) {
        if(this.whetherFollowStatus(followDto)) {
            followDto.setFollowFlag(FollowConstant.FOLLOW_FLAG);
        } else {
            followDto.setFollowFlag(FollowConstant.UNFOLLOW_FLAG);
        }
        //发往MQ队列
        this.sendMQ(followDto);
        return 1;
    }

    /**
     * 设置备注名
     * @param   followDto
     * @return
     * */
    public Integer setAlias(FollowDto followDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(followDto.getCreateUserId()));
        query.addCriteria(Criteria.where("followUserId").is(followDto.getUserId()));
        Update update = new Update();
        update.set("alias", followDto.getAlias());
        followMongo.update(query, update);
        return 1;
    }

    /**
     * 获得关注状态
     * @param   followDto
     * @return
     * */
    private boolean whetherFollowStatus(FollowDto followDto) {
        //Hash key
        String key = FollowConstant.getHashKey(followDto);
        String field = followDto.getUserId();
        Integer followStatus = null;
        //判断redis中是否存在此field
        if(!JedisUtils.mapExists(key, field)){
            followStatus = this.getFollowStatus(followDto.getCreateUserId(), followDto.getUserId());
            JedisUtils.mapSetnx(key, field, followStatus.toString());
        }
        //递增用户的关注状态
        Long status = JedisUtils.mapIncrby(key, field, 1);
        if(status != null) {
            return EventReportConstant.whetherOdd(status);
        }
        throw new QsourceException(ExceptionEnum.SysException);
    }

    /**
     * 从数据库中获取状态
     * @param   createUserId
     * @param   userId
     * @return
     * */
    public Integer getFollowStatus(String createUserId, String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(createUserId));
        query.addCriteria(Criteria.where("followUserId").is(userId));
        return followMongo.exists(query) ? 1 : 0;
    }

    /**
     * 送到MQ队列
     * @param   followDto
     * */
    private void sendMQ(FollowDto followDto) {
        Map<String, String> map = new HashMap<>();
        map.put("entity", JSON.toJSONString(followDto));
        map.put("path", "com.jzc.spring.dubbo.module.follow.service.FollowService");
        feedsSenderManager.sendEventReport(map);
    }

    @Override
    public boolean execute(Map<String, String> data) {
        if(data == null){
            return false;
        }
        FollowDto followDto = JSON.parseObject(data.get("entity"), FollowDto.class);
        if(followDto == null){
            return false;
        }
        //关注
        if(Integer.valueOf(1).equals(followDto.getFollowFlag())) {
            //保存关注人信息
            this.saveAttention(followDto, 1);
            //保存粉丝人信息
            this.saveFans(followDto, 1);
            //TODO:此处需要将关注人的朋友圈推送到被关注人feeds流中
        } else {//取消关注
            //保存关注人信息
            this.saveAttention(followDto, -1);
            //保存粉丝人信息
            this.saveFans(followDto, -1);
            //TODO:此处需要做反操作
        }

        return true;
    }

    /**
     * 保存关注人信息
     * @param   followDto
     * @param   count
     * */
    private void saveAttention(FollowDto followDto, Integer count) {
        Follow follow = new Follow();
        if(Integer.valueOf(1).equals(count)) {
            follow.setKid(System.currentTimeMillis());
            follow.setUserId(followDto.getCreateUserId());
            follow.setFollowUserId(followDto.getUserId());
            follow.setCreateDate(new Date());
            follow.setCreateDateLong(follow.getCreateDate().getTime());
            followMongo.save(follow);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(followDto.getCreateUserId()));
            query.addCriteria(Criteria.where("followUserId").is(followDto.getUserId()));
            followMongo.remove(query);
        }
        //删除关注列表
        String key = FollowConstant.getHashKey(followDto) + FollowConstant.FOLLOW_ALL;
        JedisUtils.del(key);
    }

    /**
     * 保存粉丝人信息
     * @param   followDto
     * @param   count
     * */
    private void saveFans(FollowDto followDto, Integer count) {
        Fans fans = new Fans();
        if(Integer.valueOf(1).equals(count)) {
            fans.setKid(System.currentTimeMillis());
            fans.setUserId(followDto.getUserId());
            fans.setFansUserId(followDto.getCreateUserId());
            fans.setCreateDate(new Date());
            fans.setCreateDateLong(fans.getCreateDate().getTime());
            fansMongo.save(fans);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(followDto.getUserId()));
            query.addCriteria(Criteria.where("fansUserId").is(followDto.getCreateUserId()));
            fansMongo.remove(query);
        }
        //删除粉丝列表
        FollowDto followRedis = new FollowDto();
        followRedis.setCreateUserId(followDto.getUserId());
        String key = FollowConstant.getHashKey(followRedis) + FollowConstant.FANS_ALL;
        JedisUtils.del(key);
    }

    @Override
    public boolean callback(Map<String, String> data) {
        return false;
    }
}
