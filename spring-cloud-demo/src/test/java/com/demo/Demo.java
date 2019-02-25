package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/2/1810:58
 */
@Slf4j
public class Demo {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:11100";

    public static void main(String[] args) {
        for(int i = 0; i < 1000; i++){
            new TestSimpleDateFormatThreadSafe().start();
            new TestSimpleDateFormatThreadSafe2().start();
        }
    }

    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
//            log.info("=======================================");
            String url = BASE_URL + "/api/entrust/apply-details?accountNo=6217001210012016240&accountName=曹军&documentNumber=310225198812115610&mobilePhone=13795344187&channelCode=";
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if( exchange.getStatusCodeValue() != 200 ){
                log.info("响应：{}",exchange);
            }
//            log.info("=======================================");
        }
    }
    public static class TestSimpleDateFormatThreadSafe2 extends Thread {
        @Override
        public void run() {
//            log.info("=======================================");
            String url = BASE_URL + "/api/entrust/apply-details?accountNo=6217001210075827053&accountName=汤旭&documentNumber=342425199310062419&mobilePhone=13696776873&channelCode=";
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if( exchange.getStatusCodeValue() != 200 ){
                log.info("响应：{}",exchange);
            }
//            log.info("=======================================");
        }
    }

}
