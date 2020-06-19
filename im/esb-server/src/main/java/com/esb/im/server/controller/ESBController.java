package com.esb.im.server.controller;

import com.alibaba.fastjson.JSON;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.service.IMApiService;
import com.esb.im.server.system.InterfaceBean;
import com.esb.im.server.xml.AnalysisFactory;
import com.server.tools.result.InterfaceResultData;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ESBController extends InterfaceBean {
    protected static final Logger log = LoggerFactory.getLogger(ESBController.class);

    @Autowired(required = false)
    private IMApiService ilonwApiService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/xml",method = RequestMethod.POST)
    public String xml(@RequestBody String xml){
        try {
            log.info("请求的xml信息为："+xml);
            //解析XML
            Map map = AnalysisFactory.parseXml(xml);
            //获取head节点
            Map<String,Object> headMap = net.sf.json.JSONObject.fromObject(map.get("SERVICE"));
            //获取apphead节点
            Map<String,Object> appHeadMap = net.sf.json.JSONObject.fromObject(headMap.get("HEAD"));
            Map<String,Object> resultMap = net.sf.json.JSONObject.fromObject(appHeadMap.get("SYS_HEAD"));

            String transCode = String.valueOf(resultMap.get("SYS_CHANNEL_ID"));
            IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);

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
        try {
            Map<String,Object> map = (Map) JSON.parse(json);
            String transCode = map.get("trans_code").toString();
            IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);
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
}
