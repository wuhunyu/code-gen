package com.wuhunyu.code_gen.web.handler;

import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.common.exception.BusinessException;
import com.wuhunyu.code_gen.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 21:11
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionListener(Exception e) {
        log.info("全局异常: {}", e.getLocalizedMessage(), e);
        return Result.error(CommonConstant.DEFAULT_EXCEPTION_MSG);
    }

    @ExceptionHandler(BusinessException.class)
    public Result<String> businessExceptionListener(BusinessException e) {
        log.info("自定义异常: {}", e.getMsg());
        return Result.error(e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> bindExceptionListener(BindException e) {
        String defaultMessage = e.getAllErrors().get(0).getDefaultMessage();
        log.info("参数校验异常: {}", defaultMessage);
        return Result.error(defaultMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionListener(MethodArgumentNotValidException e) {
        String defaultMessage = e.getAllErrors().get(0).getDefaultMessage();
        log.info("参数校验异常: {}", defaultMessage);
        return Result.error(defaultMessage);
    }

}
