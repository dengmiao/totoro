package com.cn.miao;

import com.cn.miao.admin.properties.ProjectProperties;
import com.cn.miao.common.toolkit.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.security.config.Elements.HTTP;

/**
 * @title: AdminApplication
 * @description: 启动入口
 * @author: dengmiao
 * @create: 2019-07-12 12:40
 **/
@Slf4j
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        //获取开始时间
        long start = System.currentTimeMillis();
        SpringApplication app = new SpringApplication(AdminApplication.class);
        Environment env = app.run(args).getEnvironment();
        //获取结束时间
        long end = System.currentTimeMillis();
        String protocol = HTTP;
        ProjectProperties projectProperties = ((ProjectProperties) SpringContextUtil.getBean(ProjectProperties.class));
        // 应用上下文
        String context = env.getProperty("server.servlet.context-path");
        // 端口号
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t"
                        + "名称:\t'{}' is running! Access URLs:\n\t"
                        + "本地:\t {}://localhost:{}{}\n\t"
                        + "外部:\t {}://{}:{}{}\n\t"
                        + "环境:\t {}\n\t"
                        + "版本:\t {}\n\t"
                        + "用时:\t {}\n"
                        + "----------------------------------------------------------",
                projectProperties.getName(),
                protocol, port, context,
                protocol, InetAddress.getLocalHost().getHostAddress(), port, context,
                env.getActiveProfiles(),
                projectProperties.getVersion(),
                (end - start) + "ms");
    }
}
