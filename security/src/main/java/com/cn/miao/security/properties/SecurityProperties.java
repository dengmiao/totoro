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
    protected static final String DEFAULT_PREFIX = "totoro.auto";

    /**
     * spring security 忽略鉴权的url集合
     */
    private final List<String> ignoreUrls;
}
