package com.blysin.leshop.shiro.domain;

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
public class Sessions implements Serializable{
    private static final long serialVersionUID = 7247714666080633254L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String session;
    private Date createTime;
}
