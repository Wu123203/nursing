package com.elderly.common.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 * 用于限制接口的访问频率
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 每分钟允许的请求次数
     */
    int limit() default 10;

    /**
     * 限流 key 的前缀（可选，默认使用方法名）
     */
    String key() default "";
}
