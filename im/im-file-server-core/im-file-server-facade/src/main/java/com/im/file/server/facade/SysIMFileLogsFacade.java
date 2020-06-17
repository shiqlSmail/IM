package com.im.file.server.facade;


import com.im.file.server.domain.SysIMFileLogsEntity;

public interface SysIMFileLogsFacade {
    /**
     * 保存日志信息
     * @param sysIMFileLogsEntity
     * @return
     */
    public boolean insertLogsByRequest(SysIMFileLogsEntity sysIMFileLogsEntity);
}
