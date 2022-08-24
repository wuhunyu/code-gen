package com.wuhunyu.code_gen.system.environment.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 环境信息dto
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 19:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEnvironmentDto {

    /**
     * 环境id
     */
    @NotNull(message = "环境id不能为空", groups = OperationTypeGroup.Update.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userEnvironmentId;

    /**
     * 配置名称
     */
    @Pattern(regexp = CommonConstant.ENGLISH_CHAR_STR_REGEXP, message = "配置名称只能由英文字母或'_'或'-'组成")
    private String configName;

    /**
     * 需要去除的表前缀
     */
    private String tablePrefix;

    /**
     * 项目包名
     */
    @NotBlank(message = "项目包名不能为空")
    private String projectPackage;

    /**
     * 模块名
     */
    @NotBlank(message = "模块名不能为空")
    private String moduleName;

    /**
     * 子模块名
     */
    @NotBlank(message = "子模块名不能为空")
    private String subModuleName;

    /**
     * 作者名称
     */
    @NotBlank(message = "作者名称不能为空")
    private String author;

    /**
     * 代码版本
     */
    @NotBlank(message = "代码版本不能为空")
    private String version;

    /**
     * 基类配置id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "基类不能为空")
    private Long baseClassId;

    /**
     * 数据源id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "数据源不能为空")
    private Long dataSourceId;

}
