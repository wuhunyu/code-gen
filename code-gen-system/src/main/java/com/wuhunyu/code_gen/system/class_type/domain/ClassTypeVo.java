package com.wuhunyu.code_gen.system.class_type.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类型映射Vo
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 18:23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeVo {

    /**
     * 类型映射id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
