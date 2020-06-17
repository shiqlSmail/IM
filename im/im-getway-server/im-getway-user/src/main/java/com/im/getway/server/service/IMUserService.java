package com.im.getway.server.service;

import com.im.getway.server.request.*;
import com.im.user.server.page.PageData;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface IMUserService {

    /**
     * 用户登录：可使用手机/邮箱/用户号进行登录
     * @param param
     * @return
     */
    public Map<String,Object> loginIlonwUserByPhoneAndEmailAndCode(IlonwLoginParam param);


    /**
     * 查询所有用户接口
     * @param pageData
     * @return
     */
    public Map<String,Object> queryAllUser(@RequestBody PageData pageData);

    /**
     * 用户忘记密码，使用手机号码修改密码接口
     * @param param
     * @return
     */
    public Map<String,Object> ilonwUserUpdatePassword(@RequestBody ForgetPassParam param);

    /**
     * 用户注册判断手机号码是否存在接口
     * @param param
     * @return
     */
    public Map<String,Object> registerIlonwUserCheckPhoneisExtis(@RequestBody CheckPhoneParam param);

    /**
     * 用户注册接口
     * @param param
     * @return
     */
    public Map<String,Object> registerIlonwUserInfo(@RequestBody SysIlonwSaveUserParam param);

    /**
     * 管理员冻结用户
     * @param param
     * @return
     */
    public Map<String, Object> updateUserStatus(@RequestBody UserIdParam param);

    /**
     * 根据用户ID查询用户信息
     * @param param
     * @return
     */
    public Map<String, Object> loginIlonwUserById(UserIdParam param);
}
