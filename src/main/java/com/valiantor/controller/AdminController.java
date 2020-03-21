package com.valiantor.controller;

import com.valiantor.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public String login(String aId, String password, HttpSession session){


        if(adminService.login(aId,password)){
            //将登录信息放到session中
            session.setAttribute("aId",aId);

            return "true";
        }

        return "false";
    }
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("aId");
        if(StringUtils.isEmpty(session.getAttribute("aId"))) return "true";
        return "false";
    }



}
