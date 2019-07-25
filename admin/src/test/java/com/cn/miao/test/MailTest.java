package com.cn.miao.test;

import com.cn.miao.mail.TotoroMailSender;
import com.cn.miao.mail.MailSenderParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: MailTest
 * @description:
 * @author: dengmiao
 * @create: 2019-07-25 17:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private TotoroMailSender totoroMailSender;

    @Test
    public void sendMail() {
        totoroMailSender.sendSimpleOrAttachmentMail(
                new MailSenderParam()
                        .setMailTo("1297320571@qq.com")
                        .setTitle("测试")
                        .setContent("你好吖")
        );
    }
}
