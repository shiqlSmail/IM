package com.esb.im.server.domain;

import java.io.Serializable;

public class IMSysConfigDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String sysConfigCode;

    private String sysConfigValue;

    private Integer sysConfigTime;

    private Integer sysConfigStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysConfigCode() {
        return sysConfigCode;
    }

    public void setSysConfigCode(String sysConfigCode) {
        this.sysConfigCode = sysConfigCode;
    }

    public String getSysConfigValue() {
        return sysConfigValue;
    }

    public void setSysConfigValue(String sysConfigValue) {
        this.sysConfigValue = sysConfigValue;
    }

    public Integer getSysConfigTime() {
        return sysConfigTime;
    }

    public void setSysConfigTime(Integer sysConfigTime) {
        this.sysConfigTime = sysConfigTime;
    }

    public Integer getSysConfigStatus() {
        return sysConfigStatus;
    }

    public void setSysConfigStatus(Integer sysConfigStatus) {
        this.sysConfigStatus = sysConfigStatus;
    }
}