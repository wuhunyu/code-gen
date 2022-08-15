package com.wuhunyu.code_gen.system.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wuhunyu.code_gen.system.user.domain.dto.UserDto;
import com.wuhunyu.code_gen.system.user.domain.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 15:59
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录密码
     */
    private String password;

    public static User convertUserDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setLoginName(userDto.getLoginName());
        user.setNickName(userDto.getNickName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserVo convertUserToUserVo(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        userVo.setUserId(user.getUserId());
        userVo.setNickName(user.getNickName());
        return userVo;
    }

}
