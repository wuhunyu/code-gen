package com.wuhunyu.code_gen.system.environment.repository;

import com.wuhunyu.code_gen.system.environment.domain.UserEnvironment;

import java.util.List;

/**
 * 环境 数据访问
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 19:58
 */

public interface UserEnvironmentRepository {

    /**
     * 根据用户id查询该用户下的所有环境信息
     *
     * @param userId 用户id
     * @return 环境信息s
     */
    List<UserEnvironment> listUserEnvironmentByUserId(Long userId);

    /**
     * 根据环境id查询环境具体信息
     *
     * @param userEnvironmentId 环境信息
     * @return 具体信息
     */
    UserEnvironment findUserEnvironmentByUserEnvironmentId(Long userEnvironmentId);

    /**
     * 新增一个环境
     *
     * @param userEnvironment 环境信息
     * @param userId          用户id
     */
    void insertUserEnvironment(UserEnvironment userEnvironment, Long userId);

    /**
     * 修改一个环境
     *
     * @param userEnvironment 环境信息
     * @param userId          用户id
     */
    void updateUserEnvironment(UserEnvironment userEnvironment, Long userId);

    /**
     * 删除一个环境
     *
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     */
    void deleteUserEnvironmentByUserEnvironmentId(Long userEnvironmentId, Long userId);

    /**
     * 判断一个指定用户下是否存在指定的环境
     *
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     * @return 是否存在(true : 存在 ; false : 不存在)
     */
    boolean existsUserEnvironmentByUserEnvironmentIdAndUserId(Long userEnvironmentId, Long userId);

    /**
     * 重新排序环境
     *
     * @param userEnvironmentIds 环境id集合
     * @param userId             用户id
     */
    void sortUserEnvironments(List<Long> userEnvironmentIds, Long userId);


    /**
     * 统计指定用户下的环境个数
     *
     * @param userId 用户id
     * @return 环境个数
     */
    Long countUserEnvironmentNumByUserId(Long userId);

}
