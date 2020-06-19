package com.im.user.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysSmsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //验证码ID
    private String smsId;

    //验证码code
    private Integer smsCode;

    //验证码状态
    private Integer smsStatus;

    //验证码创建时间
    private String smsCreatetime;

    //手机号码
    private String smsPhone;
}