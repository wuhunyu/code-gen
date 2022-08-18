package com.wuhunyu.code_gen.system.base_class.domain.dto;

import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 基类Dto
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 11:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseClassDto {

    /**
     * 基类id
     */
    @NotNull(message = "基类id不能为空", groups = OperationTypeGroup.Update.class)
    private Long baseClassId;

    /**
     * 基类名称
     */
    @NotBlank(message = "基类名称不能为空")
    private String baseClassName;

    /**
     * 基类包名
     */
    @NotBlank(message = "基类包名不能为空")
    private String packageName;

    /**
     * 基类类名
     */
    @NotBlank(message = "基类类名不能为空")
    private String className;

}
