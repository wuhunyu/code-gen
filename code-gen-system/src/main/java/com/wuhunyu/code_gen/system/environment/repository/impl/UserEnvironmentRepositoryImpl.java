package com.wuhunyu.code_gen.system.environment.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.environment.domain.UserEnvironment;
import com.wuhunyu.code_gen.system.environment.repository.UserEnvironmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
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
        String userEnvironmentSetKey = USER_ENVIRONMENT_SET + userId;
        Set<String> userEnvironmentIds = zRange(userEnvironmentSetKey);
        if (CollUtil.isEmpty(userEnvironmentIds)) {
            return Collections.EMPTY_LIST;
        }
        // 根据环境id查询全部的环境信息
        List<UserEnvironment> list = new ArrayList<>(userEnvironmentIds.size());
        try {
            for (String userEnvironmentId : userEnvironmentIds) {
                String userEnvironmentMapKey = USER_ENVIRONMENT_MAP + userEnvironmentId;
                list.add(hGetAll(userEnvironmentMapKey, UserEnvironment.class));
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("查询环境信息失败");
        }
        return list;
    }

    @Override
    public UserEnvironment findUserEnvironmentByUserEnvironmentId(Long userEnvironmentId) {
        String userEnvironmentMapKey = USER_ENVIRONMENT_MAP + userEnvironmentId;
        try {
            return hGetAll(userEnvironmentMapKey, UserEnvironment.class);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("查询环境信息失败");
        }
        return null;
    }

    @Override
    public void insertUserEnvironment(UserEnvironment userEnvironment, Long userId) {
        // 添加 环境信息
        String userEnvironmentMapKey = USER_ENVIRONMENT_MAP + userEnvironment.getUserEnvironmentId().toString();
        try {
            hSet(userEnvironmentMapKey, userEnvironment);
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("添加环境信息失败");
        }

        // 添加用户 环境映射关系
        String userEnvironmentSetKey = USER_ENVIRONMENT_SET + userId;
        zAdd(userEnvironmentSetKey, userEnvironment.getUserEnvironmentId().toString());
    }

    @Override
    public void updateUserEnvironment(UserEnvironment userEnvironment, Long userId) {
        // 更新 用户 环境 映射关系
        String userEnvironmentSetKey = USER_ENVIRONMENT_SET + userId;
        zAdd(userEnvironmentSetKey, userEnvironment.getUserEnvironmentId().toString());

        // 更新 环境信息
        String userEnvironmentMapKey = USER_ENVIRONMENT_MAP + userEnvironment.getUserEnvironmentId();
        try {
            hSet(userEnvironmentMapKey, userEnvironment);
        } catch (IllegalAccessException e) {
            log.error("反射异常: {}", e.getLocalizedMessage(), e);
            Assert.isTrue("修改环境信息失败");
        }
    }

    @Override
    public void deleteUserEnvironmentByUserEnvironmentId(Long userEnvironmentId, Long userId) {
        // 删除 用户 环境 映射关系
        String userEnvironmentSetKey = USER_ENVIRONMENT_SET + userId;
        zRemove(userEnvironmentSetKey, userEnvironmentId.toString());

        // 删除指定 环境信息
        String userEnvironmentMapKey = USER_ENVIRONMENT_MAP + userEnvironmentId;
        delete(userEnvironmentMapKey);
    }

    @Override
    public boolean existsUserEnvironmentByUserEnvironmentIdAndUserId(Long userEnvironmentId, Long userId) {
        String userEnvironmentSetKey = USER_ENVIRONMENT_SET + userId;
        return zExists(userEnvironmentSetKey, userId.toString());
    }

    @Override
    public void sortUserEnvironments(List<Long> userEnvironmentIds, Long userId) {

    }
}
