package cn.tlh.admin.consumer.config;

import cn.tlh.admin.consumer.shiro.MySessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired(required = false)
    private RedisSessionDAO redisSessionDAO;
    @Autowired(required = false)
    private RedisCacheManager redisCacheManager;

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 配置不会被拦截的链接 顺序判断
        chain.addPathDefinition("/**", "anon");
/*
        chain.addPathDefinition("/401", "anon");
        chain.addPathDefinition("/403", "anon");
        chain.addPathDefinition("/swagger/**", "anon");
        chain.addPathDefinition("/v2/**", "anon");
        chain.addPathDefinition("/webjars/**", "anon");
        chain.addPathDefinition("/swagger-resources/**", "anon");
        chain.addPathDefinition("/swagger-ui.html", "anon");
        chain.addPathDefinition("/doc.html/**", "anon");
        chain.addPathDefinition("/login", "anon");
        chain.addPathDefinition("/error", "anon");
        chain.addPathDefinition("/logout", "anon");
        chain.addPathDefinition("/**", "authc");
*/
        return chain;
    }


    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        // 不在地址栏显示sessionId
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置超时时间
        sessionManager.setGlobalSessionTimeout(60 * 60 * 24 * 7 * 1000L);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public SessionsSecurityManager sessionsSecurityManager(List<Realm> realms, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * 开启Shiro的注解
     * （如@RequiresRoles,@RequiresPermissions）,需借助SpringAOP
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}