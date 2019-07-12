package com.cn.miao.admin.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @title: ProjectAutoConfiguration
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 13:29
 **/
@Data
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(value = ProjectProperties.class)
public class ProjectAutoConfiguration {

    private final ProjectProperties projectProperties;

    public ProjectAutoConfiguration(final ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }
}
