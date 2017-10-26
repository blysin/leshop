package com.blysin.leshop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blysin
 * @since 2017/10/19
 */
@Controller
@RequestMapping("/admin/sa")
public class AdminController {

    @RequestMapping("/test")
    public void test(String param){
        System.out.println("hello world "+param);
    }

    @RequestMapping("/login")
    public String toLogin(String successUrl){
        return "/admin/login";
    }

    @RequestMapping("/index")
    public String toIndex(){
        return "/admin/index";
    }


}
