package com.im.user.server.facade;

import com.im.user.server.domain.SysIMUserLogsEntity;

public interface SysIMUserLogsFacade {
    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    public boolean insertLogsByRequest(SysIMUserLogsEntity sysIMUserLogsEntity);
}
