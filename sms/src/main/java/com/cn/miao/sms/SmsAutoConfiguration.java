package com.cn.miao.sms;

import com.cn.miao.sms.properties.AliyunSmsProperties;
import com.cn.miao.sms.sender.AliyunSmsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SmsAutoConfiguration
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 10:19
 **/
@Configuration
@EnableConfigurationProperties({AliyunSmsProperties.class})
public class SmsAutoConfiguration {

    @Autowired
    private AliyunSmsProperties aliyunSmsProperties;

    @Bean
    @ConditionalOnClass({ AliyunSmsSender.class })
    @ConditionalOnProperty(name = "totoro.sms.type", havingValue = "aliyun")
    public SmsSender smsSender() {
        AliyunSmsSender sender = new AliyunSmsSender();
        BeanUtils.copyProperties(aliyunSmsProperties, sender);
        return sender;
    }
}
