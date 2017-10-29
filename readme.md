# SpringBoot-Shiro

## 说明

1.  利用SpringBoot搭建的SSM框架，包括Spring+Spring MVC+MyBatis+Shiro+Freemarker+Redis+Ehcache
2.  项目使用了lombok，需要自行安装插件
3.  整合和通用mapper，可以实现单表的crud自动生成
4.  com.blysin.leshop.common.mybatis.generator.CodeGenerator类可以实现controller、service、dao、domain、mapper的自动生成，生成的代码自动整合了lombok及通用mapper，但是domain内的id配置可能需要自行配置。



## 一、Shiro简介

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

## 二、Demo

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

## 三、SpringBoot与Shiro集成

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

## 四、实现自定义Realm

继承AuthorizingRealm类，实现两个方法：doGetAuthenticationInfo（登录认证）、doGetAuthorizationInfo（授权认证）

```java
/**
 * 用于登录认证
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
    return new SimpleAuthenticationInfo(admin, admin.getPassword(), salt, getName());
}
```

```java
/**
 * 用于授权认证
 *
 * 1.从PrincipalCollection中获取用户信息
 * 2.利用用户信息来获取当前用户角色或权限列表
 * 2.1 当有多个Realm时，getPrimaryPrincipal会遍历获取每个Realm中的用户信息
 * 3.创建SimpleAuthorizationInfo并传入roles
 * 4.返回SimpleAuthorizationInfo
 *
 * @author Blysin
 * @date 2017/10/29 19:22
 */
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
    Set<String> roles = roleService.findRolesByUserName(admin.getUserName());
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    return info;
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

## 五、Permisions权限配置

-   配置规则：资源标识符:操作:对象实例ID-->对于哪个资源哪个对象可以进行哪种操作

-   通配符：“：”分隔符，“，”并列符，"*"匹配任意字符

    例如：

    ​	user:add:blysin  基本的权限配置

    ​	user:add,delete:blysin  配置多个权限

    ​	user:edit 等价于user:edit:*

    ​	user.* 也可以写作*.query，表示摸个用户具备所有query权限

-   ​

## 六、Freemarker-Shiro-Tag集成

### 1、导包

```xml
<!-- shiro-freemarker-tags -->
<dependency>
   <groupId>net.mingsoft</groupId>
   <artifactId>shiro-freemarker-tags</artifactId>
   <version>0.1</version>
</dependency>
```

### 2、配置

```java
/**
 * freemarker配置初始化后配置ShiroTags
 *
 * @author Blysin
 * @since 2017/10/29
 */
@Component
public class FreemarkerConfig implements InitializingBean {
    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加上这句后，可以在页面上使用shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
```

### 3、使用

#### 1.guest

游客

```xml
<@shiro.guest>  您当前是游客，<a href="javascript:void(0);" class="dropdown-toggle qqlogin" >登录</a></@shiro.guest> 
```

#### 2.user

已经登录，或者记住我登录

```xml
<@shiro.user>  欢迎[<@shiro.principal/>]登录，<a href="/logout.shtml">退出</a>  </@shiro.user>   
```

#### 3.authenticated

已经认证，排除记住我登录的

```xml
<@shiro.authenticated>  	用户[<@shiro.principal/>]已身份验证通过  </@shiro.authenticated>   
```

#### 4.notAuthenticated

和authenticated相反

```xml
<@shiro.notAuthenticated>    当前身份未认证（包括记住我登录的）</@shiro.notAuthenticated> 
```

#### 5.principal

principal标签，取值取的是你登录的时候。在Realm实现类中的如下代码：

```
....return new SimpleAuthenticationInfo(user,user.getPswd(), getName());
```

在new SimpleAuthenticationInfo(第一个参数,....)的第一个参数放的如果是一个username，那么就可以直接用。

```
<!--取到username--><@shiro. principal/>
```

如果第一个参数放的是对象，比如我喜欢放User对象。那么如果要取username字段。

```
<!--需要指定property--><@shiro.principal property="username"/>
```

和Java如下Java代码一致

```
User user = (User)SecurityUtils.getSubject().getPrincipals();String username = user.getUsername();
```

#### 6.hasRole

判断是否拥有这个角色

```
<@shiro.hasRole name="admin">  	用户[<@shiro.principal/>]拥有角色admin<br/>  </@shiro.hasRole>   
```

#### 7.hasAnyRoles

判断是否拥有这些角色的其中一个

```
<@shiro.hasAnyRoles name="admin,user,member">  用户[<@shiro.principal/>]拥有角色admin或user或member<br/>  </@shiro.hasAnyRoles>   
```

#### 8.lacksRole

判断是否不拥有这个角色

```
<@shiro.lacksRole name="admin">  用户[<@shiro.principal/>]不拥有admin角色</@shiro.lacksRole>   
```

#### 9.hasPermission

判断是否有拥有这个权限

```
<@shiro.hasPermission name="user:add">  	用户[<@shiro.principal/>]拥有user:add权限</@shiro.hasPermission>   
```

#### 10.lacksPermission

判断是否没有这个权限

```
<@shiro.lacksPermission name="user:add">  	用户[<@shiro.principal/>]不拥有user:add权限</@shiro.lacksPermission>   
```

## 七、注解权限配置

使用注解为方法添加权限，如果用户不具备该权限，则访问会报`org.apache.shiro.authz.UnauthorizedException`

1.  @RequiresAuthentication：表示用户已经登录
2.  @RequiresUser：表示已经登录或记住我
3.  @RequiresGuest：表示没用登录页没有记住我，及游客状态
4.  @RequiresRoles(value={"admin","user"},logical=Logical.AND)：表示用户需要同时具备admin和user角色
5.  @RequiresPermissions(value="user:add","user:delere")：表示需要‘user:add’**或**‘user:delete’权限

## 八、会话管理

​	Shiro提供了一个Session对象，该对象不依赖于Web容器，在非Web项目中也可使用，相应的提供了一个Listener监听器。

​	Web项目中推荐还是使用Web自带的Session对象，Shiro会自动同步HttpServletSession和shiro Session对象中保存的参数值。在Service层中可以直接SecurityUtils.getSubject().getSession()来获取Session对象，从而获取保存在Session中的参数。

## 九、SessionDao

​	Shiro的session可以缓存到数据或者缓存（常用Redis缓存）中，因此可以自己实现SessionDao管理session的更删改操作。

​	实例：

```java
package com.blysin.leshop.shiro.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Blysin
 * @since 2017/10/29
 */
@Component
public class SessionDao extends EnterpriseCacheSessionDAO implements CacheManagerAware {

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        //TODO 将session对象序列化到数据库或者缓存中并返回id
        return sessionId;
    }



    @Override
    protected Session doReadSession(Serializable sessionId) {
        //TODO 从数据库或者缓存中读书session数据并反序列化为session对象
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        //TODO 更新数据库或者缓存中的session对象数据
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        //TODO 删除操作
        super.doDelete(session);
    }
}
```

## 十、缓存

​	Shiro内部SecurityManager会自动检测相应的对象是否实现CacheManagerAware接口，并自动注入CacheManager对象。

## 十一、RemmemberMe

​	有些时候页面一些非敏感操作可以让用户使用记住我的功能来验证授权，但是当具体的敏感操作时需要重新登录，如京东的添加购物车可以使用RemmemberMe权限，定时查看购物车则需要登录后才能操作。通过保存一个Cookie来实现。

​	subject.isRemmembered() 和subject.isAuthenticated()只有一个返回true，另一个一定是返回false

修改remmemberMe配置：

```java
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

        return defaultWebSecurityManager;
    }
```

