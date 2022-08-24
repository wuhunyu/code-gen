package com.wuhunyu.code_gen.system.environment.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 环境配置Vo
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 15:12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEnvironmentVo {

    /**
     * 环境id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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

    /**
     * 基类配置名称
     */
    private String baseClassName;

    /**
     * 连接名称
     */
    private String connectionName;

}
