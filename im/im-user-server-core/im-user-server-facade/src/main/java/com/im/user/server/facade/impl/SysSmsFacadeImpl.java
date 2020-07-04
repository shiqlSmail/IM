package com.im.user.server.facade.impl;

import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.facade.SysSmsFacade;
import com.im.user.server.page.PageBean;
import com.im.user.server.service.biz.SysSmsService;
import com.im.user.server.service.request.EmailCodeRequest;
import com.im.user.server.service.request.SmsCodeRequest;
import com.server.tools.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageBean<SysSmsEntity> findSmsByPhone(SmsCodeRequest record) {
        return sysSmsService.findSmsByPhone(record);
    }

    @Override
    public boolean saveEmailCode(SysEmailCodeEntity record) {
        Cache.put(record.getEmailName(), record.getEmailCode(), 60000*5);
        return sysSmsService.saveEmailCode(record);
    }

    @Override
    public PageBean<SysEmailCodeEntity> findEmailCodeByEmailName(EmailCodeRequest record) {
        return sysSmsService.findEmailCodeByEmailName(record);
    }

    @Override
    public boolean saveEmailUrl(SysEmailUrlEntity record) {
        return sysSmsService.saveEmailUrl(record);
    }

    @Override
    public PageBean<SysEmailUrlEntity> findEmailByEmailUrl(EmailCodeRequest record) {
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
