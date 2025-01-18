package com.mybank.management.transaction.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
/**
 * @author zhangdaochuan
 * @time 2025/1/17 01:51
 */
@Component
public class AccessLogInterceptor implements HandlerInterceptor {

    private static final Logger apiLogger = LoggerFactory.getLogger("apiLogger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 设置开始时间
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        // 请求摘要
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUrl = queryString == null ? uri : uri + "?" + queryString;

        // 状态码
        int status = response.getStatus();

        // 日志记录
        apiLogger.info("Request: method={}, url={}, status={}, duration={}ms",
                method, fullUrl, status, duration);

        if (ex != null) {
            apiLogger.error("Exception occurred during request: {}", ex.getMessage(), ex);
        }
    }
}
