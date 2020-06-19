package com.server.tools.result;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class InterfaceResultData {

    public static com.alibaba.fastjson.JSONObject getResultToJson(String response){
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response);
        Object  str = null;
        Integer  code = (Integer)jsonObject.get("status");
        if(code == 200){
            String resDataStr = String.valueOf(jsonObject.get("data"));
            com.alibaba.fastjson.JSONObject resDataJSON = com.alibaba.fastjson.JSONObject.parseObject(resDataStr);
            String  resCode = (String)resDataJSON.get("resCode");
            if(StringUtils.equals(resCode,"000000")){
                str = resDataJSON.get("resData");
            }else{
                str = resDataStr;
            }
        }else if(code == 100101){
            str = jsonObject.get("data");
        }else{
            str = jsonObject.get("msg");
        }
        return  com.alibaba.fastjson.JSONObject.parseObject(str.toString());
    }

    public static String getResultToString(String response){
        JSONObject jsonObject = JSONObject.parseObject(response);
        Object  str = null;
        Integer  code = (Integer)jsonObject.get("status");
        if(code == 200){
            String resDataStr = String.valueOf(jsonObject.get("data"));
            JSONObject resDataJSON = JSONObject.parseObject(resDataStr);
            String  resCode = (String)resDataJSON.get("resCode");
            if(StringUtils.equals(resCode,"000000")){
                str = resDataJSON.get("resData");
            }else{
                str = resDataStr;
            }
        }else if(code == 100101){
            str = jsonObject.get("data");
        }else{
            str = jsonObject.get("msg");
        }
        return str.toString();
    }
}
