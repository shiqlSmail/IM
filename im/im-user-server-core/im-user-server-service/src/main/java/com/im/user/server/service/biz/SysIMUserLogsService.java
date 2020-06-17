package com.im.user.server.service.biz;

import com.im.user.server.domain.SysIMUserLogsEntity;
import org.springframework.stereotype.Service;

@Service
public interface SysIMUserLogsService {
    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    public boolean insertLogsByRequest(SysIMUserLogsEntity sysIMUserLogsEntity);
}
