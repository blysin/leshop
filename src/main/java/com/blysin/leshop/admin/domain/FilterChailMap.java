package com.blysin.leshop.admin.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterChailMap {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //请求uri
    private String requestUri;

    //拦截器名称
    private String filterName;

    //排序值，降序
    private Integer compositor;

    //创建时间
    private Date createTime;
}
