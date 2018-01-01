package com.jzc.spring.dubbo.module.follow.service;


import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.feeds.service.FeedsMQ;
import com.jzc.spring.dubbo.module.follow.dto.FollowDto;
import com.jzc.spring.dubbo.module.follow.vo.FollowUserVo;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;

import java.util.Set;

public interface FollowService extends FeedsMQ {

    /**
     * 好友关系列表
     * @param followDto
     * @return
     * */
    PageList<FollowVo> list(FollowDto followDto);

    /**
     * 获取用户信息
     * @param   createUserId    当前登录用户id
     * @param   userId          个人主页用户id
     * */
    FollowUserVo followDetail(String createUserId, String userId);

    /**
     * 获取全部好友列表
     * @param   userId
     * @return
     * */
    Set<String> getFollowAll(String userId);

    /**
     * 获取全部粉丝列表
     * @param   userId
     * @return
     * */
    Set<String> getFansAll(String userId);

    /**
     * 关注/取消关注
     * @param followDto
     * @return
     * */
    Integer doFollow(FollowDto followDto);

    /**
     * 设置备注名
     * @param   followDto
     * @return
     * */
    Integer setAlias(FollowDto followDto);

}
