package com.wuhunyu.code_gen.system.environment.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.system.environment.domain.UserEnvironment;
import com.wuhunyu.code_gen.system.environment.repository.UserEnvironmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.UserEnvironmentKeys.USER_ENVIRONMENT_MAP;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.UserEnvironmentKeys.USER_ENVIRONMENT_SET;

/**
 * 环境 数据访问实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 20:09
 */

@Repository("userEnvironmentRepository")
@Primary
@Slf4j
public class UserEnvironmentRepositoryImpl implements UserEnvironmentRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserEnvironment> listUserEnvironmentByUserId(Long userId) {
        // 查询所有的 用户 环境 映射关系
        Set<String> userEnvironmentIds = zRange(this.getUserEnvironmentSetKey(userId));
        if (CollUtil.isEmpty(userEnvironmentIds)) {
            return Collections.EMPTY_LIST;
        }
        // 根据环境id查询全部的环境信息
        List<UserEnvironment> list = new ArrayList<>(userEnvironmentIds.size());
        for (String userEnvironmentId : userEnvironmentIds) {
            list.add(hGetAll(this.getUserEnvironmentMapKey(userEnvironmentId), UserEnvironment.class));
        }
        return list;
    }

    @Override
    public UserEnvironment findUserEnvironmentByUserEnvironmentId(Long userEnvironmentId) {
        return hGetAll(this.getUserEnvironmentMapKey(userEnvironmentId), UserEnvironment.class);
    }

    @Override
    public void insertUserEnvironment(UserEnvironment userEnvironment, Long userId) {
        // 添加 环境信息
        hSet(this.getUserEnvironmentMapKey(userEnvironment.getUserEnvironmentId()), userEnvironment);

        // 添加用户 环境映射关系
        zAdd(this.getUserEnvironmentSetKey(userId), userEnvironment.getUserEnvironmentId().toString());
    }

    @Override
    public void updateUserEnvironment(UserEnvironment userEnvironment, Long userId) {
        // 更新 用户 环境 映射关系
        zAdd(this.getUserEnvironmentSetKey(userId), userEnvironment.getUserEnvironmentId().toString());

        // 更新 环境信息
        hSet(this.getUserEnvironmentMapKey(userEnvironment.getUserEnvironmentId()), userEnvironment);
    }

    @Override
    public void deleteUserEnvironmentByUserEnvironmentId(Long userEnvironmentId, Long userId) {
        // 删除 用户 环境 映射关系
        zRemove(this.getUserEnvironmentSetKey(userId), userEnvironmentId.toString());

        // 删除指定 环境信息
        delete(this.getUserEnvironmentMapKey(userEnvironmentId));
    }

    @Override
    public boolean existsUserEnvironmentByUserEnvironmentIdAndUserId(Long userEnvironmentId, Long userId) {
        return zExists(this.getUserEnvironmentSetKey(userId), userEnvironmentId.toString());
    }

    @Override
    public void sortUserEnvironments(List<Long> userEnvironmentIds, Long userId) {
        // 删除现在的 用户 环境 映射关系
        String userEnvironmentSetKey = this.getUserEnvironmentSetKey(userId);
        delete(userEnvironmentSetKey);

        // 新增 用户 环境 映射关系
        long currentTimeMillis = System.currentTimeMillis();
        for (Long userEnvironmentId : userEnvironmentIds) {
            zAdd(userEnvironmentSetKey, userEnvironmentId.toString(), currentTimeMillis++);
        }
    }

    @Override
    public Long countUserEnvironmentNumByUserId(Long userId) {
        // 统计指定的 用户 环境 映射关系 个数
        return countZSet(this.getUserEnvironmentSetKey(userId));
    }

    /**
     * 构造 用户&环境 访问key
     *
     * @param userId 用户id
     * @return 用户&环境 访问key
     */
    private String getUserEnvironmentSetKey(Long userId) {
        return USER_ENVIRONMENT_SET + userId;
    }

    /**
     * 构造 环境 访问key
     *
     * @param userEnvironmentId 环境id
     * @return 环境 访问key
     */
    private String getUserEnvironmentMapKey(Long userEnvironmentId) {
        return USER_ENVIRONMENT_MAP + userEnvironmentId;
    }

    /**
     * 构造 环境 访问key
     *
     * @param userEnvironmentId 环境id
     * @return 环境 访问key
     */
    private String getUserEnvironmentMapKey(String userEnvironmentId) {
        return USER_ENVIRONMENT_MAP + userEnvironmentId;
    }

}
