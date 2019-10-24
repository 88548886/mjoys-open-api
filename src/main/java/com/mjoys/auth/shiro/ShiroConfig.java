package com.mjoys.auth.shiro;

import com.google.common.collect.Lists;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(Realm userAuthorizingRealm, Realm jwtTokenAuthorizingRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        List<Realm> realms = Lists.newArrayList();
        realms.add(userAuthorizingRealm);
        realms.add(jwtTokenAuthorizingRealm);
        manager.setRealms(realms);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>(16);
        filterMap.put("jwtFilter", jwtFilter());
        filterMap.put("logFilter", logFilter());

        factoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(16);

        filterChainDefinitionMap.put("/login", "anon");
        //多个过滤器以逗号分隔
        filterChainDefinitionMap.put("/user/**", "jwtFilter,logFilter");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    @Bean("jwtFilter")
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean("logFilter")
    public LogFilter logFilter() {
        return new LogFilter();
    }


    @Bean
    public Realm userAuthorizingRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserAuthorizingRealm userAuthorizingRealm = new UserAuthorizingRealm();
        userAuthorizingRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userAuthorizingRealm;
    }

    @Bean
    public Realm jwtTokenAuthorizingRealm(JwtCredentialsMatcher jwtCredentialsMatcher) {
        JwtAuthorizingRealm userAuthorizingRealm = new JwtAuthorizingRealm();
        userAuthorizingRealm.setCredentialsMatcher(jwtCredentialsMatcher);
        return userAuthorizingRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    @Bean
    public JwtCredentialsMatcher jwtCredentialsMatcher() {
        return new JwtCredentialsMatcher();
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
