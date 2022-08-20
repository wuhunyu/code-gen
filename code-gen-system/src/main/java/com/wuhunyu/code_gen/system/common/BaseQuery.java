package com.wuhunyu.code_gen.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 查询基类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 16:47
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseQuery {

    /**
     * 起始页码
     */
    @NotNull(message = "起始页码不能为空")
    @Min(value = 1L, message = "起始页面取值非法")
    private Long startPage;

    /**
     * 每页展示数量
     */
    @NotNull(message = "每页展示数量不能为空")
    @Min(value = 1L, message = "每页展示数量取值非法")
    private Long size;

}
