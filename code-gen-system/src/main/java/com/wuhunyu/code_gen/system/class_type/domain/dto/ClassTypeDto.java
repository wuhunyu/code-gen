package com.wuhunyu.code_gen.system.class_type.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 类型映射Dto
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 18:17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto {

    /**
     * 类型映射信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class ClassTypeInfoDto {

        /**
         * 类型映射id
         */
        @JsonSerialize(using = ToStringSerializer.class)
        @NotNull(message = "类型映射id不能为空")
        private Long classTypeId;

        /**
         * 数据库类型
         */
        @NotBlank(message = "数据库类型不能为空")
        private String jdbcType;

        /**
         * java类型所在包名
         */
        @NotBlank(message = "java类型所在包名不能为空")
        private String javaTypePackage;

        /**
         * java类型名称
         */
        @NotBlank(message = "java类型名称不能为空")
        private String javaTypeName;

    }

    @NotEmpty(message = "类型映射信息不能为空")
    private List<ClassTypeInfoDto> classTypeInfoDtos;

    @NotNull(message = "环境id不能为空")
    private Long userEnvironmentId;

}
