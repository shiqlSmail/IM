package com.esb.im.server.serviceimpl;

import com.esb.im.server.dao.IMApiMapper;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.service.IMApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imApiService")
public class IMApiServiceImpl implements IMApiService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private IMApiMapper imApiMapper;

    @Override
    public IMApiDO selectByTransCode(String transCode) {
        return imApiMapper.selectByTransCode(transCode);
    }

    @Override
    public IMApiDO selectByPrimaryKey(Integer apiId) {
        return imApiMapper.selectByPrimaryKey(apiId);
    }

    @Override
    public List<IMApiDO> selectAll(Integer apiMenu) {
        return imApiMapper.selectAll(apiMenu);
    }

    @Override
    public boolean delete(Integer id) {
        return imApiMapper.delete(id);
    }

    @Override
    public boolean save(IMApiDO imApiDO) {
        return imApiMapper.save(imApiDO);
    }

    @Override
    public boolean edit(IMApiDO imApiDO) {
        return imApiMapper.edit(imApiDO);
    }
}
