package com.im.file.server.biz.impl;

import com.im.file.server.biz.SysIMFileLogsService;
import com.im.file.server.dataconfig.DataSourceType;
import com.im.file.server.dataconfig.MyDataSource;
import com.im.file.server.domain.SysIMFileLogsEntity;
import com.im.file.server.repository.SysIMFileLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sysIMFileLogsService")
public class SysIMFileLogsServiceImpl implements SysIMFileLogsService {

    @Autowired
    private SysIMFileLogsRepository sysIMUserLogsRepository;

    /**
     * 保存日志信息
     * @param sysIMUserLogsEntity
     * @return
     */
    @Override
    @Transactional
    @MyDataSource(DataSourceType.Slave)
    public boolean insertLogsByRequest(SysIMFileLogsEntity sysIMUserLogsEntity){
        boolean rows = sysIMUserLogsRepository.insertLogsByRequest(sysIMUserLogsEntity);
        if(rows == Boolean.FALSE) {	//更新小于1 执行回滚
            throw new RuntimeException();
        }
        return rows;
    }
}
