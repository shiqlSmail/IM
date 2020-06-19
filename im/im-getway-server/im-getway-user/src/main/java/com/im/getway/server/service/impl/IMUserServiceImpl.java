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
        Object phone = Cache.get(param.getPhone());
        if(UserLoginEunms.CODE.getResCode().equals(param.getLoginType())){
            //判断验证码是否为空
            if(StringUtils.isEmpty(param.getSmscode())){
                return getBaseResultMaps(UserEunms.SMSCODE_NULL.getResCode(),UserEunms.SMSCODE_NULL.getResMsg(),"");
            }else if(null == phone){
                return getBaseResultMaps(UserEunms.SMSCODE_EXITS.getResCode(),UserEunms.SMSCODE_EXITS.getResMsg(),"");
            }else{
                String str = String.valueOf(Cache.get(param.getPhone()));
                if("null".equals(str)){  //因为数据是从cacha缓存读取出来的，所以null值判断方式未 “null”
                    return getBaseResultMaps(UserEunms.SMSCODE_EXITS.getResCode(),UserEunms.SMSCODE_EXITS.getResMsg(),"");
                }else{
                    if(!StringUtils.equals(param.getSmscode(),str)) {
                        return getBaseResultMaps(UserEunms.SMSCODE_NOTNULL.getResCode(),UserEunms.SMSCODE_NOTNULL.getResMsg(),"");
                    }
                    record.setImUserPhone(param.getPhone());
                    map = imUserFacade.loginIMUserByPhone(record);
                }
            }
        }else{
            record.setImUserPhone(param.getPhone());
            record.setImUserPassword(MD5Util.MD5Encode(param.getPassword(),"UTF-8"));
            map = imUserFacade.loginIMUserByPhoneAndEmailAndCode(record);
        }
        return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),map);
    }

    /**
     * 查询所有用户接口
     * @param pageData
     * @return
     */
    @Override
    public Map<String,Object> queryAllUser(PageData pageData){
        PageBean<IMUserEntity> listUser = new PageBean<>();
        Map<String,Object> map = new HashMap<String,Object>();
        Object obj = Cache.get("query_user");
        if(null == obj){
            listUser = imUserFacade.findAllIMUserInfo(pageData);
            Cache.put("query_user",listUser,1000);
        }else{
            listUser = (PageBean)obj;
        }
        return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),listUser);
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
        if(!StringUtils.equals(userInfoMap.get("resCode").toString(),UserEunms.SUCCESS.getResCode())){
            return getBaseResultMaps(String.valueOf(userInfoMap.get("resCode")),String.valueOf(userInfoMap.get("resMsg")),"");
        }else{
            IMUserEntity imUserEntity1 = (IMUserEntity)userInfoMap.get("resData");
            if(org.springframework.util.StringUtils.isEmpty(imUserEntity1)){
                return getBaseResultMaps(UserEunms.USER_NULL.getResCode(),UserEunms.USER_NULL.getResMsg(),"");
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
            return getBaseResultMaps(UserEunms.SMSCODE_EXITS.getResCode(),UserEunms.SMSCODE_EXITS.getResMsg(),"");
        }

        //注册前先判断注册的手机号码是否存在
        IMUserEntity imUserEntity = new IMUserEntity();
        imUserEntity.setImUserPhone(param.getPhone());
        Map<String,Object>  phoneExits = imUserFacade.checkIMUserByPhoneExits(imUserEntity);
        if(!StringUtils.equals(phoneExits.get("resCode").toString(),UserEunms.SUCCESS.getResCode())){
            return getBaseResultMaps(String.valueOf(phoneExits.get("resCode")),String.valueOf(phoneExits.get("resMsg")),"");
        }else{
            if(StringUtils.isEmpty(param.getSmscode())){
                return getBaseResultMaps(UserEunms.SMSCODE_NULL.getResCode(),UserEunms.SMSCODE_NULL.getResMsg(),"");
            }else{
                //如果手机号码不存在，可以注册，开始判断验证码
                String str = String.valueOf(Cache.get(param.getPhone()));
                if("null".equals(str)){  //因为数据是从cacha缓存读取出来的，所以null值判断方式未 “null”
                    return getBaseResultMaps(UserEunms.SMSCODE_EXITS.getResCode(),UserEunms.SMSCODE_EXITS.getResMsg(),"");
                }else{
                    if(param.getSmscode() == str || param.getSmscode().equals(str)) {
                        //如果验证码输入正确，进行注册操作
                        IMUserEntity saveUserBTO = getUserBTO(param);
                        map = imUserFacade.saveIMUserInfo(saveUserBTO);
                    }else{
                        return getBaseResultMaps(UserEunms.SMSCODE_ERROR.getResCode(),UserEunms.SMSCODE_ERROR.getResMsg(),"");
                    }
                }
            }
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
        imUserEntity.setImUserStatus(param.getStatus());
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
