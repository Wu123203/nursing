package com.elderly.common.exception;

import com.elderly.common.enums.ErrorCode;
import com.elderly.common.vo.JSONReturn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;


/**
 * 异常全局处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //日志
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //自定义业务异常
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public JSONReturn<?> handleBizException(HttpServletRequest request, BizException e) {
        logger.warn("业务异常 [{}]: {}", e.getCode(), e.getMessage());
        return JSONReturn.failed(e.getCode(), e.getMessage());
    }

    //参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JSONReturn<?> handleValidationException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
        logger.warn("参数校验失败：{}", message);
        return JSONReturn.failed(ErrorCode.PARAM_VALID_ERROR, message);
    }

    //安全异常（SQL注入等）
    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public JSONReturn<?> handleSecurityException(HttpServletRequest request, SecurityException e) {
        logger.warn("安全异常：{}", e.getMessage());
        return JSONReturn.failed(ErrorCode.SQL_INJECT_ERROR, e.getMessage());
    }

    //非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public JSONReturn<?> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        logger.warn("非法参数：{}", e.getMessage());
        return JSONReturn.failed(ErrorCode.PARAM_ERROR, e.getMessage());
    }

    //系统未知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONReturn<?> handleException(HttpServletRequest request, Exception e) {
        logger.error("系统内部异常：", e);
        return JSONReturn.failed(ErrorCode.SYSTEM_ERROR);
    }
}
