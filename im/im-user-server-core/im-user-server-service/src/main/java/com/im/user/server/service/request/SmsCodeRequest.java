package com.im.user.server.service.request;

import com.im.user.server.page.PageData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsCodeRequest extends PageData {
    private String phone;
}
