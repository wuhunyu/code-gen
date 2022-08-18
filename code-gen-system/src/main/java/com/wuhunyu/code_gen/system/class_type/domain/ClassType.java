package com.wuhunyu.code_gen.system.class_type.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类型映射
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 18:08
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassType {

    /**
     * 类型映射id
     */
    private Long classTypeId;

    /**
     * 数据库类型
     */
    private String jdbcType;

    /**
     * java类型所在包名
     */
    private String javaTypePackage;

    /**
     * java类型名称
     */
    private String javaTypeName;

}
