package com.wuhunyu.code_gen.system.user.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息Vo
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVo {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

}
