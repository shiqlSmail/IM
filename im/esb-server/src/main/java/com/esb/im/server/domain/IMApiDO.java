package com.esb.im.server.domain;

import java.io.Serializable;

public class IMApiDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer apiId;

    private String apiName;

    private String apiContext;

    private String apiUrl;

    private String apiParam;

    private String apiTranscode;

    private Integer apiStatus;

    private Integer apiMenu;

    public Integer getApiMenu() {
        return apiMenu;
    }

    public void setApiMenu(Integer apiMenu) {
        this.apiMenu = apiMenu;
    }

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getApiContext() {
        return apiContext;
    }

    public void setApiContext(String apiContext) {
        this.apiContext = apiContext == null ? null : apiContext.trim();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl == null ? null : apiUrl.trim();
    }

    public String getApiParam() {
        return apiParam;
    }

    public void setApiParam(String apiParam) {
        this.apiParam = apiParam == null ? null : apiParam.trim();
    }

    public String getApiTranscode() {
        return apiTranscode;
    }

    public void setApiTranscode(String apiTranscode) {
        this.apiTranscode = apiTranscode == null ? null : apiTranscode.trim();
    }
}