package com.wuhunyu.code_gen.system.base_class.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.system.base_class.domain.BaseClass;
import com.wuhunyu.code_gen.system.base_class.repository.BaseClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;
import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.BaseClassKeys.*;

/**
 * 基类 数据访问实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 11:40
 */

@Repository("baseClassRepository")
@Primary
@RequiredArgsConstructor
@Slf4j
public class BaseClassRepositoryImpl implements BaseClassRepository {

    @Override
    public List<BaseClass> listBaseClasses(Long userId) {
        // 获取指定用户下的所有基类配置
        Set<String> baseClassIds = zRange(this.getBaseClassSetKey(userId));

        // 返回空
        if (CollUtil.isEmpty(baseClassIds)) {
            return Collections.emptyList();
        }
        List<BaseClass> list = new ArrayList<>(baseClassIds.size());
        for (String baseClassId : baseClassIds) {
            list.add(hGetAll(this.getBaseClassMapKey(baseClassId), BaseClass.class));
        }
        return list;
    }

    @Override
    public BaseClass findBaseClassByBaseClassIdAndUserId(Long baseClassId, Long userId) {
        // 查询指定的基类配置
        return hGetAll(this.getBaseClassMapKey(baseClassId), BaseClass.class);
    }

    @Override
    public void insertBaseClass(BaseClass baseClass, Long userId) {
        // 新增一个配置信息
        hSet(this.getBaseClassMapKey(baseClass.getBaseClassId()), baseClass);

        // 新增 用户与基类配置关系
        zAdd(this.getBaseClassSetKey(userId), userId.toString());
    }

    @Override
    public void updateBaseClass(BaseClass baseClass, Long userId) {
        // 修改配置信息
        hSet(this.getBaseClassMapKey(baseClass.getBaseClassId()), baseClass);
    }

    @Override
    public void deleteBaseClassByBaseClassId(Long baseClassId, Long userId) {
        // 删除 用户与基类配置的关联关系
        zRemove(this.getBaseClassSetKey(userId), baseClassId.toString());

        // 删除 基类配置信息
        delete(this.getBaseClassMapKey(baseClassId));
    }

    @Override
    public Long countBaseClassNumByUserId(Long userId) {
        return countZSet(this.getBaseClassSetKey(userId));
    }

    /**
     * 构造 用户&基类 访问key
     *
     * @param userId 用户id
     * @return 用户&基类 访问key
     */
    private String getBaseClassSetKey(Long userId) {
        return BASE_CLASS_SET + userId;
    }

    /**
     * 构造 基类 访问key
     *
     * @param baseClassId 基类id
     * @return 基类 访问key
     */
    private String getBaseClassMapKey(Long baseClassId) {
        return BASE_CLASS_MAP + baseClassId;
    }

    /**
     * 构造 基类 访问key
     *
     * @param baseClassId 基类id
     * @return 基类 访问key
     */
    private String getBaseClassMapKey(String baseClassId) {
        return BASE_CLASS_MAP + baseClassId;
    }

}
