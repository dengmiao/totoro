package com.cn.miao.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @title: AliyunSmsProperties
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 10:20
 **/
@Data
@Configuration
@ConfigurationProperties(prefix="totoro.sms.aliyun")
public class AliyunSmsProperties {

    private String accessKeyId ;

    private String accessKeySecret;
}
