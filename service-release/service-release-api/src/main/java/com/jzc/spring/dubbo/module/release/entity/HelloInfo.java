package com.jzc.spring.dubbo.module.release.entity;

import java.io.Serializable;

public class HelloInfo implements Serializable {

    private Long kid;

    private Integer moduleEnum;

    private String positionDesc;

    private String positionDescTxt;

    private String positionJob;

    private String positionJobTxt;

    private Integer shelveFlag;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Integer getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(Integer moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getPositionDescTxt() {
        return positionDescTxt;
    }

    public void setPositionDescTxt(String positionDescTxt) {
        this.positionDescTxt = positionDescTxt;
    }

    public String getPositionJob() {
        return positionJob;
    }

    public void setPositionJob(String positionJob) {
        this.positionJob = positionJob;
    }

    public String getPositionJobTxt() {
        return positionJobTxt;
    }

    public void setPositionJobTxt(String positionJobTxt) {
        this.positionJobTxt = positionJobTxt;
    }

    public Integer getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Integer shelveFlag) {
        this.shelveFlag = shelveFlag;
    }
}
