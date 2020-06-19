package com.im.user.server.facade;

import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;

import java.util.List;

public interface SysSmsFacade {
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

}
