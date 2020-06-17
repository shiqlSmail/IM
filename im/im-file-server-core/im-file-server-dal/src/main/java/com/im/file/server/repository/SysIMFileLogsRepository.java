package com.im.file.server.repository;

import com.im.file.server.domain.SysIMFileLogsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysIMFileLogsRepository {

    /**
     * 保存日志信息
     * @param sysIMFileLogsEntity
     * @return
     */
    boolean insertLogsByRequest(SysIMFileLogsEntity sysIMFileLogsEntity);
}
