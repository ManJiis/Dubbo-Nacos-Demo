package cn.tlh.admin.consumer.config;

import cn.tlh.admin.consumer.shiro.MySessionManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author TANG
 * @description Shiro配置
 * @date 2020-12-21
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private RedisSessionDAO redisSessionDAO;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 配置退出 过滤器, 登出后跳转配置的loginUrl
        // 配置不会被拦截的链接 顺序判断
        chain.addPathDefinition("/**", "anon");
/*        chain.addPathDefinition("/401", "anon");
        chain.addPathDefinition("/403", "anon");
        chain.addPathDefinition("/swagger/**", "anon");
        chain.addPathDefinition("/v2/**", "anon");
        chain.addPathDefinition("/webjars/**", "anon");
        chain.addPathDefinition("/swagger-resources/**", "anon");
        chain.addPathDefinition("/swagger-ui.html", "anon");
        chain.addPathDefinition("/doc.html/**", "anon");
        chain.addPathDefinition("/login", "anon");
        chain.addPathDefinition("/actuator/**", "anon");
        chain.addPathDefinition("/favicon.ico", "anon");
        chain.addPathDefinition("/sysUser/**", "anon");
        chain.addPathDefinition("/error", "anon");
        chain.addPathDefinition("/merchant/publish/sfOrderState", "anon");
        chain.addPathDefinition("/merchant/publish/sfRoutePushService", "anon");
        chain.addPathDefinition("/simTerminal/echoAdmissibleSim", "anon");
        chain.addPathDefinition("/posTerminal/echoAdmissiblePos", "anon");
        chain.addPathDefinition("/cardAcceptance/echoAdmissibleCard", "anon");
        chain.addPathDefinition("/orderResp/echoOrderResp", "anon");
        chain.addPathDefinition("/orderResp/echoIndustry", "anon");
        chain.addPathDefinition("/respBank/bankFeedback", "anon");
        chain.addPathDefinition("/manualReview/**", "anon");
        chain.addPathDefinition("/apiManager/**", "anon");
        chain.addPathDefinition("/jiangsu/**", "anon");
        chain.addPathDefinition("/logout", "anon");
        chain.addPathDefinition("/**", "authc");*/
        return chain;
    }

    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        // 不在地址栏显示sessionId
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置超时时间
        sessionManager.setGlobalSessionTimeout(3600000L);
        return sessionManager;
    }

    @Bean
    public SessionsSecurityManager sessionsSecurityManager(List<Realm> realms, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,//@RequiresPermissions),需借助SpringAOP
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}