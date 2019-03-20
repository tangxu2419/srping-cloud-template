package com.proxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/2016:32
 */
@Slf4j
@Controller
public class ProxyController {

//    @RequestMapping(value = "/**")
//    public String test(HttpServletRequest request) {
//        String contextPath = request.getRequestURI().toString();
//        log.info("Controller请求地址：{}", contextPath);
//        String redirectUrl = "http://localhost:2000" + contextPath;
//        return redirectUrl;
//    }

    @RequestMapping(value = "/**")
    public ResponseEntity test(HttpServletRequest request) {
        String contextPath = request.getRequestURI().toString();
        log.info("Controller请求地址：{}", contextPath);
        return ResponseEntity.ok().build();
    }

}
