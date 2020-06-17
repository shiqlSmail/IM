package com.im.getway.server.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysIlonwSaveUserParam {
    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @ApiModelProperty(value = "用户登陆密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String smscode;

    private String ip;
}
