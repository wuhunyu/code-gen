package com.wuhunyu.code_gen.system.data_source.basic.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据源Dto
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/19 17:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceInfoDto {

    /**
     * 数据源id
     */
    @NotNull(message = "数据源id不能为空", groups = OperationTypeGroup.Update.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    @NotNull(message = "数据源类型不能为空")
    private Integer dbType;

    /**
     * 连接名
     */
    @NotBlank(message = "连接名不能为空")
    private String connectionName;

    /**
     * 连接url
     */
    @NotBlank(message = "连接url不能为空")
    private String connectionUrl;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
