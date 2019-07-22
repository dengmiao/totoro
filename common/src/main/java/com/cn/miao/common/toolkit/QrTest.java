package com.cn.miao.common.toolkit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title: QrTest
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 11:41
 **/
public class QrTest {

    public static void main(String[] args) {
        // 存放在二维码中的内容 文本/链接资源路径。。
        String content = "这是二维码的内容";
        // 嵌入二维码的图片路径
        String imgPath = "E:/qrCode/comic.jpg";
        // 生成的二维码的路径及名称
        String destPath = "E:/qrCode/src/"+System.currentTimeMillis()+".jpg";
        QrConfig config = new QrConfig();
        // 设置边距 二维码和背景间的间距
        config.setMargin(3);
        // 设置前景色 二维码的颜色
        Color frColor = new Color(58, 95, 205);
        // 背景色
        Color bgColor = new Color(255, 193, 37);
        config.setForeColor(frColor.getRGB());
        config.setBackColor(bgColor.getRGB());
        // 附带logo
        config.setImg(imgPath);
        QrCodeUtil.generate(content, config, FileUtil.file(destPath));
        String text = QrCodeUtil.decode(FileUtil.file(destPath));
        System.out.println(StrUtil.format("解析二维码, 内容为:{}", text));

        User user = BeanUtil.mapToBean(
                new HashMap<String, Object>(2) {
                    {
                        put("name", "张三");
                        put("age", 18);
                    }
                }, User.class, true);
        System.out.println(user);

        List<Map<String, Object>> list = new ArrayList<>(){
            {
                add(new HashMap<>(2){
                    {
                        put("name", "张三");
                        put("age", 18);
                    }
                });
                add(new HashMap<>(2){
                    {
                        put("name", "李四");
                        put("age", 20);
                    }
                });
            }
        };
        list.stream().map(m -> BeanUtil.mapToBean(m, User.class, true)).collect(Collectors.toList()).forEach(u -> System.out.println(u));
    }

    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    static class User {
        private String name;

        private int age;
    }
}
