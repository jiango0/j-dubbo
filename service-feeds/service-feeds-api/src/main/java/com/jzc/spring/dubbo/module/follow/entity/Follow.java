package com.jzc.spring.dubbo.module.follow.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Follow implements Serializable {

    /**
     * kid
     * */
    private Long kid;

    /**
     * 用户Id
     * */
    private String userId;

    /**
     * 关注用户
     * */
    private String followUserId;

    /**
     * 别名
     * */
    private String alias;

    /**
     * 创建时间
     * */
    private Date createDate;

    /**
     * 修改时间
     * */
    private Date lastUpdateDate;

    /**
     * 创建时间(Long类型)
     * */
    private Long createDateLong;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getCreateDateLong() {
        return createDateLong;
    }

    public void setCreateDateLong(Long createDateLong) {
        this.createDateLong = createDateLong;
    }
}
