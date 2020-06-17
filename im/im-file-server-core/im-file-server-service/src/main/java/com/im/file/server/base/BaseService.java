package com.im.file.server.base;

import java.util.HashMap;
import java.util.Map;

public class BaseService {
    public Map<String, Object> getBaseResultMaps(String code,String msg,Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resCode", code);
        map.put("resMsg", msg);
        map.put("resData", object);
        return map;
    }
}
