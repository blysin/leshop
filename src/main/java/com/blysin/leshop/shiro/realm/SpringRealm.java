package com.blysin.leshop.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Blysin
 * @since 2017/10/29
 */
public class SpringRealm extends AuthenticatingRealm {
    @Autowired
    private AdminService adminService;

    /**
     * 认证过程
     * <p>
     * 1、把AuthenticationToken转为UsernamePasswordToken
     * 2、获取token中的username，去数据库中获取用户信息
     * 3、比对信息，手动抛出异常
     * 4、如果没有异常，则构建AuthenticationInfo并返回，shiro会自动比对密码
     * 4.1：通常使用SimpleAuthenticationInfo实现类
     *
     * @author Blysin
     * @date 2017/10/29 15:45
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println(JSON.toJSONString(token));
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        System.out.println(new String(usernamePasswordToken.getPassword()));
        Admin admin = Admin.builder().userName(usernamePasswordToken.getUsername()).build();
        admin = adminService.selectOne(admin);
        if (admin == null) {
            throw new UnknownAccountException("用户不准在");
        }
        if (admin.getStatucCd() == 0) {
            throw new LockedAccountException("用户被冻结");
        }

        ByteSource salt = ByteSource.Util.bytes(admin.getUserName());//盐值加密

        //构造器：1、用户对象 2、密码 3、盐值 4、realm名称
        return  new SimpleAuthenticationInfo(admin, admin.getPassword(),salt, getName());
    }
}
