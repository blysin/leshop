package com.blysin.leshop;

import com.alibaba.fastjson.JSON;
import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.admin.service.AdminService;
import com.blysin.leshop.admin.service.FilterChailMapService;
import com.blysin.leshop.shiro.dao.SerializableUtils;
import com.blysin.leshop.shiro.dao.SessionsDao;
import com.blysin.leshop.shiro.domain.Sessions;
import com.blysin.leshop.shop.domain.Product;
import com.blysin.leshop.shop.service.ProductService;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeshopApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private FilterChailMapService filterChailMapService;

    @Test
    public void contextLoads() {
        Admin admin = Admin.builder().userName("admin").build();
        System.out.println(adminService.selectOne(admin));
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void reidsTest() {
        Admin admin = Admin.builder().userName("admin").realName("test").adminId(18).build();
        redisTemplate.opsForValue().set("admin", admin);
        System.out.println(redisTemplate.opsForValue().get("admin"));
    }

    @Test
    public void shiroTest() {
        LinkedList<FilterChailMap> result = filterChailMapService.findMapping();
        for (FilterChailMap temp : result) {
            System.out.println(temp.getRequestUri() + "-->" + temp.getFilterName());
        }
    }

    @Autowired
    private SessionsDao sessionsDao;

    @Test
    public void serizableTest() {
        //		Admin admin = Admin.builder().adminId(123).createTime(new Date()).realName("blysin").build();
        //		Sessions sessions= Sessions.builder().id("123123123").session(SerializableUtils.serialize(admin)).createTime(new Date()).build();
        //		sessionsDao.insert(sessions);

        Sessions sessions = Sessions.builder().id("123123123").build();
        Sessions result = sessionsDao.selectByPrimaryKey(sessions);
        System.out.println(result.getId());
        Admin admin = (Admin) SerializableUtils.deSerialize(result.getSession());


        System.out.println(admin);
    }

    private ProductService productService;
    @Test
    public void insertProducts(){
        List<Product> list = new ArrayList<>();
        BoundListOperations<String, Product> ops =  redisTemplate.boundListOps("projects");
        for (int i = 1; i < 21; i++) {
//            list.add();
//            ops.leftPush(Product.builder().ProductId(i).ProductName("商品_"+ RandomStringUtils.random(6,true,false)).DefaultPrice(BigDecimal.valueOf(RandomUtils.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP)).build());
        }
//        System.out.println(ops.range(0,ops.size()));

        System.out.println(ops.size());
        System.out.println(ops.leftPop());
        System.out.println(ops.size());

    }

}
