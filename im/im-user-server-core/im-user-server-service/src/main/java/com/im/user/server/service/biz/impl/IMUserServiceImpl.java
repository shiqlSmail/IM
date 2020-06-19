package com.im.user.server.service.biz.impl;

import com.github.pagehelper.PageHelper;
import com.im.user.server.dataconfig.DataSourceType;
import com.im.user.server.dataconfig.MyDataSource;
import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.page.PageBean;
import com.im.user.server.page.PageConvert;
import com.im.user.server.page.PageData;
import com.im.user.server.repository.IMUserRepository;
import com.im.user.server.service.base.BaseService;
import com.im.user.server.service.biz.IMUserService;
import com.im.user.server.service.eunms.UserEunms;
import com.server.tools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("imUserService")
public class IMUserServiceImpl extends BaseService implements IMUserService {

    @Autowired
    private IMUserRepository imUserRepository;

    @Override
    @MyDataSource(DataSourceType.Master)
    public PageBean<IMUserEntity> findAllIMUserInfo(PageData pageData) {
        PageBean<IMUserEntity> page = new PageBean<IMUserEntity>();
        if (null != pageData.getCurrentPage()) {
            page.setCurrentPage(pageData.getCurrentPage());
        }
        if (null != pageData.getPageSize()) {
            page.setPageSize(pageData.getPageSize());
        }

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<IMUserEntity> listUser = imUserRepository.findAllIMUserInfo();
        Integer countUser = imUserRepository.countAllIMUserInfo();

        //返回数据，这样返回才会有分页的参数
        return PageConvert.getPageBean(page,listUser,countUser);
    }

