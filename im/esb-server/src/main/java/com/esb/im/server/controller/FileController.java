package com.esb.im.server.controller;

import com.esb.im.server.common.DateUtils;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.service.IMApiService;
import com.esb.im.server.system.InterfaceBean;
import com.server.tools.util.UUIDUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制类
 *
 * @author shiqilong
 */
@RestController
public class FileController extends InterfaceBean {

    @Autowired
    private IMApiService ilonwApiService;

    /**
     * 动态接口
     * @param
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public net.sf.json.JSONObject fileUpload(@RequestParam(value = "file",required = false) MultipartFile files[], @Param("transCode") String transCode){
        net.sf.json.JSONObject objResult = null;
        String secNos = DateUtils.format(new Date(),"yyyyMMdd") + DateUtils.format(new Date(),"sss") + UUIDUtil.getOrderIdByUUId();
        if(files.length == 0){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("code","FILE_NULL_999");
            resultMap.put("data","文件列表为空");
            resultMap.put("secNo",secNos);
            objResult = net.sf.json.JSONObject.fromObject(resultMap);
            return objResult;
        }
       try{
           //首先判断上传图片的个数
           net.sf.json.JSONObject jsonObject = fileLength(files.length,secNos);
           if(null != jsonObject){
               return jsonObject;
           }
           IMApiDO ilonwApiDO = ilonwApiService.selectByTransCode(transCode);
           String url =ilonwApiDO.getApiUrl();
           log.info("获取到的URL信息为："+url);
           Integer status =ilonwApiDO.getApiStatus();
           String result = uploadFileToSendPost(url,files, status);

           if(org.thymeleaf.util.StringUtils.isEmpty(result)){
               Map<String, Object> resultMap = new HashMap<String, Object>();
               resultMap.put("code","UPLOAD_FILE_999");
               resultMap.put("data","文件上传失败");
               resultMap.put("secNo",secNos);
               objResult = net.sf.json.JSONObject.fromObject(resultMap);
           }else{
               objResult = net.sf.json.JSONObject.fromObject(result);
           }
           log.info("返回的数据为："+objResult);
       }catch (Exception e){
           e.printStackTrace();
       }
        return objResult;
    }

    private net.sf.json.JSONObject fileLength(Integer count, String secNos){
        if(count > 9){
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("code","FILE_COUNT_999");
            returnMap.put("data","上传文件个数超过限制，最多上传【9】个文件");
            returnMap.put("secNo",secNos);
            return net.sf.json.JSONObject.fromObject(returnMap);
        }
        return null;
    }
}