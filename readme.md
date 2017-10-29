# Shiro学习笔记

## 功能简介

### 基本功能

1.  Authentication：登录

2.  Authorization：权限校验

3.  Session Management：session管理（web环境中对HttpSession进行包装，非web环境也可以使用）

4.  Cryptography：加密

5.  Caching：缓存，如用户登录后其用户信息、拥有角色、权限不必每次去查询，这样可以提高效率

6.  Run As：允许一个用户假装另外一个用户身份进行访问

7.  Remmember Me：记住我，即第一次登录后下次就不用登录了

    ​

### 组织结构

Subject：当前用户，应用代码直接与该对象进行交互，是Shiro对外的核心API，Subject所有交互都会交给SecurityManager，Subject只是个门面，SecurityManager才是实际执行者

SecurityManager：安全管理器，管理所有用户（Subject）。

Realm：获取权限信息

## Demo

```java
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
```

## SpringBoot与Shiro集成

### 1、注入自定义Realm

```java
@Bean
public SpringRealm getRealm() {
    SpringRealm realm = new SpringRealm();
  	//设置加密算法
    HashedCredentialsMatcher credentialsMatcher =  new HashedCredentialsMatcher("SHA-1");
    credentialsMatcher.setHashIterations(1024);
    realm.setCredentialsMatcher(credentialsMatcher);
    return realm;
}
```

### 2、注入ehcache缓存管理器

```java
 @Bean
public EhCacheManager ehCacheManager(){
  EhCacheManager cacheManager = new EhCacheManager();
  cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
  return cacheManager;
}
```

### 2、注入SecurityManager

```java
@Bean
public org.apache.shiro.mgt.SecurityManager securityManager() {
  DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(getRealm());
  defaultWebSecurityManager.setCacheManager(ehCacheManager());
  return defaultWebSecurityManager;
}
```

### 3、开启shiro aop注解支持.

```java
@Bean
public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager){
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
}
```

### 4、配置shiro拦截器工厂类

```java
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

    // 配置不会被拦截的链接 顺序判断
    filterChainDefinitionMap.put("/static/**", "anon");
    filterChainDefinitionMap.put("/common/**", "anon");

    // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
    filterChainDefinitionMap.put("/logout", "logout");

    filterChainDefinitionMap.put("/add", "perms[权限添加]");

    // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
    filterChainDefinitionMap.put("/**", "authc");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    logger.debug("Shiro拦截器工厂类注入成功");
    return shiroFilterFactoryBean;
}
```

拦截器配置说明（枚举DefaultFilter中）：

**同一个路径如果配置了多个拦截器，只有第一个会生效**

1.  anno：不需要登录校验 
2.  authc：需要登录校验 
3.  logout：自动退出登录并返回到登录页面 

## 实现Realm

```java
/**
 * 认证过程
 * <p>
 * 1、把AuthenticationToken转为UsernamePasswordToken
 * 2、获取token中的username，去数据库中获取用户信息
 * 3、比对信息，手动抛出异常
 * 4、如果没有异常，则构建AuthenticationInfo并返回，shiro会自动比对密码
 * 4.1：通常使用SimpleAuthenticationInfo实现类，构造器1.用户对象，2.密码，3.当前Realm对象name，直接调用父类getName()方法即可
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

    return  new SimpleAuthenticationInfo(admin, admin.getPassword(),salt, getName());
}
```

### 盐值加密

​	相同的明文在同一种加密算法下加密结果是一样的，因此需要设置一个‘盐值’，进行干扰处理。shiro中我们使用用户名来作为盐值。

​	1、盐值加密过程：

```java
@Test
public void tesetSha1(){
    String name = "SHA-1";
    String userName = "admin";
  	String password = "123123"
    ByteSource salt = ByteSource.Util.bytes(userName);
    int hashIterations = 1024;

    Object result = new SimpleHash(name,password,salt,hashIterations);
    System.out.println(result);//盐值加密结果
}
```

​	2、shiro中配置盐值加密

```java
@Bean
public SpringRealm getRealm() {
    SpringRealm realm = new SpringRealm();
  	//配置加密算法
    HashedCredentialsMatcher credentialsMatcher =  new HashedCredentialsMatcher("SHA-1");
    credentialsMatcher.setHashIterations(1024);
    realm.setCredentialsMatcher(credentialsMatcher);
    return realm;
}
```

​	3、自定义中的Realm中配置盐值

```java
ByteSource salt = ByteSource.Util.bytes(admin.getUserName());//盐值加密

//构造器：1、用户对象 2、密码 3、盐值 4、realm名称
return  new SimpleAuthenticationInfo(admin, admin.getPassword(),salt, getName());
```

## Permisions权限配置

-   配置规则：资源标识符:操作:对象实例ID-->对于哪个资源哪个对象可以进行哪种操作
-   通配符