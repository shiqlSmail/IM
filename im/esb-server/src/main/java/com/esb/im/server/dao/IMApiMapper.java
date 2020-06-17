package com.esb.im.server.dao;


import com.esb.im.server.domain.IMApiDO;

import java.util.List;

public interface IMApiMapper {
    IMApiDO selectByPrimaryKey(Integer apiId);

    IMApiDO selectByTransCode(String transCode);

    List<IMApiDO> selectAll(Integer apiMenu);

    boolean delete(Integer id);

    boolean save(IMApiDO imApiDO);

    boolean edit(IMApiDO imApiDO);
}