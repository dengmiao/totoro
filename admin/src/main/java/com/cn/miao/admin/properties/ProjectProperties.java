package com.cn.miao.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.cn.miao.admin.properties.ProjectProperties.DEFAULT_PREFIX;

/**
 * @title: ProjectProperties
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 13:28
 **/
@Data
@ConfigurationProperties(value = DEFAULT_PREFIX)
public class ProjectProperties {

    static final String DEFAULT_PREFIX = "totoro.project";

    private String name;

    private String version;

    private String poweredBy;
}
