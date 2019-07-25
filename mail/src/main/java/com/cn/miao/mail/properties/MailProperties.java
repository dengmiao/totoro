package com.cn.miao.mail.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: MailProperties
 * @description: 邮件服务的配置
 * @author: dengmiao
 * @create: 2019-07-25 15:53
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String host;

    private String username;

    private String password;

    private String name;

    private String from;

    private boolean preferIPv4Stack;
}
