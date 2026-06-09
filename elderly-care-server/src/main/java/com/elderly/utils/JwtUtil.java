package com.elderly.utils;

import com.elderly.common.config.RedisConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 密钥（至少256位）
    private static final String SECRET_KEY = "elderly-care-server-jwt-secret-key-2024";

    // Token 过期时间（2小时）
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000L;

    @Autowired
    private RedisUtil redisUtil;

    // 获取密钥
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成 Token
     * @param userId 用户ID
     * @param username 用户名
     * @param role 角色
     * @return JWT Token
     */
    public String generateToken(String userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 解析 Token
     * @param token JWT Token
     * @return JWT Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token 已过期");
        } catch (JwtException e) {
            throw new RuntimeException("Token 无效");
        }
    }

    /**
     * 从 Token 中获取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public String getUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", String.class);
    }

    /**
     * 从 Token 中获取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从 Token 中获取角色
     * @param token JWT Token
     * @return 角色
     */
    public String getRole(String token) {
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }

    /**
     * 获取 Token 剩余有效期（秒）
     * @param token JWT Token
     * @return 剩余秒数
     */
    public long getExpirationTime(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            if (expiration == null) {
                return 0;
            }
            long remaining = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            return Math.max(0, remaining);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 验证 Token 是否有效（包含黑名单检查）
     * @param token JWT Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 1. 基本格式和签名校验
            parseToken(token);

            // 2. 检查是否在黑名单中（登出后加入黑名单）
            String blacklistKey = RedisConstants.TOKEN_BLACKLIST_KEY + token;
            if (redisUtil.hasKey(blacklistKey)) {
                logger.debug("Token 在黑名单中");
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.debug("Token 验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 将 Token 加入黑名单（登出时调用）
     * @param token JWT Token
     */
    public void invalidateToken(String token) {
        // 计算 Token 剩余有效时间
        long remainingTime = getExpirationTime(token);
        if (remainingTime <= 0) {
            return;  // Token 已过期，无需加入黑名单
        }

        // 将 Token 存入黑名单，过期时间与 Token 剩余时间一致
        String blacklistKey = RedisConstants.TOKEN_BLACKLIST_KEY + token;
        redisUtil.set(blacklistKey, "1", remainingTime);
        logger.info("Token 已加入黑名单，剩余有效期: {}秒", remainingTime);
    }
}
