package com.redspider.pss.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author QingChen
 * @date 2021/8/14
 * @description 请求过滤器
 * @since
 */

@Component
public class PssWebFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(PssWebFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("本次请求资源路径：{} {}", request.getMethod(), request.getRequestURI());
        logger.info("请求参数：{}", JSON.toJSONString(parameterMap));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
