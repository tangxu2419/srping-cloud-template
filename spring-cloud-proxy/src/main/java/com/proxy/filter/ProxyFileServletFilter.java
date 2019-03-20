package com.proxy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tangxu
 * @Title: 拦截器
 * @date 2019/3/2016:17
 */
@Slf4j
public class ProxyFileServletFilter implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器访问方法：" + request.getServletPath());
        String contextPath = request.getRequestURL().toString();
        log.info("请求地址：{}", contextPath);
        log.info("请求信息：{}", request.toString());
        request.setAttribute("filePath", "/asdasd/asdasd/asdasdas.png");
        response.sendRedirect("http://localhost:2000" + request.getServletPath());
        return true;
    }

    /**
     * 执行目标方法之后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 在请求已经返回之后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
