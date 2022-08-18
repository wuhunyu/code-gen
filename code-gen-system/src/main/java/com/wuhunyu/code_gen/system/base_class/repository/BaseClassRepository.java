package com.wuhunyu.code_gen.system.base_class.repository;

import com.wuhunyu.code_gen.system.base_class.domain.BaseClass;

import java.util.List;

/**
 * 基类 数据访问
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 11:33
 */

public interface BaseClassRepository {

    /**
     * 查询指定用户下的所有基类配置
     *
     * @param userId 用户id
     * @return 基类配置
     */
    List<BaseClass> listBaseClasses(Long userId);

    /**
     * 根据基类id和用户id查询基类配置信息
     *
     * @param baseClassId 基类id
     * @param userId      用户id
     * @return 基类配置信息
     */
    BaseClass findBaseClassByBaseClassIdAndUserId(Long baseClassId, Long userId);

    /**
     * 新增一个基类配置
     *
     * @param baseClass 基类配置
     * @param userId    用户id
     */
    void insertBaseClass(BaseClass baseClass, Long userId);

    /**
     * 修改一个基类配置
     *
     * @param baseClass 基类配置
     * @param userId    用户id
     */
    void updateBaseClass(BaseClass baseClass, Long userId);

    /**
     * 删除一个基类配置
     *
     * @param baseClassId 基类配置id
     * @param userId      用户id
     */
    void deleteBaseClassByBaseClassId(Long baseClassId, Long userId);

    /**
     * 查询指定用户下的基类配置数量
     *
     * @param userId 用户id
     * @return 指定用户下的基类配置数量
     */
    Long countBaseClassNumByUserId(Long userId);

}
