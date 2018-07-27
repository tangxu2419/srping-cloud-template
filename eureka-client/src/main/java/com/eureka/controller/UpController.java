package com.eureka.controller;

import com.eureka.conf.HealthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangxu
 * @date 2018/7/1011:34
 */
@RestController
public class UpController {

    @Autowired
    private HealthChecker healthChecker;

    @RequestMapping("/up")
    public String up(@RequestParam("up") Boolean up) {
        healthChecker.setUp(up);
        return up.toString();
    }
}
