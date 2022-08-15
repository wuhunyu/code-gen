package com.wuhunyu.code_gen.system.user.repository.impl;

import com.wuhunyu.code_gen.system.user.domain.User;
import com.wuhunyu.code_gen.system.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.UserKeys.*;

/**
 * 用户信息 数据访问实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 17:11
 */

@Repository("userRepository")
@Primary
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void insertUser(User user) {
        // 新增用户信息
        try {
            hSet(USER_MAP, user);
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            // TODO 抛出异常
            return;
        }
        // 新增用户登录名称映射信息
        hSet(LOGIN_USER_NAME_MAP, user.getLoginName(), user.getUserId());
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUserByUserId(Long userId) {

    }
}
