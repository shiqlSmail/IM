package com.im.getway.server.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailSendParam {
    @NotBlank(message = "发件人邮箱不能为空")
    private String email;

    @NotBlank(message = "邮箱URL不能为空")
    private String url;

    @NotBlank(message = "发件人姓名不能为空")
    private String username;
}
