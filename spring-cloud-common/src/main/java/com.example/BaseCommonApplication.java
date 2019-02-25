package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tangxu
 * @Title: 启动基本类型
 * @date 2019/1/3111:33
 */
@Slf4j
public class BaseCommonApplication {

    protected static void run(Class clazz, String[] args) {
        ConfigurableEnvironment env = SpringApplication.run(clazz, args).getEnvironment();
        loggingByEnv(env);
    }

    private static void loggingByEnv(ConfigurableEnvironment env) {
        String protocol = StringUtils.isBlank(env.getProperty("server.ssl.key-store")) ? "https" : "http";
        try {
            log.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! \n\t" +
                            "Access URLs:\n\t" +
                            " - Local: {}://localhost:{}\n\t" +
                            " - External: {}://{}:{}\n\t" +
                            "Active Profile(s): {}\n" +
                            "----------------------------------------------------------",
                    env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
                    InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getActiveProfiles());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
