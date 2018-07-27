package com.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangxu
 * @date 2018/7/2614:08
 */
@RestController
public class ConfigController {

    @Value("${test}")
    String str;

    @RequestMapping("/test")
    public String test(){
        return str;
    }

}
