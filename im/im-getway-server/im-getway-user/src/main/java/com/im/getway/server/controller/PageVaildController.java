package com.im.getway.server.controller;

import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.facade.SysSmsFacade;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
     * @param phone
     * @return
     */
    @GetMapping(value="/findSmsByPhone",produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findSmsByPhone(@RequestParam("phone") String phone) {
        long now = System.currentTimeMillis();
        SysSmsEntity BO = new SysSmsEntity();
        BO.setSmsPhone(phone);
        List<SysSmsEntity> listBO = sysSmsFacade.findSmsByPhone(BO);
        return getIntefaceDataToJson(phone,listBO,"/vaild"+"/findSmsByPhone",now,"查询手机验证码详细描述");
    }



    /**
     * 查看邮件，点击邮件链接激活
     * @param email
     * @return
     */
    @GetMapping(value="/emailurl",produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findEmailUrl(@RequestParam("email") String email) {
        long now = System.currentTimeMillis();
        SysEmailUrlEntity BO = new SysEmailUrlEntity();
        BO.setEmailName(email);
        List<SysEmailUrlEntity> listBO = sysSmsFacade.findEmailByEmailUrl(BO);
        return getIntefaceDataToJson(email,listBO,"/vaild"+"/emailurl",now,"查看邮件，点击邮件链接激活详细描述");
    }


    /**
     * 发送邮件验证码
     * @param email
     * @return
     */
    @GetMapping(value="/emailyzm")
    public JsonResult findEmailYzm(@RequestParam("email") String email) {
        long now = System.currentTimeMillis();
        SysEmailCodeEntity BO = new SysEmailCodeEntity();
        BO.setEmailName(email);
        List<SysEmailCodeEntity> listBO = sysSmsFacade.findEmailCodeByEmailName(BO);
        return getIntefaceDataToJson(email,listBO,"/vaild"+"/emailyzm",now,"发送邮件验证码详细描述");
    }
}
