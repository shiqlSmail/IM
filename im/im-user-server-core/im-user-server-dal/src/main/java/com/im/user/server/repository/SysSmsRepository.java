package com.im.user.server.repository;

import com.im.user.server.domain.SysSmsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysSmsRepository {
    /**
     * 保存验证码信息
     * @param record
     * @return
     */
    boolean saveSms(SysSmsEntity record);

    /**
     * 根据手机号码查看验证码信息
     * @param record
     * @return
     */
    List<SysSmsEntity> findSmsByPhone(SysSmsEntity record);

    /**
     * 更新验证码信息
     * @return
     */
    Integer updateSms();

    /**
     * 根据手机号码更新验证码状态
     * @param record
     * @return
     */
    boolean updateSmsByPhone(SysSmsEntity record);

    Integer countSmsByPhone(SysSmsEntity record);
}
