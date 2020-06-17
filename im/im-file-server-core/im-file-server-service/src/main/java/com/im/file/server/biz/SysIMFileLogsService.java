package com.im.file.server.biz;

import com.im.file.server.domain.SysIMFileLogsEntity;
import org.springframework.stereotype.Service;

@Service
public interface SysIMFileLogsService {
    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    public boolean insertLogsByRequest(SysIMFileLogsEntity sysIMUserLogsEntity);
}
