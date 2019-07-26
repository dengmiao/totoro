package com.cn.miao.sms;

/**
 * @title: SmsSender
 * @description: 邮件发送器
 * @author: dengmiao
 * @create: 2019-07-26 10:01
 **/
public interface SmsSender {

    /**
     * 发送短信
     * @param parameter
     * @return
     */
    SmsSendResult send(SmsParameter parameter);
}
