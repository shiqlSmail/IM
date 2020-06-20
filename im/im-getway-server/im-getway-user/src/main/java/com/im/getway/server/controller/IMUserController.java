package com.im.getway.server.controller;

import com.im.getway.server.base.BaseController;
import com.im.getway.server.request.*;
import com.im.getway.server.service.IMUserService;
import com.im.user.server.page.PageData;
import com.server.tools.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping(value="/user")
@Api(description = "im用户接口")
public class IMUserController extends BaseController {
    @Autowired
    public IMUserService imUserService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/query_user", method = RequestMethod.POST)
    @ApiOperation(value="查询所有用户接口", notes="用户接口详细描述")
    public Map<String,Object> queryAllUser( @RequestBody PageData pageData) {
        long now = System.currentTimeMillis();
        Map<String,Object> map = imUserService.queryAllUser(pageData);
        return getIntefaceData(pageData,map,"/user"+"/query_user",now,"查询所有用户接口");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/update_password", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="用户忘记密码，使用手机号码修改密码接口", notes="用户修改密码")
    public Map<String,Object> ilonwUserUpdatePassword(@RequestBody ForgetPassParam param) {
        long now = System.currentTimeMillis();
        Map<String,Object> map = imUserService.ilonwUserUpdatePassword(param);
        return getIntefaceData(param,map,"/user"+"/update_password",now,"用户修改密码");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/update_status", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="管理员修改用户状态接口", notes="用户状态修改")
    public Map<String,Object> ilonwUserUpdateStatus(@RequestBody UserIdParam param) {
        long now = System.currentTimeMillis();
        Map<String,Object> map = imUserService.updateUserStatus(param);
        return getIntefaceData(param,map,"/user"+"/update_status",now,"用户状态修改");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/findUserById", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="管理员根据用户ID查询用户信息接口", notes="根据用户ID查询用户")
    public Map<String,Object> findUserById(@RequestBody UserIdParam param) {
        long now = System.currentTimeMillis();
        Map<String,Object> map = imUserService.loginIlonwUserById(param);
        return getIntefaceData(param,map,"/user"+"/findUserById",now,"根据用户ID查询用户");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="用户登录接口", notes="用户登录：可使用手机/邮箱/用户号进行登录")
    public Map<String,Object> loginIlonwUserByPhoneAndEmailAndCode(@RequestBody IlonwLoginParam param){
        long now = System.currentTimeMillis();
        Map<String,Object> map = imUserService.loginIlonwUserByPhoneAndEmailAndCode(param);
        return getIntefaceData(param,map,"/user"+"/login",now,"用户登录接口");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/check_phone", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="用户注册判断手机号码是否存在接口", notes="用户注册")
    public Map<String,Object> registerIlonwUserCheckPhoneisExtis(@RequestBody CheckPhoneParam param) {
        long now = System.currentTimeMillis();
        Map<String,Object> map  = imUserService.registerIlonwUserCheckPhoneisExtis(param);
        return getIntefaceData(param,map,"/user"+"/check_phone",now,"用户注册判断手机号码是否存在接口");
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/register", method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="用户注册接口", notes="用户注册")
    public Map<String,Object> registerIlonwUserInfo(@RequestBody SysIlonwSaveUserParam param) {
        long now = System.currentTimeMillis();
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        param.setIp(IpUtils.getIpAddr(request));
        Map<String,Object>  map = imUserService.registerIlonwUserInfo(param);
        return getIntefaceData(param,map,"/user"+"/register",now,"用户注册");
    }
}
