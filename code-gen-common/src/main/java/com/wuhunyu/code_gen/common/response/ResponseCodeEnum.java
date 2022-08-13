package com.wuhunyu.code_gen.common.response;

/**
 * 响应码枚举
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/13 14:16
 */

public enum ResponseCodeEnum {

    /**
     * 成功
     */
    OK(200, "ok"),

    /**
     * 请求参数异常
     */
    PARAMS_EXCEPTION(400, "请求参数异常"),

    /**
     * 服务器异常
     */
    SERVER_ERROR(500, "server error");

    private final int code;

    private final String defaultMsg;

    ResponseCodeEnum(int code, String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }
}
