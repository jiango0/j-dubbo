package com.jzc.spring.dubbo.module.follow.vo;

import java.io.Serializable;

public class FollowUserVo implements Serializable {

    /**
     * 用户Id
     * */
    private String userId;

    /**
     * 别名
     * */
    private String alias;

    /**
     * 关注数
     * */
    private long attentionCount;

    /**
     * 粉丝数
     * */
    private long fansCount;

    /**
     * 关注状态
     * */
    private Integer attentionFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(long attentionCount) {
        this.attentionCount = attentionCount;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(Integer attentionFlag) {
        this.attentionFlag = attentionFlag;
    }
}
