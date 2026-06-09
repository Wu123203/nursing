package com.elderly.common.lock;

import com.elderly.common.config.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁工具类
 * 使用 Redis 实现分布式锁，支持重试获取锁
 */
@Component
public class DistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 尝试获取锁（立即返回）
     *
     * @param lockKey    锁的 key
     * @param requestId  请求标识（用于释放锁时校验，防止误删其他线程的锁）
     * @param expireTime 过期时间（秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        String key = RedisConstants.LOCK_KEY + lockKey;
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(key, requestId, expireTime, TimeUnit.SECONDS);
        boolean success = Boolean.TRUE.equals(result);

        if (success) {
            logger.debug("获取锁成功: key={}, requestId={}", key, requestId);
        } else {
            logger.debug("获取锁失败: key={}", key);
        }

        return success;
    }

    /**
     * 尝试获取锁（带重试）
     *
     * @param lockKey     锁的 key
     * @param requestId   请求标识
     * @param expireTime  过期时间（秒）
     * @param retryTimes  重试次数
     * @param retryDelay  重试间隔（毫秒）
     * @return 是否获取成功
     */
    public boolean tryLockWithRetry(String lockKey, String requestId,
                                    long expireTime, int retryTimes, long retryDelay) {
        // 先尝试一次
        if (tryLock(lockKey, requestId, expireTime)) {
            return true;
        }

        // 重试获取锁
        for (int i = 0; i < retryTimes; i++) {
            try {
                Thread.sleep(retryDelay);
                if (tryLock(lockKey, requestId, expireTime)) {
                    return true;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("获取锁被中断: key={}", lockKey);
                return false;
            }
        }

        logger.warn("获取锁失败，已达最大重试次数: key={}, retryTimes={}", lockKey, retryTimes);
        return false;
    }

    /**
     * 释放锁（使用 Lua 脚本保证原子性：校验 + 删除）
     *
     * @param lockKey   锁的 key
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        String key = RedisConstants.LOCK_KEY + lockKey;

        // Lua 脚本：只有当 key 的值等于 requestId 时才删除
        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then" +
                "    return redis.call('del', KEYS[1])" +
                "else" +
                "    return 0" +
                "end";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript,
                Collections.singletonList(key), requestId);

        boolean success = result != null && result == 1;
        if (success) {
            logger.debug("释放锁成功: key={}, requestId={}", key, requestId);
        } else {
            logger.debug("释放锁失败（锁可能已过期或不属于当前请求）: key={}, requestId={}", key, requestId);
        }

        return success;
    }

    /**
     * 生成唯一的请求标识
     *
     * @return UUID 字符串（去掉横杠）
     */
    public String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 尝试获取锁（使用默认过期时间）
     *
     * @param lockKey 锁的 key
     * @return 请求标识（用于释放锁），获取失败返回 null
     */
    public String tryLock(String lockKey) {
        String requestId = generateRequestId();
        if (tryLock(lockKey, requestId, RedisConstants.LOCK_EXPIRE_TIME)) {
            return requestId;
        }
        return null;
    }

    /**
     * 尝试获取锁（使用默认过期时间，带重试）
     *
     * @param lockKey    锁的 key
     * @param retryTimes 重试次数
     * @param retryDelay 重试间隔（毫秒）
     * @return 请求标识（用于释放锁），获取失败返回 null
     */
    public String tryLockWithRetry(String lockKey, int retryTimes, long retryDelay) {
        String requestId = generateRequestId();
        if (tryLockWithRetry(lockKey, requestId, RedisConstants.LOCK_EXPIRE_TIME, retryTimes, retryDelay)) {
            return requestId;
        }
        return null;
    }
}
