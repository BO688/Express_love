package com.bo.express_love.Config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(MyShiroRealm());

        securityManager.setCacheManager(MyCacheManager());
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/mail", "anon");
        filterChainDefinitionMap.put("/checkMail", "anon");
        filterChainDefinitionMap.put("/RegisterUser_getCode", "anon");
        filterChainDefinitionMap.put("/RegisterUser_setCode", "anon");
        filterChainDefinitionMap.put("/Client/Session", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/demo_1", "anon");
        filterChainDefinitionMap.put("/demo_2", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/ClientLogin", "anon");
        filterChainDefinitionMap.put("/MakeDemo_1", "anon");
        filterChainDefinitionMap.put("/MakeDemo_2", "anon");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/CancelLogin", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/Login");
//        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public MyShiroRealm MyShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }
    @Bean
    public CacheManager MyCacheManager(){
        EhCacheManager ehCacheCacheManager=new EhCacheManager();
        ehCacheCacheManager.setCacheManagerConfigFile("classpath:Config/ehcache.xml");
        return ehCacheCacheManager;
    }



}
