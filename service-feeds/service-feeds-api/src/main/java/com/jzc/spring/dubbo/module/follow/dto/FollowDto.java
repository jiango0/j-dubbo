package com.jzc.spring.dubbo.module.follow.dto;

import com.jzc.spring.dubbo.entity.PageList;

public class FollowDto extends PageList {

    /**
     * 当前用户id
     * */
    private String createUserId;

    /**
     * 用户Id
     * */
    private String userId;

    /**
     * 类型 1：关注 2：粉丝
     * */
    private Integer followType;

    /**
     * 备注名
     * */
    private String alias;

    /**
     * 关注标识
     * */
    private Integer followFlag;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(Integer followFlag) {
        this.followFlag = followFlag;
    }

}
