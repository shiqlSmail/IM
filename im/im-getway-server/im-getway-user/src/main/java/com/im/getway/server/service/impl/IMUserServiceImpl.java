package com.im.getway.server.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.im.getway.server.enums.UserLoginEunms;
import com.im.getway.server.request.*;
import com.im.getway.server.service.IMUserService;
import com.im.getway.server.service.base.BaseService;
import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.facade.IMUserFacade;
import com.im.user.server.page.PageBean;
import com.im.user.server.page.PageData;
import com.im.user.server.service.eunms.UserEunms;
import com.server.tools.cache.Cache;
import com.server.tools.encryption.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class IMUserServiceImpl extends BaseService implements IMUserService {

    @Resource
    public IMUserFacade imUserFacade;

    /**
     * 用户登录：可使用手机/邮箱/用户号进行登录
     * @return
     */
    @Override
    public Map<String,Object> loginIlonwUserByPhoneAndEmailAndCode(IlonwLoginParam param){
        Map<String,Object> map = new HashMap<String,Object>();
        IMUserEntity record = new IMUserEntity();
        if(UserLoginEunms.CODE.getResCode().equals(param.getLoginType())){
            record.setImUserPhone(param.getPhone());
            map = imUserFacade.loginIMUserByPhone(record);
        }else{
            record.setImUserPhone(param.getPhone());
            record.setImUserPassword(MD5Util.MD5Encode(param.getPassword(),"UTF-8"));
            map = imUserFacade.loginIMUserByPhoneAndEmailAndCode(record);
        }
        return getBaseResultMaps(UserEunms.BIZ_SUCCESS.getResCode(),UserEunms.BIZ_SUCCESS.getResMsg(),map);
    }

    /**
     * 查询所有用户接口
     * @param pageData
     * @return
     */
    @Override
    public Map<String,Object> queryAllUser(PageData pageData){
        PageBean<IMUserEntity> listUser = new PageBean<>();
        Object obj = Cache.get("query_user");
        if(null == obj){
            listUser = imUserFacade.findAllIMUserInfo(pageData);
            Cache.put("query_user",listUser,1000);
        }else{
            listUser = (PageBean)obj;
        }
        return getBaseResultMaps(UserEunms.BIZ_ADMIN_SUCCESS.getResCode(),UserEunms.BIZ_ADMIN_SUCCESS.getResMsg(),listUser);
    }

    /**
     * 用户忘记密码，使用手机号码修改密码接口
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> ilonwUserUpdatePassword(ForgetPassParam param){
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserPhone(param.getPhone());

        //根据手机号码查询用户信息，获得用户ID
        Map<String,Object> userInfoMap = imUserFacade.checkIMUserByPhoneExits(imUserEntity);
        Integer resData = Integer.valueOf(userInfoMap.get("resData").toString());
        if(resData == 0){
            return getBaseResultMaps(UserEunms.BIZ_PHONE_ISNULL.getResCode(),UserEunms.BIZ_PHONE_ISNULL.getResMsg(),"");
        }else{
            //查询用户信息
            Map<String,Object> imUserEntityResData = imUserFacade.loginIMUserById(imUserEntity);
            IMUserEntity imUserEntity1 = (IMUserEntity)imUserEntityResData.get("resData");
            if(org.springframework.util.StringUtils.isEmpty(imUserEntity1)){
                return getBaseResultMaps(UserEunms.BIZ_USER_NULL.getResCode(),UserEunms.BIZ_USER_NULL.getResMsg(),"");
            }
            IMUserEntity imUserEntity2 = new IMUserEntity();
            imUserEntity2.setImUserPassword(MD5Util.MD5Encode(param.getPassword(),"UTF-8"));
            imUserEntity2.setImUserId(imUserEntity1.getImUserId());
            return imUserFacade.updateIMUserPasswordById(imUserEntity2);
        }
    }

    /**
     * 用户注册判断手机号码是否存在接口
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> registerIlonwUserCheckPhoneisExtis(CheckPhoneParam param){
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserPhone(param.getPhone());
        return imUserFacade.checkIMUserByPhoneExits(imUserEntity);
    }

    /**
     * 用户注册接口
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> registerIlonwUserInfo(SysIlonwSaveUserParam param){
        Map<String,Object> map = new HashMap<String,Object>();
        if(null == param.getPhone()){
            return getBaseResultMaps(UserEunms.BIZ_SMSCODE_EXITS.getResCode(),UserEunms.BIZ_SMSCODE_EXITS.getResMsg(),"");
        }

        //注册前先判断注册的手机号码是否存在
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserPhone(param.getPhone());
        Map<String,Object>  phoneExits = imUserFacade.checkIMUserByPhoneExits(imUserEntity);
        Integer resData = Integer.valueOf(phoneExits.get("resData").toString());
        if(resData == 1){
            return getBaseResultMaps(String.valueOf(phoneExits.get("resCode")),String.valueOf(phoneExits.get("resMsg")),"");
        }else{
            IMUserEntity saveUserBTO = getUserBTO(param);
            map = imUserFacade.saveIMUserInfo(saveUserBTO);
        }
        return map;
    }

    /**
     * 管理员冻结用户
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateUserStatus(UserIdParam param) {
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserId(param.getUserId());
        imUserEntity.setImUserStatus(Integer.valueOf(param.getStatus()));
        return imUserFacade.updateUserStatus(imUserEntity);
    }

    @Override
    public Map<String, Object> loginIlonwUserById(UserIdParam param) {
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserId(param.getUserId());
        return imUserFacade.loginIMUserById(imUserEntity);
    }

    /**
     * 类转换
     * @param param
     * @return
     */
    private IMUserEntity getUserBTO(SysIlonwSaveUserParam param){
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserIp(param.getIp());
        imUserEntity.setImUserPassword(param.getPassword());
        imUserEntity.setImUserPhone(param.getPhone());
        return imUserEntity;
    }
}
