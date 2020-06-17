package com.esb.im.server.service;


import com.esb.im.server.domain.IMLoginDO;

import java.util.List;

public interface IMLoginService {
    IMLoginDO loginApi(IMLoginDO imLoginDO);

    boolean save(IMLoginDO imLoginDO);

    boolean edit(IMLoginDO imLoginDO);

    List<IMLoginDO> findAll();
}