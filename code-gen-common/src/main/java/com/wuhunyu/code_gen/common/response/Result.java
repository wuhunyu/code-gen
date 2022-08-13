package com.wuhunyu.code_gen.common.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * 全局响应
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/13 13:11
 */

@Getter
public final class Result<T> implements Serializable {
    private static final long serialVersionUID = -965415085312107761L;

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String msg;

    /**
     * 响应数据
     */
    private final T data;

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param <T> 响应泛型
     * @return ok
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 成功
     *
     * @param data 响应参数
     * @param <T>  响应泛型
     * @return ok
     */
    public static <T> Result<T> ok(T data) {
        return new Result<T>(
                ResponseCodeEnum.OK.getCode(),
                ResponseCodeEnum.OK.getDefaultMsg(),
                data);
    }

    /**
     * 成功(列表分页)
     *
     * @param rows  列表数据
     * @param total 总数据量
     * @param <T>   响应泛型
     * @return ok
     */
    public static <T> TableResult<T> page(List<T> rows, Long total) {
        return TableResult.ok(rows, total);
    }

    /**
     * 失败
     *
     * @param <T> 响应泛型
     * @return error
     */
    public static <T> Result<T> error() {
        return Result.error(ResponseCodeEnum.SERVER_ERROR.getDefaultMsg());
    }

    /**
     * 失败
     *
     * @param msg 响应msg
     * @param <T> 响应泛型
     * @return error
     */
    public static <T> Result<T> error(String msg) {
        return new Result<T>(
                ResponseCodeEnum.SERVER_ERROR.getCode(),
                msg,
                null);
    }

}
