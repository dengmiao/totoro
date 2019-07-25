package com.cn.miao.mail;

import com.cn.miao.mail.properties.MailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

/**
 * @title: MailAutoConfiguration
 * @description: 邮件发送配置
 * @author: dengmiao
 * @create: 2019-07-25 15:55
 **/
@Slf4j
@Configuration
@AutoConfigureAfter({MailSenderAutoConfiguration.class})
@EnableConfigurationProperties({MailProperties.class})
public class MailAutoConfiguration {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    @ConditionalOnMissingBean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        return sender;
    }

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    public TotoroMailSender adiMailSender(JavaMailSender sender, FreeMarkerConfigurer freeMarkerConfigurer) {
        if (mailProperties.isPreferIPv4Stack()) {
            System.setProperty("java.net.preferIPv4Stack", "true");
        }

        JavaMailSenderImpl js= (JavaMailSenderImpl) sender;
        js.setHost(mailProperties.getHost());
        js.setUsername(mailProperties.getUsername());
        js.setPassword(mailProperties.getPassword());
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.timeout", 25000);
        js.setJavaMailProperties(props);
        TotoroMailSender mailSender = new TotoroMailSender();
        mailSender.setJavaMailSender(js);
        mailSender.setFreeMarkerConfigurer(freeMarkerConfigurer);
        mailSender.setName(mailProperties.getUsername());
        mailSender.setFrom(mailProperties.getUsername());
        return mailSender;
    }
}
