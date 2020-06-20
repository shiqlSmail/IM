package com.im.user.server.service.eunms;

public enum UserEunms {
    //系统层面
    SYS_SUCCESS("SYS_000000","交易成功"),
    SYS_FAIL("SYS_999999","交易失败"),
    SYS_ERROR("SYS_111111","系统异常"),
    SYS_PHONE_NOTNULL("SYS_100001","手机号码已存在"),
    SYS_EMAIL_NOTNULL("SYS_100002","邮箱已存在"),
    SYS_SMSCODE_NOTNULL("SYS_100003","请输入验证码"),
    SYS_REGISTER_FAIL("SYS_100004","注册失败"),
    SYS_LOGIN_CODE_NOTEXITS("SYS_100005","登录账号不存在"),
    SYS_PASSWORD_FAIL("SYS_100006","登录密码错误"),
    SYS_PASSWORD_UPDATE_FAIL("SYS_100007","密码修改失败"),
    SYS_SMSCODE_NULL("SYS_100008","验证码不能为空"),
    SYS_SMSCODE_EXITS("SYS_100009","未获取验证码"),
    SYS_USER_NULL("SYS_100010","暂无用户信息"),
    SYS_PHONE_ISNULL("SYS_100011","手机号码不存在"),
    SYS_SMSCODE_ERROR("SYS_100012","验证码输入有误，请重新输入"),

    //业务层面
    BIZ_ADMIN_SUCCESS("BIZ_ADMIN_SUCCESS","交易成功"),
    BIZ_SUCCESS("BIZ_SUCCESS","交易成功"),
    BIZ_FAIL("BIZ_999999","交易失败"),
    BIZ_PHONE_NOTNULL("BIZ_100001","手机号码已存在"),
    BIZ_EMAIL_NOTNULL("BIZ_100002","邮箱已存在"),
    BIZ_SMSCODE_NOTNULL("BIZ_100003","请输入验证码"),
    BIZ_REGISTER_FAIL("BIZ_100004","注册失败"),
    BIZ_LOGIN_CODE_NOTEXITS("BIZ_100005","登录账号不存在"),
    BIZ_PASSWORD_FAIL("BIZ_100006","登录密码错误"),
    BIZ_PASSWORD_UPDATE_FAIL("BIZ_100007","密码修改失败"),
    BIZ_SMSCODE_NULL("BIZ_100008","验证码不能为空"),
    BIZ_SMSCODE_EXITS("BIZ_100009","未获取验证码"),
    BIZ_USER_NULL("BIZ_100010","暂无用户信息"),
    BIZ_PHONE_ISNULL("BIZ_100011","手机号码不存在"),
    BIZ_SMSCODE_ERROR("BIZ_100012","验证码输入有误，请重新输入"),
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
