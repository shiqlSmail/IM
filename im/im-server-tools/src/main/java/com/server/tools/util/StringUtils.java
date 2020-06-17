package com.server.tools.util;

import java.security.SecureRandom;
import java.util.Random;


public class StringUtils {
    public static boolean isNull(String param) {
        if (null == param) {
            return true;
        }
        if (null == param.trim() || "".equals(param.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isIntegerNull(Integer param) {
        if (null == param || "".equals(param)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(String param) {
        return !isNull(param);
    }

    public static boolean isNotIntegerNull(Integer param) {
        return !isIntegerNull(param);
    }
    
    
    private static Random random = new Random(new SecureRandom().nextLong());
    
    public static String getRandomCode(int length,char[] alphabet){
        StringBuilder sb = new StringBuilder(length);
        for(int i=0;i<length;i++){
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }
}
