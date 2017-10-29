package com.blysin.leshop;

import com.alibaba.fastjson.JSON;
import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.admin.service.AdminService;
import com.blysin.leshop.admin.service.FilterChailMapService;
import com.blysin.leshop.shiro.dao.SerializableUtils;
import com.blysin.leshop.shiro.dao.SessionsDao;
import com.blysin.leshop.shiro.domain.Sessions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

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

	@Test
	public void reidsTest(){
		System.out.println(JSON.toJSONString(stringRedisTemplate.keys("*")));
	}

	@Test
	public void shiroTest(){
		LinkedList<FilterChailMap> result = filterChailMapService.findMapping();
		for(FilterChailMap temp:result){
			System.out.println(temp.getRequestUri()+"-->"+temp.getFilterName());
		}
	}

	@Autowired
	private SessionsDao sessionsDao;
	@Test
	public void serizableTest(){
//		Admin admin = Admin.builder().adminId(123).createTime(new Date()).realName("blysin").build();
//		Sessions sessions= Sessions.builder().id("123123123").session(SerializableUtils.serialize(admin)).createTime(new Date()).build();
//		sessionsDao.insert(sessions);

		Sessions sessions= Sessions.builder().id("123123123").build();
		Sessions result = sessionsDao.selectByPrimaryKey(sessions);
		System.out.println(result.getId());
		Admin admin = (Admin) SerializableUtils.deSerialize(result.getSession());

		System.out.println(admin);
	}

}
