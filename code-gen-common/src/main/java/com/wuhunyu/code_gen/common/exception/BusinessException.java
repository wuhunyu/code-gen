package com.wuhunyu.code_gen.common.exception;

import com.wuhunyu.code_gen.common.response.ResponseCodeEnum;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 19:09
 */

@Getter
public class BusinessException extends RuntimeException {

    /**
     * 异常码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String msg;

    public BusinessException() {
        this.code = ResponseCodeEnum.SERVER_ERROR.getCode();
        this.msg = ResponseCodeEnum.SERVER_ERROR.getDefaultMsg();
    }

    public BusinessException(String msg) {
        this.code = ResponseCodeEnum.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
