package com.im.getway.server.base;

import com.alibaba.fastjson.JSON;
import com.im.user.server.domain.SysIMUserLogsEntity;
import com.im.user.server.facade.SysIMUserLogsFacade;
import com.server.tools.common.SelNoFactory;
import com.server.tools.util.IpUtils;
import com.server.tools.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 日志保存
 * @author shiql
 */
public abstract class BaseController extends ResultResponse {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    SysIMUserLogsFacade sysIMUserLogsFacade;

    public JsonResult getIntefaceData(Object requestParam,Map<String, Object> responseResult, String responseMethod, long now, String type) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            //保存日志
            saveLogs(request,JSON.toJSON(requestParam),JSON.toJSON(responseResult),responseMethod,"SUCCESS",System.currentTimeMillis() - now,type);
            return JsonResult.ok(responseResult);
        } catch (Exception e) {
            //保存日志
            saveLogs(request,JSON.toJSON(requestParam),JSON.toJSON(responseResult),responseMethod,"ERROR",System.currentTimeMillis() - now,type);
            return JsonResult.build(500,"系统正在维护中，请稍后再试",e.getMessage());
        }
    }

    public void saveLogs(HttpServletRequest requestIp,Object requestParam,Object responseResult, String responseMethod,String resultCode, long now, String type) {
        String selNo = SelNoFactory.getSelNo(1001L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SysIMUserLogsEntity record = new SysIMUserLogsEntity();
        record.setLogAuthor("IM-USER-LOGS");
        record.setLogCreatetime(sdf.format(new Date()));
        record.setLogId(UUIDUtil.primaryKeyUUID());
        record.setLogIp(IpUtils.getIpAddr(requestIp));
        record.setLogParam(JSON.toJSON(requestParam).toString());
        record.setLogRequest(responseMethod);
        record.setLogResult(JSON.toJSON(responseResult).toString());
        record.setLogStatus(resultCode);
        record.setLogTimes(System.currentTimeMillis()+"ms");
        record.setLogType(type);
        record.setLogSystem("IM用户管理系统");
        record.setLogSelno(selNo);
        Boolean saveStatus = sysIMUserLogsFacade.insertLogsByRequest(record);
        if(saveStatus){
            log.info("日志【" + selNo+"】保存成功");
        }else{
            log.info("日志【" + selNo+"】保存失败" );
        }
    }
}
