package com.cn.miao.sms;

import lombok.Data;

/**
 * @title: SmsSendResult
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 10:01
 **/
@Data
public class SmsSendResult {

    private boolean success;

    private String code;
}
