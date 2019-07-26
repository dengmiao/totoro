package com.cn.miao.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title: Swagger2Properties
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 10:47
 **/
@Data
@ConfigurationProperties(prefix = "totoro.swagger2")
public class Swagger2Properties {

    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String apiName;
    private String apiKeyName;
    private String termsOfServiceUrl;
}
