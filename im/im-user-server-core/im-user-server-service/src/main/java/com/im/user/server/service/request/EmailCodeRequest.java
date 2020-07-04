package com.im.user.server.service.request;

import com.im.user.server.page.PageData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeRequest  extends PageData {
    private String email;
}
