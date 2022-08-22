package com.wuhunyu.code_gen.system.data_source.dynamic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段数据
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/22 21:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldData {

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型(小写)
     */
    private String fieldType;

    /**
     * 是否为主键
     */
    private Integer primaryKey;

    /**
     * 字段注释
     */
    private String fieldComment;

}
