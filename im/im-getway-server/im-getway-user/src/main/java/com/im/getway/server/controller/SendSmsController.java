package com.im.getway.server.controller;

import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.getway.server.request.PhoneSmsParam;
import com.im.user.server.domain.SysSmsEntity;
import com.im.user.server.facade.SysSmsFacade;
import com.server.tools.cache.Cache;
import com.server.tools.result.APIBaseResult;
import com.server.tools.send.SendSmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/send")
@Api(description = "短信接口")
public class SendSmsController extends BaseController {
    @Resource
    private SysSmsFacade sysSmsFacade;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/sms", produces = "application/json", method = {RequestMethod.POST})
    @ApiOperation(value="发送短信验证码", notes="发送短信接口详细描述")
    public Map<String, Object> sendSms(@RequestBody @Valid PhoneSmsParam param) {
        long now = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        try {
            int code = Integer.valueOf(param.getSmscode());
            /*
            SendSmsUtils sms = new SendSmsUtils();
            boolean isFlag = sms.send(param.getPhone(), code);*/
            boolean isFlag = true;
            if (isFlag) {
                map.put("resCode", "BIZ_ADMIN_SUCCESS");
                map.put("resMsg", "success");
                map.put("resData", code);

                SysSmsEntity BO = new SysSmsEntity();
                BO.setSmsCode(code);
                BO.setSmsPhone(param.getPhone());
                sysSmsFacade.saveSms(BO);
            } else {
                map.put("resMsg", "验证码发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getIntefaceData(param,map,"/send"+"/sms",now,"发送短信验证码");
    }
}
