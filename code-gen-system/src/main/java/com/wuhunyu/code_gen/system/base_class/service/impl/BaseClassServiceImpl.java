package com.wuhunyu.code_gen.system.base_class.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.base_class.constants.BaseClassConstant;
import com.wuhunyu.code_gen.system.base_class.domain.BaseClass;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassDto;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassVo;
import com.wuhunyu.code_gen.system.base_class.repository.BaseClassRepository;
import com.wuhunyu.code_gen.system.base_class.service.BaseClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基类 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 13:44
 */

@Service("baseClassService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class BaseClassServiceImpl implements BaseClassService {

    private final BaseClassRepository baseClassRepository;

    @Override
    public List<SelectData> listBaseClasses(Long userId) {
        // 查询该用户下的所有基类配置
        List<BaseClass> baseClasses = baseClassRepository.listBaseClasses(userId);
        // 结果容器
        int size = CollUtil.isEmpty(baseClasses) ? 1 : baseClasses.size() + 1;
        List<SelectData> list = new ArrayList<>(size);
        // 新增一个 默认无基类 选项的选择
        list.add(new SelectData(
                BaseClassConstant.NON_BASE_CLASS_LABEL,
                String.valueOf(BaseClassConstant.NON_BASE_CLASS_VALUE)));

        if (CollUtil.isEmpty(baseClasses)) {
            return list;
        }
        for (BaseClass baseClass : baseClasses) {
            list.add(new SelectData(
                    baseClass.getBaseClassName(),
                    baseClass.getBaseClassId().toString()));
        }
        return list;
    }

    @Override
    public BaseClassVo findBaseClassVoByBaseClassIdAndUserId(Long baseClassId, Long userId) {
        // 确认此 基类配置 属于该用户
        boolean existBaseClass = baseClassRepository.existBaseClass(baseClassId, userId);
        Assert.isTrue(!existBaseClass, "基类配置不存在");

        // 查询 基类配置
        BaseClass baseClass = baseClassRepository.findBaseClassByBaseClassIdAndUserId(baseClassId, userId);
        Assert.isTrue(baseClass == null, "基类配置不存在");

        return new BaseClassVo(
                baseClass.getBaseClassId(),
                baseClass.getBaseClassName(),
                baseClass.getPackageName(),
                baseClass.getClassName());
    }

    @Override
    public void insertBaseClass(BaseClassDto baseClassDto, Long userId) {
        // 直接新增
        BaseClass baseClass = new BaseClass(
                SequenceInstance.INSTANCE.nextId(),
                baseClassDto.getBaseClassName(),
                baseClassDto.getPackageName(),
                baseClassDto.getClassName());
        baseClassRepository.insertBaseClass(baseClass, userId);
    }

    @Override
    public void updateBaseClass(BaseClassDto baseClassDto, Long userId) {
        // 查询是否存在指定基类配置
        BaseClass baseClass =
                baseClassRepository.findBaseClassByBaseClassIdAndUserId(baseClassDto.getBaseClassId(), userId);
        Assert.isTrue(baseClass == null, "基类配置不存在");

        // 执行修改
        BaseClass updateBaseClass = new BaseClass(
                baseClassDto.getBaseClassId(),
                baseClassDto.getBaseClassName(),
                baseClassDto.getPackageName(),
                baseClassDto.getClassName());
        baseClassRepository.updateBaseClass(updateBaseClass, userId);
    }

    @Override
    public void deleteBaseClassByBaseClassIdAndUserId(Long baseClassId, Long userId) {
        // 查询是否存在指定基类配置
        BaseClass baseClass = baseClassRepository.findBaseClassByBaseClassIdAndUserId(baseClassId, userId);
        if (baseClass != null) {
            baseClassRepository.deleteBaseClassByBaseClassIdAndUserId(baseClassId, userId);
        }
    }
}
