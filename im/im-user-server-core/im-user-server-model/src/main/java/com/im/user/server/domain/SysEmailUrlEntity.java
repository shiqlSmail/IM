package com.im.user.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysEmailUrlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //验证码ID
    private String emailId;

    //验证码code
    private String emailUrl;

    //验证码状态
    private Integer emailStatus;

    //验证码创建时间
    private String emailCreatetime;

    //手机号码
    private String emailName;
}