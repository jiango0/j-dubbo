package com.jzc.spring.dubbo.module.follow;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.follow.vo.FollowUserVo;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;
import com.jzc.spring.dubbo.web.DubboResponse;

import java.util.Map;
import java.util.Set;

public interface FollowApi {

    /**
     * 关注/取消关注
     * @param followDto
     * @return
     * */
    DubboResponse<Map<String, Integer>> doFollow(com.jzc.spring.dubbo.module.follow.dto.FollowDto followDto);

    /**
     * 好友关系列表
     * @param followDto
     * @return
     * */
    DubboResponse<PageList<FollowVo>> list(com.jzc.spring.dubbo.module.follow.dto.FollowDto followDto);

    /**
     * 获取关注信息
     * @param   createUserId
     * @param   userId
     * @return
     * */
    DubboResponse<FollowUserVo> followDetail(String createUserId, String userId);

    /**
     * 获取全部好友
     * @param   userId
     * @return
     * */
    DubboResponse<Set<String>> getFollowAll(String userId);

    /**
     * 获取全部粉丝列表
     * @param   userId
     * @return
     * */
    DubboResponse<Set<String>> getFansAll(String userId);

    /**
     * 设置备注名
     * @param   followDto
     * @return
     * */
    DubboResponse<Map<String, Integer>> setAlias(com.jzc.spring.dubbo.module.follow.dto.FollowDto followDto);

}
