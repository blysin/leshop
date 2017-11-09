package com.blysin.leshop.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * @author 代码生成器
 * @since 2017-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = 7247714666080613254L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    //管理员名称
    private String userName;

    //真实姓名
    private String realName;

    //登录密码
    private String password;

    //状态（是否启用）
    private Integer statucCd;

    //创建时间
    private Date createTime;

    //上次登录时间
    private Date lastLoginTime;

    //上次登录ip
    private String lastLoginIp;
}
