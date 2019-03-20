package com.proxy.config;

import com.proxy.filter.ProxyFileServletFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/2016:10
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器，并且配置拦截路径等参数
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new ProxyFileServletFilter()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
