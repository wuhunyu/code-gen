package com.wuhunyu.code_gen.system.user.repository.impl;

import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.user.domain.User;
import com.wuhunyu.code_gen.system.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

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
        return hGetAll(this.getUserMapKey(userId), User.class);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        // 根据 loginName 查询 userId
        Long userId = hGet(this.getLoginNameMapKey(), loginName, Long.class);
        if (userId == null) {
            return null;
        }
        return this.findUserByUserId(userId);
    }

    @Override
    public void insertUser(User user) {
        Assert.isTrue(user.getUserId() == null, "用户id不能为空");
        // 新增用户信息
        hSet(this.getUserMapKey(user.getUserId()), user);
        // 新增用户登录名称映射信息
        hSet(this.getLoginNameMapKey(), user.getLoginName(), user.getUserId());
    }

    @Override
    public void updateUser(User user) {
        Assert.isTrue(user.getUserId() == null, "用户id不能为空");
        // 根据 userId 查询用户信息
        String userKey = this.getUserMapKey(user.getUserId());
        Assert.isTrue(!exists(userKey), "用户信息不存在");
        // 执行修改
        hSet(userKey, user);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        // 根据 userId 查询用户信息
        String userKey = this.getUserMapKey(userId);
        if (exists(userKey)) {
            // 删除指定用户
            delete(userKey);
        }
    }

    /**
     * 构造 用户 访问key
     *
     * @param userId 用户id
     * @return 用户 访问key
     */
    private String getUserMapKey(Long userId) {
        return USER_MAP + userId;
    }

    /**
     * 返回 用户登录名称 访问key
     *
     * @return 用户登录名称 访问key
     */
    private String getLoginNameMapKey() {
        return LOGIN_NAME_MAP;
    }

}
