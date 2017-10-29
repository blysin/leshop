package com.blysin.leshop;

import com.alibaba.fastjson.JSON;
import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeshopApplicationTests {
	@Autowired
	private AdminService adminService;
	@Test
	public void contextLoads() {
		Admin admin = Admin.builder().userName("admin").build();
		System.out.println(adminService.selectOne(admin));
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void reidsTest(){
		System.out.println(JSON.toJSONString(stringRedisTemplate.keys("*")));
	}
}
