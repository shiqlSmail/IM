package com.im.getway.server.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 检查手机号码是否存在
 */
@Getter
@Setter
public class CheckPhoneParam {
    @ApiModelProperty(value = "用户手机号码")
    private String phone;


}
