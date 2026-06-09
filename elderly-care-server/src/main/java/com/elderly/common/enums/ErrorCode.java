package com.elderly.common.enums;

/**
 * 统一错误码枚举
 */
public enum ErrorCode {
    
    // 成功
    SUCCESS(200, "操作成功"),
    
    // 通用错误 1000-1099
    FAILED(1000, "操作失败"),
    PARAM_ERROR(1001, "参数错误"),
    PARAM_VALID_ERROR(1002, "参数校验失败"),
    
    // 认证授权错误 2000-2099
    UNAUTHORIZED(2000, "未授权"),
    TOKEN_EXPIRED(2001, "Token已过期"),
    TOKEN_INVALID(2002, "Token无效"),
    LOGIN_FAILED(2003, "登录失败"),
    USER_NOT_FOUND(2004, "用户不存在"),
    PASSWORD_ERROR(2005, "密码错误"),
    CAPTCHA_ERROR(2006, "验证码错误"),
    
    // 业务逻辑错误 3000-3099
    DATA_NOT_FOUND(3000, "数据不存在"),
    DATA_EXISTS(3001, "数据已存在"),
    OPERATION_NOT_ALLOWED(3002, "操作不允许"),
    BUSINESS_ERROR(3003, "业务逻辑错误"),
    
    // 安全错误 4000-4099
    SECURITY_ERROR(4000, "安全错误"),
    SQL_INJECT_ERROR(4001, "检测到SQL注入攻击"),
    
    // 系统错误 5000-5099
    SYSTEM_ERROR(5000, "系统繁忙，请稍后重试"),
    DATABASE_ERROR(5001, "数据库操作失败"),
    NETWORK_ERROR(5002, "网络异常");
    
    private final Integer code;
    private final String message;
    
    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}