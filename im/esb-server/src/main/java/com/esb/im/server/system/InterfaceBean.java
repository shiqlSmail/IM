package com.esb.im.server.system;

import com.alibaba.fastjson.JSON;
import com.esb.im.server.service.IMSysConfigService;
import com.server.tools.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口bean，用于统一接口调用
 */
public class InterfaceBean extends HttpRequestUtils{

    protected static final Logger log = LoggerFactory.getLogger(InterfaceBean.class);

    @Autowired(required = false)
    private IMSysConfigService imSysConfigService;

    /**
     * 发送post请求
     * @param desUrl
     * @param map
     * @return
     */
    public static String toSendPost(String desUrl,Map<String,Object> map,Integer status){
        HttpRespons  httpRespons = new HttpRespons();
        try {
            HttpRequester request = new HttpRequester();
            if(status == 1){
                httpRespons = request.sendPost(desUrl,map);
            }else{
                return "接口已停用";
            }
            log.info("post返回结果为："+httpRespons.getContent());
        } catch(ConnectException tex){
            //单独处理超时
            return setTimeOut();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return httpRespons.getContent();
    }


    /**
     * 文件上传发送post请求
     * @param desUrl
     * @return
     */
    public static String uploadFileToSendPost(String desUrl, MultipartFile files[], Integer status){
        String  httpRespons = new String();
        try {
            HttpRequester request = new HttpRequester();
            if(status == 1){
                httpRespons = request.uploadFileSendPost(desUrl,files);
            }else{
                return "接口已停用";
            }
            log.info("post返回结果为："+httpRespons);
        }catch(ConnectException tex){
            //单独处理超时
            return setTimeOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpRespons;
    }

    /**
     * 设置超时单独处理
     * @return
     */
    public static String setTimeOut(){
        //单独处理超时
        Map<String,Object> timeOutMap = new HashMap<>();
        timeOutMap.put("resCode","TIMEOUT_999999");
        timeOutMap.put("resMessage","【IM】服务正在重启，请稍后再试");
        timeOutMap.put("resTimes", DateUtil.getDateTime(new Date()));

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg", "交易成功");
        map.put("status", 100101);
        map.put("data", timeOutMap);
        return JSON.toJSONString(map);
    }

    public String configCodeByValue(String code){
        return imSysConfigService.findConfigByCode(code);
    }
}