package com.wuhunyu.code_gen.web.controller;

import com.wuhunyu.code_gen.auth.AuthContextHolder;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import com.wuhunyu.code_gen.system.user.domain.dto.UserDto;
import com.wuhunyu.code_gen.system.user.domain.vo.UserVo;
import com.wuhunyu.code_gen.system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 9:02
 */

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 登录
     *
     * @param userDto 用户信息
     * @return 访问凭证
     */
    @PostMapping("/login")
    public String login(@RequestBody @Validated(OperationTypeGroup.Insert.class) UserDto userDto) {
        return userService.login(userDto);
    }

    /**
     * 注册
     *
     * @param userDto 用户信息
     * @return 访问凭证
     */
    @PostMapping("/register")
    public String register(@RequestBody @Validated(OperationTypeGroup.Insert.class) UserDto userDto) {
        return userService.register(userDto);
    }

    /**
     * 修改
     *
     * @param userDto 用户信息
     */
    @PutMapping
    public void updateUser(@RequestBody @Validated(OperationTypeGroup.Update.class) UserDto userDto) {
        userService.updateUser(userDto);
    }

    /**
     * 删除当前用户
     */
    @DeleteMapping
    public void deleteCurUser() {
        userService.deleteUserByUserId(AuthContextHolder.getUserId());
    }

    /**
     * 查询当前用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping
    public UserVo findCurUserVo() {
        return userService.findUserVoByUserId(AuthContextHolder.getUserId());
    }

}
