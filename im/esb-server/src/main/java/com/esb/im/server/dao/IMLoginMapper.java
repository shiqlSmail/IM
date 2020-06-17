package com.esb.im.server.dao;


import com.esb.im.server.domain.IMLoginDO;

import java.util.List;

public interface IMLoginMapper {
    IMLoginDO loginApi(IMLoginDO imLoginDO);

    boolean save(IMLoginDO imLoginDO);

    boolean edit(IMLoginDO imLoginDO);

    List<IMLoginDO> findAll();
}