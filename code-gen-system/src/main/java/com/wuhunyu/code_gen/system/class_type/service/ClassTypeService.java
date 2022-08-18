package com.wuhunyu.code_gen.system.class_type.service;

import com.wuhunyu.code_gen.system.class_type.domain.ClassTypeVo;
import com.wuhunyu.code_gen.system.class_type.domain.dto.ClassTypeDto;

import java.util.List;

/**
 * 类型映射 处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 20:13
 */

public interface ClassTypeService {

    /**
     * 初始化类型映射关系
     *
     * @param userEnvironmentId 环境id
     */
    void initClassType(Long userEnvironmentId);

    /**
     * 查询指定环境下的类型映射关系
     *
     * @param userEnvironmentId 环境id
     * @param userId            用户id
     * @return 类型映射关系
     */
    List<ClassTypeVo> listClassTypeVosByUserEnvironmentId(Long userEnvironmentId, Long userId);

    /**
     * 更新类型映射关系
     *
     * @param classTypeDto 类型映射dto
     * @param userId       用户id
     */
    void refreshClassTypeDto(ClassTypeDto classTypeDto, Long userId);

}
