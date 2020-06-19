package com.im.user.server.facade.impl;

import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.facade.SysSmsFacade;
import com.im.user.server.service.biz.SysSmsService;
import com.server.tools.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysSmsFacade")
public class SysSmsFacadeImpl implements SysSmsFacade {

    @Autowired
    private SysSmsService sysSmsService;

    @Override
    public boolean saveSms(SysSmsEntity record) {
        Cache.put(record.getSmsPhone(), record.getSmsCode(), 60000);
        return sysSmsService.saveSms(record);
    }

    @Override
    public List<SysSmsEntity> findSmsByPhone(SysSmsEntity record) {
        return sysSmsService.findSmsByPhone(record);
    }

    @Override
    public boolean saveEmailCode(SysEmailCodeEntity record) {
        Cache.put(record.getEmailName(), record.getEmailCode(), 60000*5);
        return sysSmsService.saveEmailCode(record);
    }

    @Override
    public List<SysEmailCodeEntity> findEmailCodeByEmailName(SysEmailCodeEntity record) {
        return sysSmsService.findEmailCodeByEmailName(record);
    }

    @Override
    public boolean saveEmailUrl(SysEmailUrlEntity record) {
        return sysSmsService.saveEmailUrl(record);
    }

    @Override
    public List<SysEmailUrlEntity> findEmailByEmailUrl(SysEmailUrlEntity record) {
        return sysSmsService.findEmailByEmailUrl(record);
    }

    @Override
    public Integer updateSms() {
        return sysSmsService.updateSms();
    }

    @Override
    public Integer updateEmailCode() {
        return sysSmsService.updateEmailCode();
    }
}
