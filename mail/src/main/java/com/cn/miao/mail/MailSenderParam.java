package com.cn.miao.mail;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @title: MailSenderParam
 * @description: 邮件发送参数配置
 * @author: dengmiao
 * @create: 2019-07-25 15:45
 **/
@Data
@Accessors(chain = true)
public class MailSenderParam {

    /**
     * 收件人
     */
    private String mailTo;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件
     */
    private List<File> attachments = new ArrayList<>();

    /**
     * 模板文件
     */
    private String templateFile;

    private String template;

    /**
     * 模板的参数集
     */
    private Map<String, Object> templateModels;
}
