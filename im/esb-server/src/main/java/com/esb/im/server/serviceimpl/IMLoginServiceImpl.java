package com.esb.im.server.serviceimpl;

import com.esb.im.server.dao.IMLoginMapper;
import com.esb.im.server.domain.IMLoginDO;
import com.esb.im.server.service.IMLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("imLoginService")
public class IMLoginServiceImpl implements IMLoginService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private IMLoginMapper imLoginMapper;

    @Override
    public IMLoginDO loginApi(IMLoginDO imLoginDO) {
        return imLoginMapper.loginApi(imLoginDO);
    }

    @Override
    public List<IMLoginDO> findAll() {
        return imLoginMapper.findAll();
    }

    @Override
    public boolean save(IMLoginDO imLoginDO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        imLoginDO.setCreatetime(sdf.format(new Date()));
        imLoginDO.setStatus(1);
        imLoginDO.setRole(1);
        return imLoginMapper.save(imLoginDO);
    }

    @Override
    public boolean edit(IMLoginDO imLoginDO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        imLoginDO.setUpdatetime(sdf.format(new Date()));
        return imLoginMapper.edit(imLoginDO);
    }
}
