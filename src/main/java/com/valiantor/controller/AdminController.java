package com.valiantor.controller;

import com.valiantor.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public String login(String aId,String password){

        if(adminService.login(aId,password)) return "true";

        return "false";
    }
}
