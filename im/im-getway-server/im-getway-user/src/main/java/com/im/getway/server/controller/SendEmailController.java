package com.im.getway.server.controller;

import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.getway.server.request.EmailSendParam;
import com.im.user.server.domain.SysEmailCodeEntity;
import com.im.user.server.domain.SysEmailUrlEntity;
import com.im.user.server.facade.SysSmsFacade;
import com.server.tools.send.SendEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/send")
@Api(description = "邮件接口")
public class SendEmailController extends BaseController {
    @Resource
    private SysSmsFacade sysSmsFacade;

    /**
     * 发送邮件，点击邮件链接激活
     * @param param
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/emailUrl", produces = "application/json", method = {RequestMethod.POST})
    @ApiOperation(value="发送邮件激活链接", notes="发送邮件接口详细描述")
    public JsonResult sendEmailUrl(@RequestBody @Valid EmailSendParam param) {
        long now = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        try {
            SendEmailUtils email = new SendEmailUtils();
            boolean isFlag = email.sendAccountActivateEmailToUrl(param.getEmail(),param.getUrl(),param.getUsername());
            if (isFlag) {
                map.put("resMsg", "邮件发送成功");
                map.put("resCode", "000000");
                map.put("resData", "激活链接已发送你的【"+param.getEmail()+"】邮箱，请注意查收！");
            } else {
                map.put("resMsg", "邮件发送失败");
                map.put("resCode", "000000");
                map.put("resData", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysEmailUrlEntity BO = new SysEmailUrlEntity();
        BO.setEmailUrl(param.getUsername() + "先生/女士您好，请点击此链接激活账号" + "<a target='_BLANK' href='" + param.getUrl() + "'>" + param.getUrl() + "</a>");
        BO.setEmailName(param.getEmail());
        sysSmsFacade.saveEmailUrl(BO);
        return getIntefaceData(param,map,"/send"+"/emailUrl",now,"发送邮件激活链接");
    }


    /**
     * 发送邮件验证码
     * @param param
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/emailYzm", produces = "application/json", method = {RequestMethod.POST})
    @ApiOperation(value="发送邮件验证码", notes="发送邮件接口详细描述")
    public JsonResult sendEmailYzm(@RequestBody @Valid EmailSendParam param) {
        long now = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        try {
            SendEmailUtils email = new SendEmailUtils();
            boolean isFlag = email.sendAccountActivateEmailToYzm(param.getEmail(),String.valueOf(code),param.getUsername());
            if (isFlag) {
                map.put("resMsg", "短信发送成功");
                map.put("resCode", "SUCCESS");
                map.put("resData", "你的验证码是【"+code+"】！");
            } else {
                map.put("resMsg", "短信发送失败");
                map.put("resCode", "SUCCESS");
                map.put("resData", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysEmailCodeEntity BO = new SysEmailCodeEntity();
        BO.setEmailCode(code);
        BO.setEmailName(param.getEmail());
        return getIntefaceData(param,map,"/send"+"/emailYzm",now,"发送邮件验证码");
    }
}
