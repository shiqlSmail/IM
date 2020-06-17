package com.im.file.server.facade.impl;


import com.im.file.server.biz.SysIMFileLogsService;
import com.im.file.server.domain.SysIMFileLogsEntity;
import com.im.file.server.facade.SysIMFileLogsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysIMFileLogsFacade")
public class SysIMFileLogsFacadeImpl implements SysIMFileLogsFacade {

    @Autowired
    private SysIMFileLogsService sysIMUserLogsService;

    public boolean insertLogsByRequest(SysIMFileLogsEntity sysIMFilerLogsEntity){
        return sysIMUserLogsService.insertLogsByRequest(sysIMFilerLogsEntity);
    }
}
