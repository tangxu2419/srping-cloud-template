package com.demo;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangxu
 * @date 2019/1/1618:07
 */
@EnableScheduling
@Import(FdfsClientConfig.class)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
