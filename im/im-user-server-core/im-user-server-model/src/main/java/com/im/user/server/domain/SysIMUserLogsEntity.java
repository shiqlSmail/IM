package com.im.user.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysIMUserLogsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //日志ID
    private String logId;

    //日志写入人
    private String logAuthor;

    //日志创建时间
    private String logCreatetime;

    //日志记录接口的状态
    private String logStatus;

    //接口返回结果
    private String logResult;

    //接口请求参数
    private String logParam;

    //接口请求路径
    private String logRequest;

    //接口耗时
    private String logTimes;

    //接口请求的IP
    private String logIp;

    //接口请求的类型
    private String logType;

    private String logSystem;

    private String logSelno;

}