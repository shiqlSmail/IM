package com.server.tools.common;



import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapTOSign {
    public static String getSignForMap(Integer appId, String appKey, String appPrivateKey, String appPublicKey){
        Map<String, Object> mapSM = new HashMap<String, Object>();
        mapSM.put("app_id",appId);
        mapSM.put("app_key",appKey);
        mapSM.put("app_private_key",appPrivateKey);
        mapSM.put("app_public_key",appPublicKey);
        return String.valueOf(JSONObject.fromObject(mapSM));
    }
}
