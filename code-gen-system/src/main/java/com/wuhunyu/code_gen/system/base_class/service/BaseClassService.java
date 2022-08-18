package com.wuhunyu.code_gen.system.base_class.service;

import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassDto;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassVo;

import java.util.List;

/**
 * 基类 处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 13:37
 */

public interface BaseClassService {

    /**
     * 查询指定用户下的全部基类配置
     *
     * @param userId 用户id
     * @return 基类配置
     */
    List<SelectData> listBaseClasses(Long userId);

    /**
     * 查询指定的基类配置
     *
     * @param baseClassId 基类id
     * @param userId      用户id
     * @return 基类配置
     */
    BaseClassVo findBaseClassVoByBaseClassIdAndUserId(Long baseClassId, Long userId);

    /**
     * 新增一个基类配置
     *
     * @param baseClassDto 基类id
     * @param userId       用户id
     */
    void insertBaseClass(BaseClassDto baseClassDto, Long userId);

    /**
     * 修改一个基类配置
     *
     * @param baseClassDto 基类id
     * @param userId       用户id
     */
    void updateBaseClass(BaseClassDto baseClassDto, Long userId);

    /**
     * 删除一个基类配置
     *
     * @param baseClassId 基类id
     * @param userId      用户id
     */
    void deleteBaseClassByBaseClassIdAndUserId(Long baseClassId, Long userId);

}
