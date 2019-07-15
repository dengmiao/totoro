package com.cn.miao.security.config;

import com.cn.miao.security.filter.TotoroJwtAuthenticationFilter;
import com.cn.miao.security.filter.TotoroLoginAuthenticationFilter;
import com.cn.miao.security.filter.TotoroXssFilter;
import com.cn.miao.security.handler.TotoroAccessDeniedHandler;
import com.cn.miao.security.handler.TotoroAuthenticationFailHandler;
import com.cn.miao.security.handler.TotoroAuthenticationSuccessHandler;
import com.cn.miao.security.properties.SecurityProperties;
import com.cn.miao.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: WebSecurityConfig
 * @description: security核心配置
 * @author: dengmiao
 * @create: 2019-07-12 16:19
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${totoro.security.tokenRedis:false}")
    private Boolean tokenRedis;

    @Value("${totoro.security.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注入自带实现的加密工具
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 权限不足处理器
     * @return
     */
    @Bean
    public TotoroAccessDeniedHandler accessDeniedHandler() {
        return new TotoroAccessDeniedHandler();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        TotoroLoginAuthenticationFilter filter = new TotoroLoginAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failHandler());
        filter.setFilterProcessesUrl(properties.getLoginUrl());

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * 登录成功处理器
     * @return
     */
    @Bean
    public TotoroAuthenticationSuccessHandler successHandler() {
        return new TotoroAuthenticationSuccessHandler();
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public TotoroAuthenticationFailHandler failHandler() {
        return new TotoroAuthenticationFailHandler();
    }

    /**
     * 处理 rememberMe 自动登录认证
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    /**
     * XssFilter Bean
     * @return
     */
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new TotoroXssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 权限不足处理器
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .csrf()
                    .disable()
                .headers()
                    .frameOptions()
                        .disable()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                // 表单方式
                .formLogin()
                // 未认证跳转url
                .loginPage(properties.getLoginPage())
                // 处理登录认证的url
                .loginProcessingUrl(properties.getLoginUrl())
                // 处理登录成功
                .successHandler(successHandler())
                // 处理登录失败
                .failureHandler(failHandler())
            .and()
                // 添加记住我功能
                .rememberMe()
                // token持久化仓库
                .tokenRepository(persistentTokenRepository())
                // rememberMe过期时间（s）
                .tokenValiditySeconds(properties.getTokenExpireTime())
                // 处理自动登录逻辑
                .userDetailsService(userDetailsService)
            .and()
                // 配置登出
                .logout()
                // 处理登出的url
                .logoutUrl(properties.getLogoutUrl())
                //.logoutSuccessUrl("/security/needLogin")
            .and()
                // 授权配置
                .authorizeRequests()
                // 免认证静态资源路径
                .antMatchers(properties.getIgnoreUrls())
                    .permitAll()
                .antMatchers(
                        properties.getLoginPage(),
                        properties.getLoginUrl(),
                        properties.getLogoutUrl())
                    .permitAll()
                // 所有请求
                .anyRequest()
                    // 都需要认证
                    .authenticated()
            .and()
            .addFilter(new TotoroJwtAuthenticationFilter(authenticationManager(), tokenRedis, tokenExpireTime, redisTemplate))
            .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
