package com.cn.miao.security.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SecurityAutoConfiguration
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 17:22
 **/
@Data
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SecurityAutoConfiguration {

    /**
     * 注入 CorgiProperties
     */
    private final SecurityProperties securityProperties;

    /**
     * @param properties {@link SecurityProperties}
     */
    public SecurityAutoConfiguration(SecurityProperties properties) {
        this.securityProperties = properties;

    }
}
