package com.im.user.server.service.biz.impl;

import com.github.pagehelper.PageHelper;
import com.im.user.server.dataconfig.DataSourceType;
import com.im.user.server.dataconfig.MyDataSource;
import com.im.user.server.domain.IMUserEntity;
import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.page.PageBean;
import com.im.user.server.page.PageConvert;
import com.im.user.server.repository.SysEmailCodeRepository;
import com.im.user.server.repository.SysEmailUrlRepository;
import com.im.user.server.repository.SysSmsRepository;
import com.im.user.server.service.biz.SysSmsService;
import com.im.user.server.service.request.EmailCodeRequest;
import com.im.user.server.service.request.SmsCodeRequest;
import com.server.tools.date.DateUtil;
import com.server.tools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("sysSmsService")
public class SysSmsServiceImpl implements SysSmsService {

    @Autowired
    private SysSmsRepository sysSmsRepository;

    @Autowired
    private SysEmailCodeRepository sysEmailCodeRepository;

    @Autowired
    private SysEmailUrlRepository sysEmailUrlRepository;

    /**
     * 保存验证码信息
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MyDataSource(DataSourceType.Master)
    public boolean saveSms(SysSmsEntity record) {
        String uuid = UUIDUtil.primaryKeyUUID();
        record.setSmsCreatetime(DateUtil.getDateTime(new Date()));
        record.setSmsId(uuid);
        record.setSmsStatus(1);
        boolean flag = sysSmsRepository.saveSms(record);
        if(flag){
            record.setSmsId(uuid);
            sysSmsRepository.updateSmsByPhone(record);
        }else{
            return false;
        }
        return true;
    }

    /**
     * 根据手机号码查看验证码信息
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public PageBean<SysSmsEntity> findSmsByPhone(SmsCodeRequest record) {
        PageBean<SysSmsEntity> page = new PageBean<SysSmsEntity>();
        if (null != record.getCurrentPage()) {
            page.setCurrentPage(record.getCurrentPage());
        }
        if (null != record.getPageSize()) {
            page.setPageSize(record.getPageSize());
        }

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

        SysSmsEntity smsEntity = new SysSmsEntity();
        smsEntity.setSmsPhone(record.getPhone());
        List<SysSmsEntity> listSysSmsEntity = sysSmsRepository.findSmsByPhone(smsEntity);
        Integer countSysSmsEntity = sysSmsRepository.countSmsByPhone(smsEntity);

        //返回数据，这样返回才会有分页的参数
        return PageConvert.getPageBean(page,listSysSmsEntity,countSysSmsEntity);
    }


    /**
     * 保存邮件验证码信息
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MyDataSource(DataSourceType.Master)
    public boolean saveEmailCode(SysEmailCodeEntity record) {
        String uuid = UUIDUtil.primaryKeyUUID();
        record.setEmailCreatetime(DateUtil.getDateTime(new Date()));
        record.setEmailId(uuid);
        record.setEmailStatus(1);
        boolean flag = sysEmailCodeRepository.saveEmailCode(record);
        if(flag){
            record.setEmailId(uuid);
            sysEmailCodeRepository.updateEmailCodeByEmailName(record);
        }else{
            return false;
        }
        return true;
    }

    /**
     * 根据邮箱账号查询验证码信息
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public PageBean<SysEmailCodeEntity> findEmailCodeByEmailName(EmailCodeRequest record) {
        PageBean<SysEmailCodeEntity> page = new PageBean<SysEmailCodeEntity>();
        if (null != record.getCurrentPage()) {
            page.setCurrentPage(record.getCurrentPage());
        }
        if (null != record.getPageSize()) {
            page.setPageSize(record.getPageSize());
        }

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

        SysEmailCodeEntity smsEntity = new SysEmailCodeEntity();
        smsEntity.setEmailName(record.getEmail());
        List<SysEmailCodeEntity> listSysEmailCodeEntity = sysEmailCodeRepository.findEmailCodeByEmailName(smsEntity);
        Integer countSysEmailCodeEntity = sysEmailCodeRepository.countEmailCodeByEmailName(smsEntity);

        //返回数据，这样返回才会有分页的参数
        return PageConvert.getPageBean(page,listSysEmailCodeEntity,countSysEmailCodeEntity);
    }

    /**
     * 保存邮件发送的URL信息
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MyDataSource(DataSourceType.Master)
    public boolean saveEmailUrl(SysEmailUrlEntity record) {
        record.setEmailCreatetime(DateUtil.getDateTime(new Date()));
        record.setEmailId(UUIDUtil.primaryKeyUUID());
        record.setEmailStatus(1);
        return sysEmailUrlRepository.saveEmailUrl(record);
    }

    /**
     * 根据邮箱账号查询URL信息
     * @param record
     * @return
     */
    @Override
    @MyDataSource(DataSourceType.Master)
    public PageBean<SysEmailUrlEntity> findEmailByEmailUrl(EmailCodeRequest record) {
        PageBean<SysEmailUrlEntity> page = new PageBean<SysEmailUrlEntity>();
        if (null != record.getCurrentPage()) {
            page.setCurrentPage(record.getCurrentPage());
        }
        if (null != record.getPageSize()) {
            page.setPageSize(record.getPageSize());
        }

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

        SysEmailUrlEntity smsEntity = new SysEmailUrlEntity();
        smsEntity.setEmailName(record.getEmail());
        List<SysEmailUrlEntity> listSysEmailUrlEntity = sysEmailUrlRepository.findEmailByEmailUrl(smsEntity);
        Integer countSysEmailUrlEntity = sysEmailUrlRepository.countEmailByEmailUrl(smsEntity);

        //返回数据，这样返回才会有分页的参数
        return PageConvert.getPageBean(page,listSysEmailUrlEntity,countSysEmailUrlEntity);
    }

    /**
     * 更新验证码信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MyDataSource(DataSourceType.Master)
    public Integer updateSms() {
        return sysSmsRepository.updateSms();
    }

    /**
     * 更新邮箱验证码信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MyDataSource(DataSourceType.Master)
    public Integer updateEmailCode() {
        return sysEmailCodeRepository.updateEmailCode();
    }
}
