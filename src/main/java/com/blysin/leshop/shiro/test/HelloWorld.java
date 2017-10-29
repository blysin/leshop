package com.blysin.leshop.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Blysin
 * @since 2017/10/29
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        //1、获取工厂类
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        //2、设定SecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        //3、获取一个用户对象
        Subject currentUser = SecurityUtils.getSubject();

        //4、获取并操作session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        logger.info("获取session中的参数：" + session.getAttribute("someKey"));

        //5、用户登录
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("blysin", "123123");//授权信息（账号密码）
            token.setRememberMe(true);//remember me

            try {
                currentUser.login(token);//授权登录
            } catch (UnknownAccountException e) {
                logger.error("账号错误");
            } catch (IncorrectCredentialsException e) {
                logger.error("密码错误");
            } catch (LockedAccountException e) {
                logger.error("用户被冻结");
            } catch (AuthenticationException e) {
                logger.error("AuthenticationException 异常为以上所有异常的父类");
            }
        }

        logger.info("用户："+currentUser.getPrincipal()+"已经登录。");

        //6、角色判断
        if(currentUser.hasRole("admin")){
            logger.info(currentUser.getPrincipal()+"拥有admin角色");
        }else{
            logger.error(currentUser.getPrincipal()+"不具备该角色");
        }

        //7、权限校验
        if(currentUser.isPermitted("admin:delete:xiaohong")){
            logger.info(currentUser.getPrincipal()+"拥有该操作权限");
        }else{
            logger.error(currentUser.getPrincipal()+"不具备该该操作权限");
        }

        //8、登出
        logger.info("用户登录状态："+currentUser.isAuthenticated());

        currentUser.logout();

        logger.info("用户登录状态："+currentUser.isAuthenticated());
    }
}
