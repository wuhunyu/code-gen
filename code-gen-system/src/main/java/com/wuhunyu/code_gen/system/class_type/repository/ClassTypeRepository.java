package com.wuhunyu.code_gen.system.class_type.repository;

import com.wuhunyu.code_gen.system.class_type.domain.ClassType;

import java.util.List;

/**
 * 类型映射 数据访问
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 18:06
 */

public interface ClassTypeRepository {

    /**
     * 查询指定用户下的所有类型映射关系
     *
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     * @return 类型映射关系
     */
    List<ClassType> listClassTypes(Long userEnvironmentId, Long userId);

    /**
     * 新增类型映射关系
     *
     * @param classTypes        类型映射关系
     * @param userEnvironmentId 环境id
     */
    void insertClassTypes(List<ClassType> classTypes, Long userEnvironmentId);

    /**
     * 新增/修改类型映射关系
     *
     * @param classTypes        类型映射关系
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     */
    void insertOrUpdateClassType(List<ClassType> classTypes, Long userEnvironmentId, Long userId);

}
