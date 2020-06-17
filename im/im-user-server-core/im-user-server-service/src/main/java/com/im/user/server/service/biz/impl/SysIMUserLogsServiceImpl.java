package com.im.user.server.service.biz.impl;

import com.im.user.server.dataconfig.DataSourceType;
import com.im.user.server.dataconfig.MyDataSource;
import com.im.user.server.domain.SysIMUserLogsEntity;
import com.im.user.server.repository.SysIMUserLogsRepository;
import com.im.user.server.service.biz.SysIMUserLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysIMUserLogsService")
public class SysIMUserLogsServiceImpl implements SysIMUserLogsService {

    @Autowired
    private SysIMUserLogsRepository sysIMUserLogsRepository;

    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Slave)
    @Transactional
    public boolean insertLogsByRequest(SysIMUserLogsEntity sysIMUserLogsEntity){
        boolean rows = sysIMUserLogsRepository.insertLogsByRequest(sysIMUserLogsEntity);
        if(rows == Boolean.FALSE) {	//更新小于1 执行回滚
            throw new RuntimeException();
        }
        return rows;
    }
}
