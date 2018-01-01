package com.jzc.spring.dubbo.module.follow.web;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.follow.FollowApi;
import com.jzc.spring.dubbo.module.follow.dto.FollowDto;
import com.jzc.spring.dubbo.module.follow.vo.FollowUserVo;
import com.jzc.spring.dubbo.module.follow.vo.FollowVo;
import com.jzc.spring.dubbo.web.BaseController;
import com.jzc.spring.dubbo.web.DubboResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("services/app/v1/follow")
public class FollowController extends BaseController {

    @Autowired
    private FollowApi followApi;

    @ResponseBody
    @RequestMapping(value="/single", method = RequestMethod.POST)
    public DubboResponse<Map<String, Integer>> doFollow(@RequestBody FollowDto followDto) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        followDto.setCreateUserId(userId);
        return followApi.doFollow(followDto);
    }

    @ResponseBody
    @RequestMapping(value="/alias", method = RequestMethod.POST)
    public DubboResponse<Map<String, Integer>> setAlias(@RequestBody FollowDto followDto) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        followDto.setCreateUserId(userId);
        return followApi.setAlias(followDto);
    }

    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public DubboResponse<PageList<FollowVo>> list(FollowDto followDto) {
        String userId = request.getHeader("userId");
        followDto.setCreateUserId(userId);
        return followApi.list(followDto);
    }

    @ResponseBody
    @RequestMapping(value="/followDetail", method = RequestMethod.GET)
    public DubboResponse<FollowUserVo> followDetail(String userId) {
        String createUserId = request.getHeader("userId");
        return followApi.followDetail(createUserId, userId);
    }

    @ResponseBody
    @RequestMapping(value="/getFollowAll", method = RequestMethod.GET)
    public DubboResponse<Set<String>> getFollowAll() {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        return followApi.getFollowAll(userId);
    }

    @ResponseBody
    @RequestMapping(value="/getFansAll", method = RequestMethod.GET)
    public DubboResponse<Set<String>> getFansAll() {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        return followApi.getFansAll(userId);
    }

}
