package com.esb.im.server.controller;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ViewController{

    @RequestMapping(value = "/login.htm")
    public String count(){
        return "login";
    }

    @RequestMapping(value = "/index.htm")
    public String index(HttpSession session, ModelMap model){
       String username = (String)session.getAttribute("login_username");
        if (StringUtils.isEmpty(username)) {
            return "login";
        }
        model.addAttribute("username", username);
        return "index";
    }
}
