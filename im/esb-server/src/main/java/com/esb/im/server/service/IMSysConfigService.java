package com.esb.im.server.service;


import com.esb.im.server.domain.IMSysConfigDO;

public interface IMSysConfigService {
    String findConfigByCode(String code);
}