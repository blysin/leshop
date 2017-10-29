package com.blysin.leshop.configuration.shiro;

import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.admin.service.FilterChailMapService;
import com.blysin.leshop.shiro.realm.SpringRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Sha1CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Blysin
 * @since 2017/10/29
 */
@Configuration
@EnableCaching
public class ShiroConfig {
    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    private FilterChailMapService filterChailMapService;

    /**
     * 配置shiro拦截器工厂类
     *
     * @author Blysin
     * @date 2017/10/29 14:09
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/admin/sa/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin/sa/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/sa/unauth");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        /*
        * 拦截器配置：DefaultFilter枚举
        * anno：不需要登录校验
        * authc：需要登录校验
        * logout：自动退出登录并返回到登录页面
        *
        * 同一个路径如果配置了多个拦截器，只有第一个会生效*/

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/admin/sa/api/login", "anon");
        filterChainDefinitionMap.put("/admin/sa/test", "anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        //角色拦截器
        filterChainDefinitionMap.put("/admin/sa/product","roles[admin,product]");
        filterChainDefinitionMap.put("/admin/sa/user","roles[user,admin]");
        filterChainDefinitionMap.put("/admin/sa/setting","roles[admin]");

        //权限拦截器
       filterChainDefinitionMap.put("/add", "perms[权限添加]");

        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        filterChainDefinitionMap.put("/**", "authc");

        //使用数据库读取配置
//        LinkedList<FilterChailMap> result = filterChailMapService.findMapping();
//        for(FilterChailMap temp:result){
//            filterChainDefinitionMap.put(temp.getRequestUri(),temp.getFilterName());
//        }

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        logger.debug("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置SecurityManager
     *
     * @author Blysin
     * @date 2017/10/29 14:08
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(realm());

        //配置remmenberme的属性
        CookieRememberMeManager cookieRememberMeManager =  new CookieRememberMeManager();
        Cookie cookie = new SimpleCookie("shiro_rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10000);
        cookieRememberMeManager.setCookie(cookie);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);

        defaultWebSecurityManager.setCacheManager(ehCacheManager());
        return defaultWebSecurityManager;
    }

    /**
     * ehcache缓存管理器
     *
     * @author Blysin
     * @date 2017/10/29 15:01
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 自定义Realm
     *
     * @return
     */
    @Bean
    public SpringRealm realm() {
        SpringRealm realm = new SpringRealm();
        HashedCredentialsMatcher credentialsMatcher =  new HashedCredentialsMatcher("SHA-1");
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }
}
