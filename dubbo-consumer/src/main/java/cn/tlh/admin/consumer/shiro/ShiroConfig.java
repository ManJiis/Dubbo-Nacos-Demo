package cn.tlh.admin.consumer.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TANG
 * @description Shiro配置
 * @date 2020-12-21
 */
@Configuration
public class ShiroConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Autowired
    private RedisSessionDAO redisSessionDAO;
    @Autowired
    private RedisCacheManager redisCacheManager;

    /**
     * 此bean名称必须为shiroFilterFactoryBean
     *
     * @param securityManager /
     * @return /
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecuritManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        // 配置拦截器,实现无权限返回401,而不是跳转到登录页
        filters.put("authc", new ShiroLoginFilter());
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        // authc：所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/doc.html/**", "anon");
        filterChainDefinitionMap.put("/system/auth/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro中配置redis信息
     *
     * @return RedisManager
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost + ":" + redisPort);
        if (StringUtils.isNotBlank(redisPassword)) {
            redisManager.setPassword(redisPassword);
        }
        return redisManager;
    }

    /**
     * 自定义Realm
     *
     * @return /
     */
    @Bean
    public CustomAuthorizingRealm userRealm() {
        CustomAuthorizingRealm customAuthorizingRealm = new CustomAuthorizingRealm();
        // 开启缓存
        customAuthorizingRealm.setCachingEnabled(true);
        // 开启身份验证缓存，即缓存AuthenticationInfo信息
        customAuthorizingRealm.setAuthenticationCachingEnabled(true);
        // 设置身份缓存名称前缀
        customAuthorizingRealm.setAuthenticationCacheName("userinfo_cache");
        // 开启授权缓存
        customAuthorizingRealm.setAuthorizationCachingEnabled(true);
        // 这是权限缓存名称前缀
        customAuthorizingRealm.setAuthorizationCacheName("authorization_cache");
        return customAuthorizingRealm;
    }

    /**
     * 自定义 sessionManager，可从请求头中获取 token 做为 sessionid 实现无状态请求
     */
    @Bean
    public SessionManager sessionManager() {
        // 自定义SessionManager
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(1800000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 不在地址栏显示sessionId
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 超时时间，默认30分钟，设置sessionId超时时间，方法单位：毫秒
        // 一天
//        sessionManager.setGlobalSessionTimeout(60 * 60 * 24 * 1000L);
        // 10分钟
        sessionManager.setGlobalSessionTimeout(60 * 10 * 1000L);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现，实现sessionId管理
     * 使用的是shiro-redis开源插件
     *
     * @return /
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        // 设置缓存名前缀
//        redisSessionDAO.setKeyPrefix("shiro:session:");
        // 自定义sessionId生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }

    /**
     * cacheManager 缓存 redis实现 ,将用户信息缓存在redis
     * 使用的是shiro-redis开源插件
     *
     * @return /
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 选择属性字段作为缓存标识，这里选择userId字段
        redisCacheManager.setPrincipalIdFieldName("userId");
        // 设置信息缓存时间 7天
        redisCacheManager.setExpire(60 * 60 * 24 * 7);
        return redisCacheManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
//        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SessionsSecurityManager sessionsSecurityManager(List<Realm> realms, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms);
        // 配置 SecurityManager，并注入 UserRealm
        securityManager.setRealm(userRealm());
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,//@RequiresPermissions)
     * 需配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}