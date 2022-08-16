package com.wuhunyu.code_gen.system.user.repository.impl;

import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.user.domain.User;
import com.wuhunyu.code_gen.system.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;

import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.UserKeys.LOGIN_NAME_MAP;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.UserKeys.USER_MAP;

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
    public User findUserByUserId(Long userId) {
        // 根据 userId 查询用户信息
        String userKey = USER_MAP + userId;
        try {
            return hGetAll(userKey, User.class);
        } catch (NoSuchMethodException | InvocationTargetException |
                InstantiationException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("查询用户信息失败");
        }
        return null;
    }

    @Override
    public User findUserByLoginName(String loginName) {
        // 根据 loginName 查询 userId
        try {
            Long userId = hGet(LOGIN_NAME_MAP, loginName, Long.class);
            if (userId == null) {
                return null;
            }
            return this.findUserByUserId(userId);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("查询用户信息失败");
        }
        return null;
    }

    @Override
    public void insertUser(User user) {
        Assert.isTrue(user.getUserId() == null, "用户id不能为空");
        // 新增用户信息
        try {
            hSet(USER_MAP + user.getUserId(), user);
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("新增用户失败");
        }
        // 新增用户登录名称映射信息
        hSet(LOGIN_NAME_MAP, user.getLoginName(), user.getUserId());
    }

    @Override
    public void updateUser(User user) {
        Assert.isTrue(user.getUserId() == null, "用户id不能为空");
        // 根据 userId 查询用户信息
        String userKey = USER_MAP + user.getUserId();
        Assert.isTrue(!exists(userKey), "用户信息不存在");
        try {
            // 执行修改
            hSet(userKey, user);
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("修改用户失败");
        }
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        // 根据 userId 查询用户信息
        String userKey = USER_MAP + userId;
        if (exists(userKey)) {
            // 删除指定用户
            delete(userKey);
        }
    }
}
