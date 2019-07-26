package com.cn.miao.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title: OssProperties
 * @description: 阿里云文件上传属性配置
 * @author: dengmiao
 * @create: 2019-07-26 13:53
 **/
@Data
@ConfigurationProperties(prefix = "totoro.storage.oss" )
public class OssProperties {

    /**
     * endpoint地址
     */
    private String endpoint;

    /**
     * oss key
     */
    private String accessKeyId;

    /**
     * oss密钥
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;
}
