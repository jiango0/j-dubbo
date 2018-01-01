package com.jzc.spring.dubbo.entity;

import java.io.Serializable;

public class GenericEntity implements Serializable {

    //主键ID
    private Long kid;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }
}
