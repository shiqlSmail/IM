package com.im.user.server.service.eunms;

public enum UserEunms {
    SUCCESS("000000","交易成功"),
    FAIL("999999","交易失败"),
    ERROR("111111","系统异常"),

    PHONE_NOTNULL("100001","手机号码已存在"),
    EMAIL_NOTNULL("100002","邮箱已存在"),
    SMSCODE_NOTNULL("100003","请输入验证码"),
    REGISTER_FAIL("100004","注册失败"),
    LOGIN_CODE_NOTEXITS("100005","登录账号不存在"),
    PASSWORD_FAIL("100006","登录密码错误"),
    PASSWORD_UPDATE_FAIL("100007","密码修改失败"),
    SMSCODE_NULL("100008","验证码不能为空"),
    SMSCODE_EXITS("100009","未获取验证码"),
    USER_NULL("100010","暂无用户信息"),
    PHONE_ISNULL("100011","手机号码不存在"),
    SMSCODE_ERROR("100012","验证码输入有误，请重新输入"),
    ;


    private String resCode;
    private String resMsg;

    private UserEunms() {
    }
    private UserEunms(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
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
