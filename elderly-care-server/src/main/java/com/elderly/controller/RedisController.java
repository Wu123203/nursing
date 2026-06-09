package com.elderly.controller;

import com.elderly.common.vo.JSONReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis 测试控制器
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试 Redis 连接
     */
    @GetMapping("/test")
    public JSONReturn testConnection() {
        try {
            // 测试设置和获取
            String key = "test_key";
            String value = "test_value";
            
            redisTemplate.opsForValue().set(key, value);
            Object result = redisTemplate.opsForValue().get(key);
            
            if (value.equals(result)) {
                Map<String, Object> data = new HashMap<>();
                data.put("status", "success");
                data.put("message", "Redis 连接成功！");
                data.put("key", key);
                data.put("value", result);
                return JSONReturn.success(data);
            } else {
                return JSONReturn.failed("Redis 连接失败！");
            }
        } catch (Exception e) {
            return JSONReturn.failed("Redis 连接异常: " + e.getMessage());
        }
    }

    /**
     * 设置缓存
     */
    @GetMapping("/set")
    public JSONReturn setCache(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return JSONReturn.success("缓存设置成功");
        } catch (Exception e) {
            return JSONReturn.failed("缓存设置失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存
     */
    @GetMapping("/get")
    public JSONReturn getCache(String key) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            Map<String, Object> data = new HashMap<>();
            data.put("key", key);
            data.put("value", value);
            return JSONReturn.success(data);
        } catch (Exception e) {
            return JSONReturn.failed("获取缓存失败: " + e.getMessage());
        }
    }

    /**
     * 删除缓存
     */
    @GetMapping("/delete")
    public JSONReturn deleteCache(String key) {
        try {
            Boolean result = redisTemplate.delete(key);
            if (result) {
                return JSONReturn.success("缓存删除成功");
            } else {
                return JSONReturn.failed("缓存不存在");
            }
        } catch (Exception e) {
            return JSONReturn.failed("删除缓存失败: " + e.getMessage());
        }
    }

    /**
     * 测试验证码缓存（模拟实际使用场景）
     */
    @GetMapping("/captcha")
    public JSONReturn testCaptcha() {
        try {
            String sessionId = "test_session";
            String captcha = "1234";
            
            // 模拟存储验证码，有效期5分钟
            redisTemplate.opsForValue().set("captcha:" + sessionId, captcha, 5, java.util.concurrent.TimeUnit.MINUTES);
            
            // 获取验证码
            String storedCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + sessionId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("status", "success");
            data.put("message", "验证码缓存测试成功");
            data.put("sessionId", sessionId);
            data.put("captcha", storedCaptcha);
            return JSONReturn.success(data);
        } catch (Exception e) {
            return JSONReturn.failed("验证码缓存测试失败: " + e.getMessage());
        }
    }
}
