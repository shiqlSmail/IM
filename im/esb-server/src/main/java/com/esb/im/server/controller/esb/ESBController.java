package com.esb.im.server.controller.esb;

import com.alibaba.fastjson.JSON;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.service.IMApiService;
import com.esb.im.server.system.InterfaceBean;
import com.esb.im.server.xml.AnalysisFactory;
import com.server.tools.crypto.SM2;
import com.server.tools.crypto.Util;
import com.server.tools.result.InterfaceResultData;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class ESBController extends InterfaceBean {
    protected static final Logger log = LoggerFactory.getLogger(ESBController.class);

    @Autowired(required = false)
    private IMApiService ilonwApiService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/xml",method = RequestMethod.POST)
    public String xml(@RequestBody String xml){
        //先解密
        Map<String,String> maps = getAppInfo(xml);
        if(StringUtils.equals(maps.get("code"),"SIGN-999999")){
            return JSON.toJSONString(maps);
        }
        try {
            xml = maps.get("data");
            log.info("请求的xml信息为："+xml);
            //解析XML
            Map map = AnalysisFactory.parseXml(xml);
            //获取head节点
            Map<String,Object> headMap = net.sf.json.JSONObject.fromObject(map.get("SERVICE"));
            //获取apphead节点
            Map<String,Object> appHeadMap = net.sf.json.JSONObject.fromObject(headMap.get("HEAD"));
            Map<String,Object> resultMap = net.sf.json.JSONObject.fromObject(appHeadMap.get("SYS_HEAD"));

            String appIp = String.valueOf(resultMap.get("APP_IP"));

            //判断是否启用白名单
            if(!StringUtils.isEmpty(configCodeByValue("CONFIG_WHITE_LIST"))){
                //不为空，代表启用了，进行白名单IP判断
                if(!configCodeByValue("CONFIG_WHITE_LIST").contains(appIp)){
                    Map<String, String> ipMaps = new HashMap<String, String>();
                    ipMaps.put("data", "非法请求");
                    ipMaps.put("code", "ILLEGAL-REQUEST");
                    return JSON.toJSONString(ipMaps);
                }
            }
            String transCode = String.valueOf(resultMap.get("SYS_CHANNEL_ID"));
            IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);
            if(null == ilonwApiDO){
                Map<String,String> resultMap1 = new HashMap<>();
                resultMap.put("data", "服务识别失败");
                resultMap.put("code", "SERVER-ERROR");
                return JSON.toJSONString(resultMap);
            }

            String url =ilonwApiDO.getApiUrl();
            Integer status =ilonwApiDO.getApiStatus();
            Map<String,Object> bodyMap = net.sf.json.JSONObject.fromObject(headMap.get("BODY"));

            String  response = toSendPost(url,bodyMap,status);
            return InterfaceResultData.getResultToString(response);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public String json(@RequestBody String json){
        //先解密
        Map<String,String> maps = getAppInfo(json);
        if(StringUtils.equals(maps.get("code"),"SIGN-999999")){
            return JSON.toJSONString(maps);
        }
        try {
            json = maps.get("data");

            Map<String,Object> map = (Map) JSON.parse(json);
            String transCode = map.get("trans_code").toString();
            IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);
            if(null == ilonwApiDO){
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("data", "服务识别失败");
                resultMap.put("code", "SERVER-ERROR");
                return JSON.toJSONString(resultMap);
            }
            String url =ilonwApiDO.getApiUrl();
            Integer status =ilonwApiDO.getApiStatus();
            //获取完trans_code,从map集合中取出trans_code
            map.remove("trans_code");
            log.info("获取到的URL信息为："+url);
            String  response = toSendPost(url,map,status);
            return InterfaceResultData.getResultToString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getAppInfo(String signParan) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            //调用解密方法
            SM2 sm02 = new SM2();

            JSONObject jsonObject = JSONObject.fromObject(signParan.trim());
            HashMap<String, String> maps = JsonObjectToHashMap(jsonObject);

            String sign = "";
            if(StringUtils.equals(maps.get("code"),"SIGN_SUCCESS")){
                sign = maps.get("data");
            }else{
                map.put("data", "签名异常");
                map.put("code", "SIGN-999999");
                return map;
            }
            log.info("-----------------开始进行解密操作-----------------");
           // BigInteger privateKey = sm02.importPrivateKey("/usr/src/certificate/privatekey.pem");
            BigInteger privateKey = sm02.importPrivateKey("/usr/src/crypto/esb-privatekey.pem");
            byte[] data = Util.hexStringToBytes(sign);
            String appInfo = sm02.decrypt(data, privateKey);
            log.info("解密完成的数据为：" + appInfo);
            map.put("data", appInfo);
            map.put("code", "sign_success");
            log.info("-----------------结束解密操作-----------------");
        } catch (Exception e) {
            log.info("解密异常：" + e.getMessage());
            map.put("data", "签名异常");
            map.put("code", "SIGN-999999");
            e.printStackTrace();
        }
        return map;
    }

    //1.將JSONObject對象轉換為HashMap<String,String>
    public static HashMap<String, String> JsonObjectToHashMap(JSONObject jsonObj){
        HashMap<String, String> data = new HashMap<String, String>();
        Iterator it = jsonObj.keys();
        while(it.hasNext()){
            String key = String.valueOf(it.next().toString());
            String value = jsonObj.get(key).toString();
            data.put(key, value);
        }
        System.out.println(data);
        return data;
    }
}
