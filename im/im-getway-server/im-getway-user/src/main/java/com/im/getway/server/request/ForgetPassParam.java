package com.im.getway.server.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 忘记密码
 */
@Getter
@Setter
public class ForgetPassParam {
    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户手机号码")
    private String phone;
}
