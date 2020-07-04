package com.esb.im.server.dao;


import com.esb.im.server.domain.IMSysConfigDO;

public interface IMSysConfigMapper {
    IMSysConfigDO findConfigByCode(IMSysConfigDO imSysConfigDO);
}