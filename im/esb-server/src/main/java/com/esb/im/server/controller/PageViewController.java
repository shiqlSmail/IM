package com.esb.im.server.controller;

import com.alibaba.druid.util.StringUtils;
import com.esb.im.server.domain.IMApiDO;
import com.esb.im.server.domain.IMLoginDO;
import com.esb.im.server.service.IMApiService;
import com.esb.im.server.service.IMLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/page")
public class PageViewController {

    @Autowired
    private IMLoginService imLoginService;

    @Autowired(required = false)
    private IMApiService ilonwApiService;

    @RequestMapping(value = "/admin-add.htm")
    public String adminAdd(){
        return "admin-add";
    }

    @RequestMapping(value = "/admin-list.htm")
    public String adminList(ModelMap model){
        List<IMLoginDO> imLoginDO = imLoginService.findAll();
        model.addAttribute("listUser",imLoginDO);
        return "admin-list";
    }

    @RequestMapping(value = "/one_set.htm")
    public String one_set(HttpSession session, ModelMap model){
        IMLoginDO imLoginDO = (IMLoginDO)session.getAttribute("user_data");
        if (null == imLoginDO) {
            return "login";
        }
        model.addAttribute("userInfo", imLoginDO);
        return "one_set";
    }

    @RequestMapping(value = "/article-list.htm")
    public String articleList(HttpSession session,ModelMap model,String menu){
        String username = (String)session.getAttribute("login_username");
        if (StringUtils.isEmpty(username)) {
            return "login";
        }
        List<IMApiDO> imApiDOList = ilonwApiService.selectAll(Integer.valueOf(menu));
        model.addAttribute("imApiList", imApiDOList);
        return "article-list";
    }

    @RequestMapping(value = "/articleadd.htm")
    public String articleadd(HttpSession session){
        String username = (String)session.getAttribute("login_username");
        if (StringUtils.isEmpty(username)) {
            return "login";
        }
        return "articleadd";
    }

    @RequestMapping(value = "/articlesend.htm")
    public String articlesend(String id,HttpSession session,ModelMap model){
        String username = (String)session.getAttribute("login_username");
        if (StringUtils.isEmpty(username)) {
            return "login";
        }
        IMApiDO imApiDO = ilonwApiService.selectByPrimaryKey(Integer.valueOf(id));
        model.addAttribute("apiData", imApiDO);
        return "articlesend";
    }

    @RequestMapping(value = "/login_out.htm")
    public String loginOut(HttpSession session){
        session.removeAttribute("login_username");
        return "login";
    }
}
