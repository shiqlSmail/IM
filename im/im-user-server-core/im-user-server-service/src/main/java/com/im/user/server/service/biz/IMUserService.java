package com.im.user.server.service.biz;

import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.page.PageBean;
import com.im.user.server.page.PageData;

import java.util.Map;

public interface IMUserService {

    public PageBean<IMUserEntity> findAllIMUserInfo(PageData pageData);

    /**
     * 用户登录  根据账号密码
     * @param record
     * @return
     */
    public Map<String, Object> loginIMUserByPhoneAndEmailAndCode(IMUserEntity record);

    /**
     * 用户登录  根据手机号验证码
     * @param record
     * @return
     */
    public Map<String, Object> loginIMUserByPhone(IMUserEntity record);

    /**
     * 检查注册手机号码是否存在
     * @param param
     * @return
     */
    public Map<String, Object> checkIMUserByPhoneExits(IMUserEntity param);

    /**
     * 检查注册邮箱是否存在
     * @param param
     * @return
     */
    public Map<String, Object> checkIMUserByEmailExits(IMUserEntity param);

    /**
     * 用户注册
     * @param param
     * @return
     */
    public Map<String, Object> saveIMUserInfo(IMUserEntity param);

    /**
     * 忘记密码，根据ID进行修改
     * @param param
     * @return
     */
    public Map<String, Object> updateIMUserPasswordById(IMUserEntity param);

    /**
     * 管理员冻结用户
     * @param record
     * @return
     */
    public Map<String, Object> updateUserStatus(IMUserEntity record);

    /**
     * 根据用户ID查询用户信息
     * @param record
     * @return
     */
    public Map<String, Object> loginIMUserById(IMUserEntity record);

}
