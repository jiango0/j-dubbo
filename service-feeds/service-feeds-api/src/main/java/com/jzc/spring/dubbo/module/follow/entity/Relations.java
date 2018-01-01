package com.jzc.spring.dubbo.module.follow.entity;

import java.io.Serializable;

public class Relations implements Serializable {

    private String userId;

    private String alias;

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

}
