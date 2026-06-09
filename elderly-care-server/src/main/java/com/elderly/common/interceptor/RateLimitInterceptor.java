package com.elderly.common.interceptor;

import com.elderly.common.annotation.RateLimit;
import com.elderly.common.config.RedisConstants;
import com.elderly.common.vo.JSONReturn;
import com.elderly.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 限流拦截器
 * 使用 Redis 计数器实现基于 IP 的接口限流
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 只处理 Controller 方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        RateLimit rateLimit = method.getMethodAnnotation(RateLimit.class);

        // 如果没有限流注解，直接放行
        if (rateLimit == null) {
            return true;
        }

        // 获取客户端 IP
        String clientIp = getClientIp(request);

        // 构建限流 key
        String methodName = method.getMethod().getName();
        String keyPrefix = rateLimit.key();
        if (keyPrefix.isEmpty()) {
            keyPrefix = methodName;
        }
        String limitKey = RedisConstants.RATE_LIMIT_KEY + keyPrefix + ":" + clientIp;

        // 获取当前计数
        Object countObj = redisUtil.get(limitKey);
        long count;

        if (countObj == null) {
            // 首次访问，设置初始值和过期时间（1分钟）
            redisUtil.set(limitKey, 1, RedisConstants.LOGIN_FAIL_TTL);
            count = 1;
        } else {
            // 计数 +1
            count = redisUtil.increment(limitKey);
        }

        // 检查是否超过限制
        if (count > rateLimit.limit()) {
            logger.warn("接口限流触发: method={}, ip={}, count={}, limit={}",
                    methodName, clientIp, count, rateLimit.limit());

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    objectMapper.writeValueAsString(JSONReturn.failed("请求过于频繁，请稍后再试"))
            );
            return false;
        }

        return true;
    }

    /**
     * 获取客户端真实 IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 如果是多个代理，第一个 IP 为真实 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}
