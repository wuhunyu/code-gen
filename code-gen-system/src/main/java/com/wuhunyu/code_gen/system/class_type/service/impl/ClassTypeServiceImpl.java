package com.wuhunyu.code_gen.system.class_type.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.system.class_type.domain.ClassType;
import com.wuhunyu.code_gen.system.class_type.domain.ClassTypeVo;
import com.wuhunyu.code_gen.system.class_type.domain.dto.ClassTypeDto;
import com.wuhunyu.code_gen.system.class_type.model.ClassTypeModel;
import com.wuhunyu.code_gen.system.class_type.repository.ClassTypeRepository;
import com.wuhunyu.code_gen.system.class_type.service.ClassTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类型映射 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 20:21
 */

@Service("classTypeService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class ClassTypeServiceImpl implements ClassTypeService {

    private final ClassTypeRepository classTypeRepository;

    private final ClassTypeModel classTypeModel;

    @Override
    public void initClassType(Long userEnvironmentId) {
        // 构建 类型映射关系
        List<ClassTypeModel.ClassTypeInnerModel> defaultList = classTypeModel.getDefaultList();
        if (CollUtil.isEmpty(defaultList)) {
            return;
        }
        List<ClassType> classTypes = defaultList.stream()
                .map(item -> new ClassType(
                        SequenceInstance.INSTANCE.nextId(),
                        item.getJdbcType(),
                        item.getJavaTypePackage(),
                        item.getJavaTypeName()))
                .collect(Collectors.toList());
        // 新增 类型映射关系
        classTypeRepository.insertClassTypes(classTypes, userEnvironmentId);
    }

    @Override
    public List<ClassTypeVo> listClassTypeVosByUserEnvironmentId(Long userEnvironmentId, Long userId) {
        List<ClassType> classTypes = classTypeRepository.listClassTypes(userEnvironmentId, userId);
        if (CollUtil.isEmpty(classTypes)) {
            return Collections.emptyList();
        }

        return classTypes.stream()
                .map(classType -> new ClassTypeVo(
                        classType.getClassTypeId(),
                        classType.getJdbcType(),
                        classType.getJavaTypePackage(),
                        classType.getJavaTypeName()))
                .collect(Collectors.toList());
    }

    @Override
    public void refreshClassTypeDto(ClassTypeDto classTypeDto, Long userId) {
        // 类型转换
        List<ClassType> classTypes = classTypeDto.getClassTypeInfoDtos()
                .stream()
                .map(classTypeInfoDto -> new ClassType(
                        classTypeInfoDto.getClassTypeId() == null ?
                                SequenceInstance.INSTANCE.nextId() :
                                classTypeInfoDto.getClassTypeId(),
                        classTypeInfoDto.getJdbcType(),
                        classTypeInfoDto.getJavaTypePackage(),
                        classTypeInfoDto.getJavaTypeName()))
                .collect(Collectors.toList());

        // 更新数据
        classTypeRepository.insertOrUpdateClassType(classTypes, classTypeDto.getUserEnvironmentId(), userId);
    }
}
