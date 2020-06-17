package com.im.user.server.repository;

import com.im.user.server.domain.SysIMUserLogsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysIMUserLogsRepository {

    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    boolean insertLogsByRequest(SysIMUserLogsEntity sysIMUserLogsEntity);
}
