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

    @Autowired
    private MongoDemoService mongoDemoService;

    @Test
    public void test() throws Exception {
        mongoDemoService.outputSignInfoCSV("VCREDIT","BAOFU");
//        new Thread(()->{
//            try {
//                mongoDemoService.outputSignInfoCSV("VCREDIT","BAOFU");
//            } catch (Exception e) {
//                log.error(e.toString());
//            }
//        }).start();
//        mongoDemoService.outputSignInfoCSV("VCREDIT","YEEPAY");
    }


    @Test
    public void test1() throws Exception {
        mongoDemoService.outputSignInfoCSV("VCREDIT","YEEPAY");
//        new Thread(()->{
//            try {
//                mongoDemoService.outputSignInfoCSV("VCREDIT","BAOFU");
//            } catch (Exception e) {
//                log.error(e.toString());
//            }
//        }).start();
//        mongoDemoService.outputSignInfoCSV("VCREDIT","YEEPAY");
    }

//    @Test
//    public void test2() throws Exception {
//        mongoDemoService.outputSignInfoCSV("VCREDIT","YEEPAY");
//    }


}
