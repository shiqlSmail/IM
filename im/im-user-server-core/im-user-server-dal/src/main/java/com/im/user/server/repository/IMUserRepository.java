package com.im.user.server.repository;

import com.im.user.server.domain.IMUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IMUserRepository {
    /**
     * 查询所有用户信息
     * @return
     */
    List<IMUserEntity> findAllIMUserInfo();

    /**
     * 查询所有用户的条数
     * @return
     */
    Integer countAllIMUserInfo();

    /**
     * 判断手机号码、邮箱、code是否存在
     * @param record
     * @return
     */
    Integer checkIMUserByPhoneAndEmailAndCodeExits(IMUserEntity record);

    /**
     * 用户登录  根据账号密码
     * @param record
     * @return
     */
    IMUserEntity loginIMUserByPhoneAndEmailAndCode(IMUserEntity record);

    /**
     * 用户登录  根据手机号验证码
     * @param record
     * @return
     */
    IMUserEntity loginIMUserByPhone(IMUserEntity record);

    /**
     * 检查注册手机号码是否存在
     * @param record
     * @return
     */
    Integer checkIMUserByPhoneExits(IMUserEntity record);

    /**
     * 检查注册邮箱是否存在
     * @param record
     * @return
     */
    Integer checkIMUserByEmailExits(IMUserEntity record);

    /**
     * 检查code是否存在
     * @param record
     * @return
     */
    Integer checkIMUserByCodeExits(IMUserEntity record);

    /**
     * 用户注册
     * @param record
     * @return
     */
    boolean saveIMUserInfo(IMUserEntity record);

    /**
     * 忘记密码，根据ID进行修改
     * @param record
     * @return
     */
    Integer updateIMUserPasswordById(IMUserEntity record);

    /**
     * 管理员冻结用户
     * @param record
     * @return
     */
    Integer updateUserStatus(IMUserEntity record);

    /**
     * 根据用户ID查询用户信息
     * @param record
     * @return
     */
    IMUserEntity loginIMUserById(IMUserEntity record);
}

