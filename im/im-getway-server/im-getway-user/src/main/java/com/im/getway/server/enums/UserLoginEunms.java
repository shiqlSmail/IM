package com.im.getway.server.enums;

public enum UserLoginEunms {
    CODE(1,"验证码登录"),
    PWDS(2,"账号密码登录"),
    //PHONE_NOTNULL("100001","第三方登录");
    ;

    private Integer resCode;
    private String resMsg;

    private UserLoginEunms() {
    }
    private UserLoginEunms(Integer resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
