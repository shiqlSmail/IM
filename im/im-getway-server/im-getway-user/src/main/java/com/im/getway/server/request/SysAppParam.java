package com.im.getway.server.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysAppParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户平台名称")
    private String appChannelName;

    @ApiModelProperty(value = "客户在ilonw系统中的用户标识")
    private String appUserId;
}
