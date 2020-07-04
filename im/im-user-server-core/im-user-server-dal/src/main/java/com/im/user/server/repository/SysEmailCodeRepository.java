package com.im.user.server.repository;

import com.im.user.server.domain.SysEmailCodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysEmailCodeRepository {

    /**
     * 保存邮件验证码信息
     * @param record
     * @return
     */
    boolean saveEmailCode(SysEmailCodeEntity record);

    /**
     * 根据邮箱账号查询验证码信息
     * @param record
     * @return
     */
    List<SysEmailCodeEntity> findEmailCodeByEmailName(SysEmailCodeEntity record);

    /**
     * 更新邮箱验证码信息
     * @return
     */
    Integer updateEmailCode();

    /**
     * 根据邮箱账号更新验证码信息
     * @param record
     * @return
     */
    boolean updateEmailCodeByEmailName(SysEmailCodeEntity record);

    Integer countEmailCodeByEmailName(SysEmailCodeEntity record);
}
