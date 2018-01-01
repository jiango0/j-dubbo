package com.jzc.spring.dubbo.module.follow.provider;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.exception.ExceptionHandler;
import com.jzc.spring.dubbo.module.follow.FollowApi;
import com.jzc.spring.dubbo.module.follow.dto.FollowDto;
import com.jzc.spring.dubbo.module.follow.service.FollowService;
import com.jzc.spring.dubbo.module.follow.vo.FollowUserVo;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;
import com.jzc.spring.dubbo.web.DubboResponse;
import com.jzc.spring.dubbo.web.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class FollowProvider implements FollowApi {

    private static final Logger logger = LoggerFactory.getLogger(FollowProvider.class);

    @Autowired
    FollowService followService;

    /**
     * 关注/取消关注
     * @param followDto
     * @return
     * */
    @Override
    public DubboResponse<Map<String, Integer>> doFollow(FollowDto followDto) {
        try {
            Integer flag = followService.doFollow(followDto);
            Map<String, Integer> result = new HashMap<String, Integer>();
            result.put("flag", flag);
            return ResponseModel.returnObjectSuccess(result);
        } catch (Exception e) {
            logger.error("关注/取消关注 失败", e);
            return ResponseModel.returnException(e);
        }
    }

    /**
     *  获取查询列表
     *  @param  followDto
     *  @return
     * */
    @Override
    public DubboResponse<PageList<FollowVo>> list(FollowDto followDto) {
        try {
            return ResponseModel.returnListSuccess(followService.list(followDto));
        } catch (Exception e) {
            logger.error("获取查询列表 失败", e);
            return ResponseModel.returnException(e);
        }
    }

    /**
     * 获取关注信息
     * @param   createUserId
     * @param   userId
     * @return
     * */
    public DubboResponse<FollowUserVo> followDetail(String createUserId, String userId) {
        try {
            return ResponseModel.returnObjectSuccess(followService.followDetail(createUserId, userId));
        } catch (Exception e) {
            logger.error("获取关注信息 失败", e);
            return ResponseModel.returnException(e);
        }
    }

    /**
     * 获取全部关注列表
     * @param   userId
     * @return
     * */
    public DubboResponse<Set<String>> getFollowAll(String userId) {
        try {
            return ResponseModel.returnListSuccess(followService.getFollowAll(userId));
        } catch (Exception e) {
            logger.error("获取全部关注列表 失败", e);
            return ResponseModel.returnException(e);
        }
    }

    /**
     * 获取全部粉丝列表
     * @param   userId
     * @return
     * */
    public DubboResponse<Set<String>> getFansAll(String userId) {
        try {
            return ResponseModel.returnListSuccess(followService.getFansAll(userId));
        } catch (Exception e) {
            logger.error("获取全部粉丝列表 失败", e);
            return ResponseModel.returnException(e);
        }
    }

    /**
     * 修改备注名
     * @param followDto
     * @return
     * */
    @Override
    public DubboResponse<Map<String, Integer>> setAlias(FollowDto followDto) {
        try {
            Assert.hasText(followDto.getUserId(), "userId不能为空");
            Assert.hasText(followDto.getAlias(), "alias不能为空");
            Integer flag = followService.setAlias(followDto);
            Map<String, Integer> result = new HashMap<String, Integer>();
            result.put("flag", flag);
            return ResponseModel.returnObjectSuccess(result);
        } catch (Exception e) {
            logger.error("修改备注名 失败", e);
            return ResponseModel.returnException(e);
        }
    }

}
