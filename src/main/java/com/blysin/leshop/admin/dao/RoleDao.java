package com.blysin.leshop.admin.dao;

import com.blysin.leshop.admin.domain.Role;
import com.blysin.leshop.common.mybatis.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
  * 角色表 Mapper 接口
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Mapper
public interface RoleDao extends MyBaseMapper<Role> {
    Set<String> findRolesByUserName(@Param("userName") String userName);
}