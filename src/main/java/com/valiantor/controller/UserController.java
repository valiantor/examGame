package com.valiantor.controller;

import com.valiantor.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 用户登录
     * @param uId 用户账号
     * @param password 密码
     * @return true或false
     */
    @RequestMapping("login")
    public String login(@RequestParam("uId") String uId,@RequestParam("password") String password,HttpSession session){


        return null;
    }

    /**
     * 用户退出登录
     * @param uId 用户账号
     * @param password 密码
     * @return true或false
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){


        return null;
    }


    /**
     * 从session中获取当前已登录用户
     * @return 用户信息
     */
    @RequestMapping("getCurrentUser")
    public User getCurrentUser(HttpSession session){


        return null;
    }
}
