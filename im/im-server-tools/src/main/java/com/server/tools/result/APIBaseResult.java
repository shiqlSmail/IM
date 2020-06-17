package com.server.tools.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIBaseResult {
    protected String sysCode;
    protected String sysMessage;
    private Object sysData;
    private Integer smscode;
    private Long totalCount;
    private BigDecimal rechargeTotalAmount;
    private BigDecimal payTotalAmount;
    private String times;


    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysMessage() {
        return sysMessage;
    }

    public void setSysMessage(String sysMessage) {
        this.sysMessage = sysMessage;
    }

    public Object getSysData() {
        return sysData;
    }

    public void setSysData(Object sysData) {
        this.sysData = sysData;
    }

    public Integer getSmscode() {
        return smscode;
    }

    public void setSmscode(Integer smscode) {
        this.smscode = smscode;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getRechargeTotalAmount() {
        return rechargeTotalAmount;
    }

    public void setRechargeTotalAmount(BigDecimal rechargeTotalAmount) {
        this.rechargeTotalAmount = rechargeTotalAmount;
    }

    public BigDecimal getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(BigDecimal payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public static void setSourceMissErrResult(APIBaseResult sourceMissErrResult) {
        APIBaseResult.sourceMissErrResult = sourceMissErrResult;
    }

    private static APIBaseResult sourceMissErrResult;
    public static APIBaseResult buildErrorResult(String errorDescription) {
        APIBaseResult apiBaseResult = new APIBaseResult();
        SetAPIResultUtil.setFail(apiBaseResult);
        apiBaseResult.setSysData("");
        return apiBaseResult;
    }
    public static APIBaseResult getSourceMissErrResult() {
        if (null == sourceMissErrResult) {
            sourceMissErrResult = new APIBaseResult();
            SetAPIResultUtil.setFail(sourceMissErrResult);
            sourceMissErrResult.setSysData("");
        }
        return sourceMissErrResult;
    }
    public static APIBaseResult getSourceInValidErrResult() {
        if (null == sourceMissErrResult) {
            sourceMissErrResult = new APIBaseResult();
            SetAPIResultUtil.setFail(sourceMissErrResult);
            sourceMissErrResult.setSysData("");
        }
        return sourceMissErrResult;
    }
    public static APIBaseResult getMissVersionErrResult() {
        if (null == sourceMissErrResult) {
            sourceMissErrResult = new APIBaseResult();
            SetAPIResultUtil.setFail(sourceMissErrResult);
            sourceMissErrResult.setSysData("");
        }
        return sourceMissErrResult;
    }
}
