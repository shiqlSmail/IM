package com.im.getway.server.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysAppResponse{

    private String app_channel_name;

    private Integer app_id;

    private String app_key;

    private String app_private_key;

    private String app_public_key;
}
