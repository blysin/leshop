package com.blysin.leshop.common.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于自动执行分页，与PageHelper结合使用
 * 使用了该注解的方法中，第一个SQL查询语句会自动拦截成分页查询
 * 拦截语句select () from limit 'pageName','limitName'
 *
 * @author Blysin
 * @date 2017年4月21日19:16:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pageable {
    /**
     * 页码的参数名
     *
     * @return
     */
    String pageName() default "pageIndex";

    /**
     * 每页数量的参数名
     *
     * @return
     */
    String limitName() default "limit";
}
