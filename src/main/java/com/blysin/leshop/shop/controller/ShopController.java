package com.blysin.leshop.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blysin
 * @since 2017/11/1
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
    @RequestMapping("")
    public String index(){
        return "shop/index";
    }
}
