package com.wuhunyu.code_gen.common.utils;

import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 断言工具类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 19:21
 */

@Slf4j
public final class Assert {

    private Assert() {
    }

    /**
     * 断言
     *
     * @param expression 断言表达式
     * @param msg        异常提示信息
     */
    public static void isTrue(boolean expression, String msg) {
        if (expression) {
            log.info("主动异常: {}", msg);
            throw new BusinessException(msg);
        }
    }

    /**
     * 断言
     *
     * @param msg 异常提示信息
     */
    public static void isTrue(String msg) {
        log.info("主动异常: {}", msg);
        throw new BusinessException(msg);
    }

    /**
     * 断言
     *
     * @param expression            断言表达式
     * @param msg                   异常提示信息
     * @param runtimeExceptionClass 指定异常类型
     * @param <T>                   指定异常类型
     */
    public static <T extends RuntimeException> void isTrue(boolean expression,
                                                           String msg,
                                                           Class<T> runtimeExceptionClass) {
        if (expression) {
            try {
                log.info("主动异常: {}", msg);
                Constructor<T> constructor = runtimeExceptionClass.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                throw constructor.newInstance(msg);
            } catch (InvocationTargetException | NoSuchMethodException |
                    InstantiationException | IllegalAccessException e) {
                log.error("构建异常对象失败: {}", e.getLocalizedMessage(), e);
                throw new BusinessException(CommonConstant.DEFAULT_EXCEPTION_MSG);
            }
        }
    }

    /**
     * 断言
     *
     * @param msg                   异常提示信息
     * @param runtimeExceptionClass 指定异常类型
     * @param <T>                   指定异常类型
     */
    public static <T extends RuntimeException> void isTrue(String msg,
                                                           Class<T> runtimeExceptionClass) {
        Assert.isTrue(true, msg, runtimeExceptionClass);
    }

}
