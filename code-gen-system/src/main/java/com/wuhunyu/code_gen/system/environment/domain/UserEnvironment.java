package com.wuhunyu.code_gen.system.environment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 环境
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 19:19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEnvironment {

    /**
     * 环境id
     */
    private Long userEnvironmentId;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 需要去除的表前缀
     */
    private String tablePrefix;

    /**
     * 项目包名
     */
    private String projectPackage;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 子模块名
     */
    private String subModuleName;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 代码版本
     */
    private String version;

}
