package com.elderly.common.config;

public class RedisConstants {

    private RedisConstants() {}

    // ============ 缓存前缀 ============

    public static final String CACHE_NEWS_KEY = "cache:news:";
    public static final String CACHE_NEWS_LIST_KEY = "cache:news:list:";
    public static final long CACHE_NEWS_TTL = 300L;

    public static final String CACHE_NOTICE_KEY = "cache:notice:";
    public static final String CACHE_NOTICE_LIST_KEY = "cache:notice:list:";
    public static final long CACHE_NOTICE_TTL = 600L;

    public static final String CACHE_RESTHOME_KEY = "cache:resthome:";
    public static final String CACHE_RESTHOME_LIST_KEY = "cache:resthome:list:";
    public static final long CACHE_RESTHOME_TTL = 1800L;

    public static final String CACHE_COUNT_KEY = "cache:count";
    public static final long CACHE_COUNT_TTL = 300L;

    public static final String CACHE_HOT_NEWS_KEY = "cache:news:hot";
    public static final long CACHE_HOT_NEWS_TTL = 600L;

    public static final String CACHE_EMPLOYEE_LIST_KEY = "cache:employee:list";
    public static final long CACHE_EMPLOYEE_LIST_TTL = 600L;

    public static final String CACHE_BED_STATUS_KEY = "cache:bed:status:";
    public static final long CACHE_BED_STATUS_TTL = 300L;

    // ============ 验证码 ============

    public static final String CAPTCHA_KEY = "captcha:";
    public static final long CAPTCHA_TTL = 120L;

    // ============ JWT Token 黑名单 ============

    public static final String TOKEN_BLACKLIST_KEY = "token:blacklist:";
    public static final long TOKEN_BLACKLIST_TTL = 604800L;

    // ============ 页面浏览量 ============

    public static final String VIEW_COUNT_KEY = "view:count:";
    public static final String VIEW_COUNT_NEWS_KEY = "view:count:news:";

    // ============ 接口限流 ============

    public static final String RATE_LIMIT_KEY = "rate:limit:";

    // ============ 分布式锁 ============

    public static final String LOCK_KEY = "lock:";
    public static final long LOCK_EXPIRE_TIME = 10L;

    // ============ 点赞数统计 ============

    public static final String LIKE_COUNT_KEY = "like:count:";
    public static final String LIKE_USER_KEY = "like:user:";

    // ============ 登录限制 ============

    public static final String LOGIN_FAIL_KEY = "login:fail:";
    public static final long LOGIN_FAIL_TTL = 300L;
    public static final int MAX_LOGIN_FAIL = 5;
}
