package com.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/1910:56
 */
@EnableZuulProxy
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class);
    }


}
