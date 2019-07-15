package com.cn.miao.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.cn.miao.security.properties.SecurityProperties.DEFAULT_PREFIX;

/**
 * @title: SecurityProperties
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 17:20
 **/
@Data
@ConfigurationProperties(value = DEFAULT_PREFIX)
public class SecurityProperties {

    /**
     * 自动配置默认前缀
     */
    protected static final String DEFAULT_PREFIX = "totoro.security";

    /**
     * 登录页
     */
    private String loginPage;

    /**
     * 登录 URL
     */
    private String loginUrl;

    /**
     * 登出 URL
     */
    private String logoutUrl;

    /**
     * spring security 忽略鉴权的url集合
     */
    private String[] ignoreUrls;

    /**
     * token过期时间
     */
    private int tokenExpireTime;
}
