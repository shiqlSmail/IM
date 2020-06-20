package com.server.tools.result;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class InterfaceResultData {

    public static com.alibaba.fastjson.JSONObject getResultToJson(String response){
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response);
        String redCode = String.valueOf(jsonObject.get("resCode"));
        if(StringUtils.equals(redCode,"BIZ_ADMIN_SUCCESS")){
            return jsonObject;
        }else if(StringUtils.equals(redCode,"BIZ_SUCCESS")){
            return  com.alibaba.fastjson.JSONObject.parseObject(String.valueOf(jsonObject.get("resData")));
        }else{
            Object codeStr = jsonObject.get("status");
            if(null == codeStr || "".equals(codeStr)){
                return jsonObject;
            }
            Integer  code = Integer.valueOf(codeStr.toString());
            Object  str = null;
            if(code == 200){
                String resDataStr = String.valueOf(jsonObject.get("data"));
                com.alibaba.fastjson.JSONObject resDataJSON = com.alibaba.fastjson.JSONObject.parseObject(resDataStr);
                String  resCode = (String)resDataJSON.get("resCode");
                if(StringUtils.equals(resCode,"BIZ_SUCCESS")){
                    str = resDataJSON.get("resData");
                }else{
                    str = resDataStr;
                }
            }else if(code == 100101){
                str = jsonObject.get("data");
            }else{
                str = jsonObject.get("msg");
            }
            return  com.alibaba.fastjson.JSONObject.parseObject(String.valueOf(str));
        }
    }

    public static String getResultToString(String response){
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response);
        String redCode = String.valueOf(jsonObject.get("resCode"));
        if(StringUtils.equals(redCode,"BIZ_ADMIN_SUCCESS")){
            return jsonObject.toJSONString();
        }else if(StringUtils.equals(redCode,"BIZ_SUCCESS")){
            return  String.valueOf(jsonObject.get("resData"));
        }else{
            Object codeStr = jsonObject.get("status");
            if(null == codeStr || "".equals(codeStr)){
                return jsonObject.toJSONString();
            }
            Integer  code = Integer.valueOf(codeStr.toString());
            Object  str = null;
            if(code == 200){
                String resDataStr = String.valueOf(jsonObject.get("data"));
                com.alibaba.fastjson.JSONObject resDataJSON = com.alibaba.fastjson.JSONObject.parseObject(resDataStr);
                String  resCode = (String)resDataJSON.get("resCode");
                if(StringUtils.equals(resCode,"BIZ_SUCCESS")){
                    str = resDataJSON.get("resData");
                }else{
                    str = resDataStr;
                }
            }else if(code == 100101){
                str = jsonObject.get("data");
            }else{
                str = jsonObject.get("msg");
            }
            return  String.valueOf(str);
        }
    }
}
