package com.blysin.leshop.admin.controller;

import org.apache.shiro.authz.annotation.RequiresGuest;
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
    @RequiresGuest
    public void test(String param){
        System.out.println("hello world "+param);
    }

    @RequestMapping("/unauth")
    public String toUnauth(String successUrl){
        return "/login/unauth";
    }

    @RequestMapping("/login")
    public String toLogin(String successUrl){
        return "/login/login";
    }

    @RequestMapping("/index")
    public String toIndex(){
        return "/admin/index";
    }

    @RequestMapping("/user")
    public String toUser(){
        return "/user/user";
    }
    @RequestMapping("/product")
    public String toProduct(){
        return "/product/product";
    }

    @RequestMapping("/setting")
    public String toSetting() {
        return "/setting/setting";
    }


}
