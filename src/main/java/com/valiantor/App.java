package com.valiantor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@MapperScan("com.valiantor.dao")
@Controller
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:user_main.html";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "redirect:admin_main.html";
    }
}
