package com.server.tools.result;

import com.server.tools.date.DateUtil;

import java.util.Date;

public class SetAPIResultUtil {
    public static void setFail(APIBaseResult result) {
        result.setSysCode(ReturnCode.FAIL);
        result.setSysMessage(ReturnCode.MSG_FAIL);
        result.setTimes(DateUtil.getDateTime(new Date()));
    }


    public static void setSuccess(APIBaseResult result) {
        result.setSysCode(ReturnCode.SUCCESS);
        result.setSysMessage(ReturnCode.MSG_SUCCESS);
        result.setTimes(DateUtil.getDateTime(new Date()));
    }
}
