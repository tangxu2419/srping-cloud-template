package com.demo;

import com.demo.service.MongoDemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Date;

/**
 * @author tangxu
 * @date 2019/1/1714:46
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {

    private static final String BASE_URL = "http://10.138.61.46:31120";
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MongoDemoService mongoDemoService;

    @Test
    public void test() throws Exception {
        mongoDemoService.outputCSV();
    }

    @Test
    public void test1(){
        log.info("===================[关闭存量任务-开关]====================");
        log.info("关闭时间：{}", DateFormatUtils.format(new Date(),DEFAULT_PATTERN));
        String url = BASE_URL + "/amend/break-off";
        String requestParam = "breakOff=false";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestParam, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info(exchange.getBody());
    }

}
