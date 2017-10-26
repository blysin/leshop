package com.blysin.leshop.common.mybatis.annotation;


import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页的切面类，与@Pageable注解结合使用
 * 该注解配置与要执行分页的方法上面，在方法内执行的第一个select语句会自动执行分页
 *
 * @author Blysin
 * @date 2017年4月21日19:20:05
 */
@Aspect
@Component
public class PageAOP {
    private Logger logger = LoggerFactory.getLogger(PageAOP.class);

    //拦截Pageable注解的方法
//    @Pointcut("@annotation(cn.yr.chile.common.mybatis.annotation.Pageable)")
    //    public void log() {}

    @Before("@annotation(page)")
    public void beforeExec(JoinPoint joinPoint, Pageable page) {
        logger.info("-----执行分页搜索-----");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        PageHelper.startPage(Integer.parseInt(request.getParameter(page.pageName()))+1, Integer.parseInt(request.getParameter(page.limitName())));
    }
}
