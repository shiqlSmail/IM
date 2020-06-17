package com.im.user.server.facade.impl;

import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.facade.IMUserFacade;
import com.im.user.server.page.PageBean;
import com.im.user.server.page.PageData;
import com.im.user.server.service.biz.IMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("imUserFacade")
public class IMUserServiceFacade implements IMUserFacade {

    @Autowired
    private IMUserService imUserService;

    @Override
    public PageBean<IMUserEntity> findAllIMUserInfo(PageData pageData) {
        return imUserService.findAllIMUserInfo(pageData);
    }

    /**
     * 用户登录  根据账号密码
     *
     * @param record
     * @return
     */
    public Map<String, Object> loginIMUserByPhoneAndEmailAndCode(IMUserEntity record) {
        return imUserService.loginIMUserByPhoneAndEmailAndCode(record);
    }

    /**
     * 用户登录  根据手机号验证码
     *
     * @param record
     * @return
     */
    public Map<String, Object> loginIMUserByPhone(IMUserEntity record) {
        return imUserService.checkIMUserByPhoneExits(record);
    }

    /**
     * 检查注册手机号码是否存在
     *
     * @param param
     * @return
     */
    public Map<String, Object> checkIMUserByPhoneExits(IMUserEntity param) {
        return imUserService.checkIMUserByPhoneExits(param);
    }

    /**
     * 检查注册邮箱是否存在
     *
     * @param param
     * @return
     */
    public Map<String, Object> checkIMUserByEmailExits(IMUserEntity param) {
        return imUserService.checkIMUserByEmailExits(param);
    }

    /**
     * 用户注册
     *
     * @param record
     * @return
     */
    public Map<String, Object> saveIMUserInfo(IMUserEntity record) {
        return imUserService.saveIMUserInfo(record);
    }

    /**
     * 忘记密码，根据手机号码进行修改
     *
     * @param param
     * @return
     */
    public Map<String, Object> updateIMUserPasswordById(IMUserEntity param) {
        return imUserService.updateIMUserPasswordById(param);
    }

    @Override
    public Map<String, Object> updateUserStatus(IMUserEntity record) {
        return imUserService.updateUserStatus(record);
    }

    /**
     * 根据用户ID查询用户信息
     * @param record
     * @return
     */
    @Override
    public Map<String, Object> loginIMUserById(IMUserEntity record) {
        return imUserService.loginIMUserById(record);
    }
}
