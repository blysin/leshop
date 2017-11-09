package com.blysin.leshop.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Blysin
 * @since 2017/11/8
 */
@RestController
@RequestMapping("/products")
public class ProductApiController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(HttpServletRequest request,@RequestBody String param){
        System.out.println(param);
        JSONObject jsonObject = JSON.parseObject(param);
        return redisTemplate.boundListOps("projects").range(jsonObject.getLong("start"),jsonObject.getLong("limit"));
    }



    @RequestMapping("/{index}")
    public Object getProduct(@PathVariable Long index){
        return redisTemplate.boundListOps("projects").index(index);
    }
}
