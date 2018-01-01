package com.jzc.spring.dubbo.module.follow.vo;


import java.io.Serializable;
import java.util.Date;

public class FollowVo implements Serializable {

    /**
     * kid
     * */
    private Long kid;

    /**
     * 用户Id
     * */
    private String userId;

    /**
     * 别名
     * */
    private String alias;

    /**
     * 创建时间
     * */
    private Date createDate;

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
}
