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
 * 角色表
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer RoleId;

    //角色名称
    private String RoleName;

    //角色描述
    private String RoleDesc;

    //开始时间
    private Date CreateTime;

    //角色描述
    private Date LastUpdateTime;

    //角色状态
    private Integer StatusCd;


    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer RoleId) {
        this.RoleId = RoleId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getRoleDesc() {
        return RoleDesc;
    }

    public void setRoleDesc(String RoleDesc) {
        this.RoleDesc = RoleDesc;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Date getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(Date LastUpdateTime) {
        this.LastUpdateTime = LastUpdateTime;
    }

    public Integer getStatusCd() {
        return StatusCd;
    }

    public void setStatusCd(Integer StatusCd) {
        this.StatusCd = StatusCd;
    }

}
