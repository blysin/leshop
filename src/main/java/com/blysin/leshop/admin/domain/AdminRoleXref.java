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
 * 权限授权对照表
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleXref {

    //管理用户User_ID
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer AdminId;

    //角色ID
    private Integer RoleId;

    //创建时间
    private Date CreateTime;
}