    /**
     * 用户登录  根据账号密码
     *
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public Map<String, Object> loginIMUserByPhoneAndEmailAndCode(IMUserEntity record) {
        //先判断手机号码、邮箱、code是否存在
        Integer phoneAndEmailAndCodeIsExits = imUserRepository.checkIMUserByPhoneAndEmailAndCodeExits(record);

        if (phoneAndEmailAndCodeIsExits.equals(0)) {
            return getBaseResultMaps(UserEunms.LOGIN_CODE_NOTEXITS.getResCode(),UserEunms.LOGIN_CODE_NOTEXITS.getResMsg(),phoneAndEmailAndCodeIsExits);
        }

        IMUserEntity IMUser = imUserRepository.loginIMUserByPhoneAndPassword(record);
        if (StringUtils.isEmpty(IMUser)) {
            return getBaseResultMaps(UserEunms.PASSWORD_FAIL.getResCode(),UserEunms.PASSWORD_FAIL.getResMsg(),IMUser);
        }
        return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),IMUser);
    }

    /**
     * 用户登录  根据手机号验证码
     *
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public Map<String, Object> loginIMUserByPhone(IMUserEntity record) {
        //先判断手机号码是否存在
        Integer phoneAndEmailAndCodeIsExits = imUserRepository.checkIMUserByPhoneExits(record);
        if (null == phoneAndEmailAndCodeIsExits || phoneAndEmailAndCodeIsExits == 0) {
            return getBaseResultMaps(UserEunms.PHONE_ISNULL.getResCode(),UserEunms.PHONE_ISNULL.getResMsg(),phoneAndEmailAndCodeIsExits);
        }else{
            IMUserEntity IMUser = imUserRepository.loginIMUserByPhoneAndSMSCode(record);
            if (null == IMUser) {
                return getBaseResultMaps(UserEunms.USER_NULL.getResCode(),UserEunms.USER_NULL.getResMsg(),IMUser);
            } else {
                return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),IMUser);
            }
        }
    }

    /**
     * 检查注册手机号码是否存在
     *
     * @param param
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public Map<String, Object> checkIMUserByPhoneExits(IMUserEntity param) {
        Integer num = imUserRepository.checkIMUserByPhoneExits(param);
        if (num == 0) {
            return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),num);
        } else {
            return getBaseResultMaps(UserEunms.PHONE_NOTNULL.getResCode(),UserEunms.PHONE_NOTNULL.getResMsg(),num);
        }
    }

    /**
     * 检查注册邮箱是否存在
     *
     * @param param
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public Map<String, Object> checkIMUserByEmailExits(IMUserEntity param) {
        Integer num = imUserRepository.checkIMUserByEmailExits(param);
        if (num == 0) {
            return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),num);
        } else {
            return getBaseResultMaps(UserEunms.EMAIL_NOTNULL.getResCode(),UserEunms.EMAIL_NOTNULL.getResMsg(),num);
        }
    }

    /**
     * 用户注册
     *
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    @Transactional
    public Map<String, Object> saveIMUserInfo(IMUserEntity record) {
        String codes = UUIDUtil.userCode(7);
        try{
            //判断code是否存在
            Integer flagCode = imUserRepository.checkIMUserByCodeExits(record);
            if (flagCode.equals(0)) {
                record.setImUserCode(codes);
            } else {
                record.setImUserCode(codes + UUIDUtil.userCode(2));
            }

            record.setImUserId(UUIDUtil.primaryKeyUUID());
            record.setImUserStatus(1);
            record.setImUserCreatetime(new Date());
            record.setImUserPassword(record.getImUserPassword());

            //判断注册用户的手机号码是否存在
            Integer flagPhone = imUserRepository.checkIMUserByPhoneExits(record);
            if (flagPhone.equals(0)) {
                //不存在
                boolean saveUser = imUserRepository.saveIMUserInfo(record);
                if(saveUser){
                    return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),saveUser);
                }else{
                    return getBaseResultMaps(UserEunms.REGISTER_FAIL.getResCode(),UserEunms.REGISTER_FAIL.getResMsg(),"");
                }
            }else{
                return getBaseResultMaps(UserEunms.PHONE_NOTNULL.getResCode(),UserEunms.PHONE_NOTNULL.getResMsg(),"");
            }
        }catch(Exception e){
            return getBaseResultMaps(UserEunms.ERROR.getResCode(),UserEunms.ERROR.getResMsg(),e.getMessage());
        }
    }

    /**
     * 忘记密码，根据手机号码进行修改
     *
     * @param param
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    @Transactional
    public Map<String, Object> updateIMUserPasswordById(IMUserEntity param) {
        Integer num = imUserRepository.updateIMUserPasswordById(param);
        if (num > 0) {
            return getBaseResultMaps(UserEunms.SUCCESS.getResCode(),UserEunms.SUCCESS.getResMsg(),num);
        } else {
            return getBaseResultMaps(UserEunms.PASSWORD_UPDATE_FAIL.getResCode(),UserEunms.PASSWORD_UPDATE_FAIL.getResMsg(),num);
        }
    }

    @Override
    @MyDataSource(DataSourceType.Master)
    @Transactional
    public Map<String, Object> updateUserStatus(IMUserEntity record) {
        Integer num = imUserRepository.updateUserStatus(record);
        if (num > 0) {
            return getBaseResultMaps(UserEunms.ADMIN_SUCCESS.getResCode(),UserEunms.ADMIN_SUCCESS.getResMsg(),num);
        } else {
            return getBaseResultMaps(UserEunms.FAIL.getResCode(),UserEunms.FAIL.getResMsg(),num);
        }
    }

    /**
     * 根据用户ID查询用户信息
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public Map<String, Object> loginIMUserById(IMUserEntity record) {
        IMUserEntity IMUserEntity = imUserRepository.loginIMUserById(record);
        if (!StringUtils.isEmpty(IMUserEntity)) {
            return getBaseResultMaps(UserEunms.ADMIN_SUCCESS.getResCode(),UserEunms.ADMIN_SUCCESS.getResMsg(),IMUserEntity);
        } else {
            return getBaseResultMaps(UserEunms.FAIL.getResCode(),UserEunms.FAIL.getResMsg(),IMUserEntity);
        }
    }
}
