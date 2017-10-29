package com.blysin.leshop.admin.service.impl;

import com.blysin.leshop.admin.domain.Role;
import com.blysin.leshop.admin.dao.RoleDao;
import com.blysin.leshop.admin.service.RoleService;
import com.blysin.leshop.common.mybatis.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<String> findRolesByUserName(String userName) {
        return roleDao.findRolesByUserName(userName);
    }
}
