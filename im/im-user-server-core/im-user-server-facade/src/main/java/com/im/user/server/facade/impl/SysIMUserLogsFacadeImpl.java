package com.im.user.server.facade.impl;

import com.im.user.server.domain.SysIMUserLogsEntity;
import com.im.user.server.facade.SysIMUserLogsFacade;
import com.im.user.server.service.biz.SysIMUserLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysIMUserLogsFacade")
public class SysIMUserLogsFacadeImpl implements SysIMUserLogsFacade {

    @Autowired
    private SysIMUserLogsService sysIMUserLogsService;

    public boolean insertLogsByRequest(SysIMUserLogsEntity sysIMUserLogsEntity){
        return sysIMUserLogsService.insertLogsByRequest(sysIMUserLogsEntity);
    }
}
