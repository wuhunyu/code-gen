package com.wuhunyu.code_gen.system.user.service.impl;

import com.wuhunyu.code_gen.system.user.domain.dto.UserDto;
import com.wuhunyu.code_gen.system.user.domain.vo.UserVo;
import com.wuhunyu.code_gen.system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 用户信息处理 实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:10
 */

@Service("userService")
@RequiredArgsConstructor
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public String login(UserDto userDto) {

        return null;
    }

    @Override
    public String register(UserDto userDto) {

        return null;
    }

    @Override
    public void updateUser(UserDto userDto) {

    }

    @Override
    public void deleteCurUser() {

    }

    @Override
    public UserVo findUserCurVo() {
        return null;
    }
}
