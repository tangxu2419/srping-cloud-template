package com.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author tangxu
 * @date 2019/1/1618:08
 */
@Slf4j
@Component
public class AmendScheduledTasks {

    private RestTemplate restTemplate;

    public AmendScheduledTasks(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String BASE_URL = "http://10.138.61.46:31120";
//    private static final String BASE_URL = "http://operation.dev.vcredit.com.local:31120/";

    /**
     * "0/5 * *  * * ?"   每5秒触发
     * "0 0 12 * * ?"    每天中午十二点触发
     * "0 15 10 ? * *"    每天早上10：15触发
     * "0 15 10 * * ?"    每天早上10：15触发
     * "0 15 10 * * ? *"    每天早上10：15触发
     * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     * "0 15 10 ? * 1-5"    每个周一、周二、周三、周四、周五的10：15触发
     */
//    @Scheduled(cron = "0 0 3 ? * 2-7")
    public void startAmend() {
        log.info("===================[开启存量任务]====================");
        log.info("开启时间：{}", DateFormatUtils.format(new Date(), DEFAULT_PATTERN));
        int startLine = 0;
        String url = BASE_URL + "/amend/yeepay";
        String requestParam = "filePath=/data/ftp/tangxu/upload/yeepay_info_pro.txt&merchantId=10025556954&startLine=" + startLine;
//        String requestParam = "filePath=/data/ftp/tangxu/upload/test1.txt&merchantId=10025556954&startLine=" + startLine;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestParam, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info(exchange.getBody());
    }

    //    @Scheduled(cron = "0 0 7 ? * 2-7")
    public void breakOff() {
        log.info("===================[关闭存量任务-开关]====================");
        log.info("关闭时间：{}", DateFormatUtils.format(new Date(), DEFAULT_PATTERN));
        String url = BASE_URL + "/amend/break-off/yeepay";
        String requestParam = "breakOff=true";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestParam, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info(exchange.getBody());
    }


    //    @Scheduled(cron = "0 1 7 ? * 2-7")
    public void breakOn() {
        log.info("===================[开启存量任务-开关]====================");
        log.info("初始化任务开关时间：{}", DateFormatUtils.format(new Date(), DEFAULT_PATTERN));
        String url = BASE_URL + "/amend/break-off/yeepay";
        String requestParam = "breakOff=false";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestParam, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info(exchange.getBody());
    }

}
