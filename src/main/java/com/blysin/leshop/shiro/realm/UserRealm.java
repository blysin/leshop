package com.blysin.leshop.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.service.AdminService;
import com.blysin.leshop.admin.service.RoleService;
import com.blysin.leshop.user.domain.User;
import com.blysin.leshop.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 *
 *
 * @author Blysin
 * @since 2017/10/29
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 用于登录认证
     *
     * @author Blysin
     * @date 2017/10/29 15:45
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = new User();
        user.setLoginName(usernamePasswordToken.getUsername());
        user = userService.selectOne(user);

        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getStatusCd() == 0) {
            throw new LockedAccountException("用户被冻结");
        }

        ByteSource salt = ByteSource.Util.bytes(user.getLoginName());//盐值加密

        //构造器：1、用户对象 2、密码 3、盐值 4、realm名称
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }

    /**
     * 用于授权认证
     *
     * @author Blysin
     * @date 2017/10/29 19:22
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }
}
