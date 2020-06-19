package com.im.getway.server.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
public class IlonwLoginParam  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    private String password;

    @ApiModelProperty(value = "1：手机号验证码  2：账号密码")
    private String loginType;

    @ApiModelProperty(value = "验证码")
    private String smscode;
}
