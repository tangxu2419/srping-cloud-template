package com.proxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/1911:08
 */
@Slf4j
@Component
public class ProxyServletFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String contextPath = requestContext.getRequest().getRequestURL().toString();
        log.info("请求地址：{}", contextPath);
        log.info("请求信息：{}", requestContext.toString());
        requestContext.addZuulRequestHeader("filePath", "/asdasd/asdasd/asdasdas.png");
        return null;
    }
}
