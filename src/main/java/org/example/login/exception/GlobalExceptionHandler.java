package org.example.login.exception;

import org.example.login.common.BaseResponse;
import org.example.login.common.ErrorCode;
import org.example.login.common.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        // 业务异常日志
//        log.error("BusinessException: {}", e.getMessage(), e);
        return ResponseUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        // 记录运行时异常日志
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResponseUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}