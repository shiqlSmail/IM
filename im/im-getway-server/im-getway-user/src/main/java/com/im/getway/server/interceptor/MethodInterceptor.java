package com.im.getway.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 请求拦截器
 */
@Component
public class MethodInterceptor implements HandlerInterceptor {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public MethodInterceptor(){}

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String queryString = request.getQueryString();
        String requestURL = request.getRequestURL().toString();
        log.info("进入拦截器，访问地址，" + requestURL);

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");

        String method = request.getMethod();
        if(!StringUtils.equals(method,"POST")){
            log.info("请求方法不是POST请求，已拒绝继续访问，请修改请求方法后再试！");

            JSONObject json = new JSONObject();
            try {
                json.put("msg", "非法请求");
                json.put("status", "IM-METHOD-ERROR");
                json.put("data", "{}");
                writer = response.getWriter();
                writer.append(json.toJSONString());
            } catch (IOException e) {
                log.info("系统异常："+e.getMessage());
                json.put("msg", "系统维护中，请稍后再试！");
                json.put("status", "IM-SYSTEM-ERROR");
                json.put("data", "{}");
                writer.append(json.toJSONString());
                return false;
            } finally {
                if (writer != null)
                    writer.close();
            }
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  {
    }
}
