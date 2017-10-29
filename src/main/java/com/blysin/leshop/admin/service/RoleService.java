package com.blysin.leshop.admin.service;

import com.blysin.leshop.admin.domain.Role;
import com.blysin.leshop.common.mybatis.BaseService;

import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
public interface RoleService extends BaseService<Role> {
    Set<String> findRolesByUserName(String userName);
}
