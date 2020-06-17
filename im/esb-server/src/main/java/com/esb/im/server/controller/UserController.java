package com.esb.im.server.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.domain.IMLoginDO;
import com.esb.im.server.request.IMApiIdParam;
import com.esb.im.server.request.IMApiParam;
import com.esb.im.server.request.IMLoginParam;
import com.esb.im.server.service.IMApiService;
import com.esb.im.server.service.IMLoginService;
import com.esb.im.server.system.InterfaceBean;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shiqilong
 */
@RestController
@RequestMapping("/user")
public class UserController  extends InterfaceBean {
    protected static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IMLoginService imLoginService;

    @Autowired(required = false)
    private IMApiService ilonwApiService;

    /**
     * 登录接口
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/login")
    public JSONObject toLogin(@RequestBody IMLoginParam imLoginParam, HttpSession session){
        IMLoginDO imLoginDO = new IMLoginDO();
        imLoginDO.setUsername(imLoginParam.getUsername());
        imLoginDO.setPassword(imLoginParam.getPassword());
        IMLoginDO imLoginDO1 = imLoginService.loginApi(imLoginDO);

        Map<String,Object> resultMap = new HashMap<>();
        if(imLoginDO1 != null){
            session.setAttribute("login_username",imLoginDO1.getUsername());
            session.setAttribute("user_data",imLoginDO1);
            resultMap.put("code","000000");
            resultMap.put("data","登陆成功");
        }else{
            resultMap.put("code","REG_ERROR_999");
            resultMap.put("data","用户不存在");
        }
        return  JSONObject.fromObject(resultMap);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/edit")
    public JSONObject edit(@RequestBody IMLoginParam imLoginParam,HttpSession session){
        Map<String, Object> resultMap = new HashMap<>();
        IMLoginDO imLoginDO = (IMLoginDO)session.getAttribute("user_data");
        if (null == imLoginDO) {
            resultMap.put("code","NOW_LOGIN");
            resultMap.put("data","请先登录");
        }else {
            IMLoginDO imLoginDO1 = new IMLoginDO();
            imLoginDO1.setId(imLoginDO.getId());
            imLoginDO1.setPassword(imLoginParam.getPassword());
            boolean flag = imLoginService.edit(imLoginDO1);
            if (flag == Boolean.TRUE) {
                resultMap.put("code", "000000");
                resultMap.put("data", "修改成功");
            } else {
                resultMap.put("code", "ERROR_EDIT");
                resultMap.put("data", "修改失败");
            }
        }
        return  JSONObject.fromObject(resultMap);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/save")
    public JSONObject save(@RequestBody IMLoginParam imLoginParam){
        Map<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        IMLoginDO imLoginDO = new IMLoginDO();
        imLoginDO.setUsername(imLoginParam.getUsername());
        imLoginDO.setPassword(imLoginParam.getPassword());
        imLoginDO.setRole(1);
        imLoginDO.setCreatetime(sdf.format(new Date()));
        imLoginDO.setUpdatetime(sdf.format(new Date()));
        imLoginDO.setStatus(1);
        boolean flag = imLoginService.save(imLoginDO);
        if (flag == Boolean.TRUE) {
            resultMap.put("code", "000000");
            resultMap.put("data", "新增成功");
        } else {
            resultMap.put("code", "ERROR_EDIT");
            resultMap.put("data", "新增失败");
        }
        return  JSONObject.fromObject(resultMap);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/saveApi")
    public JSONObject saveApi(@RequestBody IMApiParam imApiParam ){
        Map<String, Object> resultMap = new HashMap<>();
        IMApiDO imApiDO = new IMApiDO();
        imApiDO.setApiContext(imApiParam.getApiContext());
        imApiDO.setApiName(imApiParam.getApiName());
        imApiDO.setApiParam(imApiParam.getApiParam());
        imApiDO.setApiStatus(1);
        imApiDO.setApiMenu(imApiParam.getApiMenu());
        imApiDO.setApiTranscode(imApiParam.getApiTranscode());
        imApiDO.setApiUrl(imApiParam.getApiUrl());
        boolean flag = ilonwApiService.save(imApiDO);
        if (flag == Boolean.TRUE) {
            resultMap.put("code", "000000");
            resultMap.put("data", "新增成功");
        } else {
            resultMap.put("code", "ERROR_EDIT");
            resultMap.put("data", "新增失败");
        }
        return  JSONObject.fromObject(resultMap);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/deleteApi")
    public JSONObject deleteApi(@RequestBody IMApiIdParam imApiIdParam){
        Map<String, Object> resultMap = new HashMap<>();
        boolean flag = ilonwApiService.delete(imApiIdParam.getId());
        if (flag == Boolean.TRUE) {
            resultMap.put("code", "000000");
            resultMap.put("data", "删除成功");
        } else {
            resultMap.put("code", "ERROR_DELETE");
            resultMap.put("data", "删除失败");
        }
        return  JSONObject.fromObject(resultMap);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/simulation",method = RequestMethod.POST)
    public JSONObject simulation(@RequestBody IMApiIdParam imApiIdParam){
        try {
            IMApiDO imApiDO = ilonwApiService.selectByPrimaryKey(Integer.valueOf(imApiIdParam.getId()));
            String transCode = imApiDO.getApiTranscode();
            IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);
            String url =ilonwApiDO.getApiUrl();
            Integer status =ilonwApiDO.getApiStatus();
            log.info("获取到的URL信息为："+url);
            Map<String,Object> map = (Map) JSON.parse(imApiDO.getApiParam());
            String  response = toSendPost(url,map,status);
            log.info("返回的数据为："+response);
            return  JSONObject.fromObject(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}