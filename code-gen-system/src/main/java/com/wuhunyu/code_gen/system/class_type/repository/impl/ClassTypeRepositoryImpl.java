package com.wuhunyu.code_gen.system.class_type.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.system.class_type.domain.ClassType;
import com.wuhunyu.code_gen.system.class_type.repository.ClassTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wuhunyu.code_gen.system.constants.RedisKeyConstant.ClassTypeKeys.*;
import static com.wuhunyu.code_gen.common.utils.RedisUtil.*;

/**
 * 类型映射 数据访问实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 19:28
 */

@Repository("classTypeRepository")
@Primary
@Slf4j
public class ClassTypeRepositoryImpl implements ClassTypeRepository {

    @Override
    public List<ClassType> listClassTypes(Long userEnvironmentId, Long userId) {
        // 查询出所有的映射id
        Set<String> classTypeIds = zRange(this.findClassTypeSetKey(userEnvironmentId));
        if (CollUtil.isEmpty(classTypeIds)) {
            return Collections.emptyList();
        }

        // 构建结果容器
        List<ClassType> list = new ArrayList<>(classTypeIds.size());
        for (String classTypeId : classTypeIds) {
            list.add(hGetAll(this.findClassTypeMapKey(classTypeId), ClassType.class));
        }
        return list;
    }

    /**
     * 新增类型映射
     *
     * @param insertList        新增列表
     * @param userEnvironmentId 环境id
     */
    @Override
    public void insertClassTypes(List<ClassType> insertList, Long userEnvironmentId) {
        if (CollUtil.isEmpty(insertList)) {
            return;
        }
        for (ClassType classType : insertList) {
            // 新增 映射信息
            hSet(this.findClassTypeMapKey(classType.getClassTypeId()), classType);
            // 新增 类型映射&环境
            zAdd(this.findClassTypeSetKey(userEnvironmentId), classType.getClassTypeId().toString());
        }
    }

    @Override
    public void insertOrUpdateClassType(List<ClassType> targetClassTypes, Long userEnvironmentId, Long userId) {
        // 查询原始的类型映射关系
        List<ClassType> sourceClassType = this.listClassTypes(userEnvironmentId, userId);

        Set<Long> sourceClassTypeIds = sourceClassType.stream()
                .map(ClassType::getClassTypeId)
                .collect(Collectors.toSet());
        Set<Long> targetClassTypeIds = targetClassTypes.stream()
                .map(ClassType::getClassTypeId)
                .collect(Collectors.toSet());

        // 新增/修改列表
        List<ClassType> insertList = new ArrayList<>();
        List<ClassType> updateList = new ArrayList<>();
        for (ClassType targetClassType : targetClassTypes) {
            if (sourceClassTypeIds.contains(targetClassType.getClassTypeId())) {
                updateList.add(targetClassType);
            } else {
                insertList.add(targetClassType);
            }
        }
        // 删除列表
        List<Long> deleteList = new ArrayList<>();
        for (Long sourceClassTypeId : sourceClassTypeIds) {
            if (!targetClassTypeIds.contains(sourceClassTypeId)) {
                deleteList.add(sourceClassTypeId);
            }
        }

        // 新增
        this.insertClassTypes(insertList, userEnvironmentId);
        // 修改
        this.updateClassTypes(updateList, userEnvironmentId);
        // 删除
        this.deleteClassTypes(deleteList, userEnvironmentId);
    }

    /**
     * 修改类型映射
     *
     * @param updateList        修改列表
     * @param userEnvironmentId 环境id
     */
    private void updateClassTypes(List<ClassType> updateList, Long userEnvironmentId) {
        if (CollUtil.isEmpty(updateList)) {
            return;
        }
        for (ClassType classType : updateList) {
            // 修改映射信息
            hSet(this.findClassTypeMapKey(classType.getClassTypeId()), classType);
        }
    }

    /**
     * 删除类型映射
     *
     * @param deleteList        删除列表
     * @param userEnvironmentId 环境id
     */
    private void deleteClassTypes(List<Long> deleteList, Long userEnvironmentId) {
        if (CollUtil.isEmpty(deleteList)) {
            return;
        }
        for (Long classTypeId : deleteList) {
            // 删除 类型映射&环境
            zRemove(this.findClassTypeSetKey(userEnvironmentId), classTypeId.toString());
            // 删除 类型信息
            delete(this.findClassTypeMapKey(classTypeId));
        }
    }

    /**
     * 构造 类型映射&环境 关系key
     *
     * @param userEnvironmentId 环境id
     * @return 类型映射&环境 关系key
     */
    private String findClassTypeSetKey(Long userEnvironmentId) {
        return CLASS_TYPE_SET + userEnvironmentId;
    }

    /**
     * 构造 类型映射key
     *
     * @param classTypeId 类型映射id
     * @return 类型映射key
     */
    private String findClassTypeMapKey(Long classTypeId) {
        return CLASS_TYPE_MAP + classTypeId;
    }

    /**
     * 构造 类型映射key
     *
     * @param classTypeId 类型映射id
     * @return 类型映射key
     */
    private String findClassTypeMapKey(String classTypeId) {
        return CLASS_TYPE_MAP + classTypeId;
    }

}
