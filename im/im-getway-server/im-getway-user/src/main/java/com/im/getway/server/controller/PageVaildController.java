package com.im.getway.server.controller;

import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.facade.SysSmsFacade;
import com.im.user.server.page.PageBean;
import com.im.user.server.service.request.EmailCodeRequest;
import com.im.user.server.service.request.SmsCodeRequest;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 短信/邮箱查询
 * @author: shiqilong / jackSmail
 * @create: 2020-02-12 21:22
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value="/vaild")
@Api(description = "ilonw短信/邮箱查询接口")
public class PageVaildController  extends BaseController {
    @Resource
    private SysSmsFacade sysSmsFacade;

    /**
     * 查询手机验证码
     * @param record
     * @return
     */
    @PostMapping(value="/findSmsByPhone",produces = MediaType.APPLICATION_JSON_VALUE)
   public JsonResult findSmsByPhone(@RequestBody SmsCodeRequest record) {
        long now = System.currentTimeMillis();
        PageBean<SysSmsEntity> listBO = sysSmsFacade.findSmsByPhone(record);
        return getIntefaceDataToJson(record,listBO,"/vaild"+"/findSmsByPhone",now,"查询手机验证码详细描述");
    }



    /**
     * 查看邮件，点击邮件链接激活
     * @param record
     * @return
     */
    @PostMapping(value="/emailurl",produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findEmailUrl(@RequestBody EmailCodeRequest record) {
        long now = System.currentTimeMillis();
        PageBean<SysEmailUrlEntity> listBO = sysSmsFacade.findEmailByEmailUrl(record);
        return getIntefaceDataToJson(record,listBO,"/vaild"+"/emailurl",now,"查看邮件，点击邮件链接激活详细描述");
    }


    /**
     * 发送邮件验证码
     * @param record
     * @return
     */
    @PostMapping(value="/emailyzm")
    public JsonResult findEmailYzm(@RequestBody EmailCodeRequest record) {
        long now = System.currentTimeMillis();
        PageBean<SysEmailCodeEntity> listBO = sysSmsFacade.findEmailCodeByEmailName(record);
        return getIntefaceDataToJson(record,listBO,"/vaild"+"/emailyzm",now,"发送邮件验证码详细描述");
    }
}
