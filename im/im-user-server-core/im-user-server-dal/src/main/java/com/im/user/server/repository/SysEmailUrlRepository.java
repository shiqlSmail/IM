package com.im.user.server.repository;

import com.im.user.server.domain.SysEmailUrlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysEmailUrlRepository {

    /**
     * 保存邮件发送的URL信息
     * @param record
     * @return
     */
    boolean saveEmailUrl(SysEmailUrlEntity record);

    /**
     * 根据邮箱账号查询URL信息
     * @param record
     * @return
     */
    List<SysEmailUrlEntity> findEmailByEmailUrl(SysEmailUrlEntity record);

    Integer countEmailByEmailUrl(SysEmailUrlEntity record);
}
