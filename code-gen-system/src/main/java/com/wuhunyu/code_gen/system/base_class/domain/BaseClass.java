package com.wuhunyu.code_gen.system.base_class.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 11:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseClass {

    /**
     * 基类id
     */
    private Long baseClassId;

    /**
     * 基类名称
     */
    private String baseClassName;

    /**
     * 基类包名
     */
    private String packageName;

    /**
     * 基类类名
     */
    private String className;

}
