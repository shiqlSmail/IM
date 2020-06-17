package com.im.user.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class IMUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imUserId;
    private String imUserCode;
    private String imUserNikename;
    private String imUserName;
    private Integer imUserSex;
    private Integer imUserAge;
    private String imUserBrithday;
    private Date imUserCreatetime;
    private Integer imUserStatus;
    private String imUserAddress;
    private String imUserIp ;
    private String imUserImage;
    private String imUserPhone;
    private String imUserEmail;
    private String imUserPassword;
    private String imUserCountry;
    private String imUserProvince;
    private String imUserCity;
    private String imUserLongitude;
    private String imUserDimension;
}

