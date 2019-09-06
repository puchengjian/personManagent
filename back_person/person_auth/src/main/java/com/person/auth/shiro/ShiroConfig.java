package com.person.auth.shiro;

import com.person.constant.RedisConst;
import com.person.redis.RedisCacheManager;
import com.person.shiro.ShiroSessionManager;
import com.person.shiro.ShiroLoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;


/**
 * @author: pzy
 * @create: 2019/8/5 16:50
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroMap shiroMap;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private Integer timout;

    @Value("${shiro.cookie.enabled}")
    private boolean cookieEnabled;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> map = filterFactoryBean.getFilters();
        map.put("authc", new ShiroLoginFilter());

        filterFactoryBean.setSuccessUrl("/index");
        filterFactoryBean.setUnauthorizedUrl("/403");
        filterFactoryBean.setFilterChainDefinitionMap(shiroMap.loadFilterMap());

        filterFactoryBean.setSecurityManager(securityManager());

        return filterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager());

        return securityManager;
    }


    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        //解决第一次重定向带jessionId
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(cookieEnabled);

        return sessionManager;
    }


    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //单位秒 一小时过期
        redisSessionDAO.setExpire(3600);
//        redisSessionDAO.setExpire(60);
        redisSessionDAO.setRedisManager(redisManager());

        return redisSessionDAO;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setPrincipalIdFieldName("roleId");
        //设置两天过期，单位秒
        redisCacheManager.setExpire(172800);

        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
        redisManager.setTimeout(timout);
        if (StringUtils.isNotBlank(password))
            redisManager.setPassword(password);

        return redisManager;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);

        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持，使用代理方法所以需要开启代码支持
     *  一定要写入上面advisorAutoProxyCreator（）自动代理。不然AOP注解不会生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCachingEnabled(true);
        authRealm.setAuthorizationCachingEnabled(true);
        authRealm.setAuthorizationCacheName(RedisConst.SHIRO_CACHE_NAME);

        return authRealm;
    }

}
