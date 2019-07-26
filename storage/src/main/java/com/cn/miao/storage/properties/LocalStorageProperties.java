package com.cn.miao.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title: LocalStorageProperties
 * @description: 本地存储属性配置
 * @author: dengmiao
 * @create: 2019-07-26 13:54
 **/
@Data
@ConfigurationProperties(prefix = "cola.storage.local" )
public class LocalStorageProperties {

    private String location;
}
