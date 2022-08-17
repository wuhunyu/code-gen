package com.wuhunyu.code_gen.system.environment.service;

import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.environment.domain.dto.UserEnvironmentDto;

import java.util.List;

/**
 * 环境 处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 19:33
 */

public interface UserEnvironmentService {

    /**
     * 为指定用户初始化一个默认环境
     *
     * @param userId 用户id
     */
    void initUserEnvironment(Long userId);

    /**
     * 查询指定用户的所有 环境配置
     *
     * @param userId 用户id
     * @return 环境配置
     */
    List<SelectData> listUserEnvironmentsByUserId(Long userId);

    /**
     * 查询环境配置信息
     *
     * @param userEnvironmentId 环境配置id
     * @param userId            用户id
     * @return 环境配置Dto
     */
    UserEnvironmentDto findUserEnvironmentDtoByUserEnvironmentId(Long userEnvironmentId, Long userId);

    /**
     * 新增一个环境
     *
     * @param userEnvironmentDto 环境配置
     * @param userId             用户id
     */
    void insertUserEnvironment(UserEnvironmentDto userEnvironmentDto, Long userId);

    /**
     * 修改一个环境
     *
     * @param userEnvironmentDto 环境配置
     * @param userId             用户id
     */
    void updateUserEnvironment(UserEnvironmentDto userEnvironmentDto, Long userId);

    /**
     * 删除一个环境
     *
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     */
    void deleteUserEnvironment(Long userEnvironmentId, Long userId);

    /**
     * 重新排序环境
     *
     * @param userEnvironmentIds 环境id集合
     * @param userId             用户id
     */
    void sortUserEnvironments(List<Long> userEnvironmentIds, Long userId);

}
