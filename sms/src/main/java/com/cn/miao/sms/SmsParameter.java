package com.cn.miao.sms;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @title: SmsParameter
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 10:03
 **/
@Data
@Accessors(chain = true)
public class SmsParameter {

    private String templateCode;

    private String signName;

    private List<String> phoneNumbers;

    private String params;
}
