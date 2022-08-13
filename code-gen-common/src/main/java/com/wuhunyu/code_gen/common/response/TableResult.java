package com.wuhunyu.code_gen.common.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * 列表响应
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/13 14:26
 */

@Getter
public final class TableResult<T> implements Serializable {
    private static final long serialVersionUID = 6712054101150495554L;

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String msg;

    /**
     * 列表数据
     */
    private final List<T> rows;

    /**
     * 总数据量
     */
    private final Long total;

    private TableResult(Integer code, String msg, List<T> rows, Long total) {
        this.code = code;
        this.msg = msg;
        this.rows = rows;
        this.total = total;
    }

    /**
     * 成功
     *
     * @param rows  列表数据
     * @param total 总数据量
     * @param <T>   响应泛型
     * @return ok
     */
    public static <T> TableResult<T> ok(List<T> rows, Long total) {
        return new TableResult<T>(
                ResponseCodeEnum.OK.getCode(),
                ResponseCodeEnum.OK.getDefaultMsg(),
                rows,
                total);
    }

}
