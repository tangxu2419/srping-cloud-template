package com.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/12/1913:53
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConsulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulServerApplication.class);
    }

}
