package com.wuhunyu.code_gen.system.user.domain.dto;

import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息Dto
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = OperationTypeGroup.Update.class)
    private Long userId;

    /**
     * 登录名称
     */
    @NotBlank(message = "登录名称不能为空", groups = OperationTypeGroup.Insert.class)
    private String loginName;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

}
