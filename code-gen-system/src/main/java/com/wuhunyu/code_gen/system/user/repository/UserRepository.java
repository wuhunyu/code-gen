package com.wuhunyu.code_gen.system.user.repository;

import com.wuhunyu.code_gen.system.user.domain.User;

/**
 * 用户信息 数据访问
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 17:09
 */

public interface UserRepository {

    /**
     * 根据userId查询用户信息
     *
     * @param userId userId
     * @return 用户信息
     */
    User findUserByUserId(Long userId);

    /**
     * 根据 登录用户名称 查询用户信息
     *
     * @param loginName 登录用户名称
     * @return 用户信息
     */
    User findUserByLoginName(String loginName);

    /**
     * 新增
     *
     * @param user 用户信息
     */
    void insertUser(User user);

    /**
     * 修改
     *
     * @param user 用户信息
     */
    void updateUser(User user);

    /**
     * 删除
     *
     * @param userId 用户id
     */
    void deleteUserByUserId(Long userId);

}
