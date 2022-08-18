package com.wuhunyu.code_gen.system.environment.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.environment.domain.UserEnvironment;
import com.wuhunyu.code_gen.system.environment.domain.dto.UserEnvironmentDto;
import com.wuhunyu.code_gen.system.environment.model.CodeGenConfigModel;
import com.wuhunyu.code_gen.system.environment.repository.UserEnvironmentRepository;
import com.wuhunyu.code_gen.system.environment.service.UserEnvironmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 环境 处理实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/17 20:30
 */

@Service("userEnvironmentService")
@RequiredArgsConstructor
@Primary
@Slf4j
public class UserEnvironmentServiceImpl implements UserEnvironmentService {

    private final CodeGenConfigModel codeGenConfigModel;

    private final UserEnvironmentRepository userEnvironmentRepository;

    @Override
    public void initUserEnvironment(Long userId) {
        // 构建默认的 环境信息
        UserEnvironment userEnvironment = new UserEnvironment(
                SequenceInstance.INSTANCE.nextId(),
                codeGenConfigModel.getConfigName(),
                codeGenConfigModel.getTablePrefix(),
                codeGenConfigModel.getProjectPackage(),
                codeGenConfigModel.getModuleName(),
                codeGenConfigModel.getSubModuleName(),
                codeGenConfigModel.getAuthor(),
                codeGenConfigModel.getVersion());

        // 新增 环境信息
        userEnvironmentRepository.insertUserEnvironment(userEnvironment, userId);
    }

    @Override
    public List<SelectData> listUserEnvironmentsByUserId(Long userId) {
        // 查询指定用户下的全部环境信息
        List<UserEnvironment> userEnvironments = userEnvironmentRepository.listUserEnvironmentByUserId(userId);
        if (CollUtil.isEmpty(userEnvironments)) {
            return Collections.emptyList();
        }
        // 筛选出简略信息
        return userEnvironments.stream()
                .map(userEnvironment ->
                        new SelectData(userEnvironment.getConfigName(),
                                userEnvironment.getUserEnvironmentId().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public UserEnvironmentDto findUserEnvironmentDtoByUserEnvironmentId(Long userEnvironmentId, Long userId) {
        UserEnvironment userEnvironment =
                userEnvironmentRepository.findUserEnvironmentByUserEnvironmentId(userEnvironmentId);
        if (userEnvironment == null) {
            return null;
        }
        return new UserEnvironmentDto(
                userEnvironment.getUserEnvironmentId(),
                userEnvironment.getConfigName(),
                userEnvironment.getTablePrefix(),
                userEnvironment.getProjectPackage(),
                userEnvironment.getModuleName(),
                userEnvironment.getSubModuleName(),
                userEnvironment.getAuthor(),
                userEnvironment.getVersion());
    }

    @Override
    public void insertUserEnvironment(UserEnvironmentDto userEnvironmentDto, Long userId) {
        log.info("环境: {}", userEnvironmentDto);

        // 构建 环境对象
        UserEnvironment userEnvironment = new UserEnvironment(
                SequenceInstance.INSTANCE.nextId(),
                userEnvironmentDto.getConfigName(),
                StringUtils.isBlank(userEnvironmentDto.getTablePrefix()) ?
                        CommonConstant.BLANK_STR : userEnvironmentDto.getTablePrefix(),
                userEnvironmentDto.getProjectPackage(),
                userEnvironmentDto.getModuleName(),
                userEnvironmentDto.getSubModuleName(),
                userEnvironmentDto.getAuthor(),
                userEnvironmentDto.getVersion());
        // 执行新增
        userEnvironmentRepository.insertUserEnvironment(userEnvironment, userId);
    }

    @Override
    public void updateUserEnvironment(UserEnvironmentDto userEnvironmentDto, Long userId) {
        log.info("环境: {}", userEnvironmentDto);

        // 查询环境是否已存在
        boolean exists = userEnvironmentRepository
                .existsUserEnvironmentByUserEnvironmentIdAndUserId(
                        userEnvironmentDto.getUserEnvironmentId(), userId);
        Assert.isTrue(!exists, "环境不存在");

        // 构建 环境对象
        UserEnvironment userEnvironment = new UserEnvironment(
                userEnvironmentDto.getUserEnvironmentId(),
                userEnvironmentDto.getConfigName(),
                StringUtils.isBlank(userEnvironmentDto.getTablePrefix()) ?
                        CommonConstant.BLANK_STR : userEnvironmentDto.getTablePrefix(),
                userEnvironmentDto.getProjectPackage(),
                userEnvironmentDto.getModuleName(),
                userEnvironmentDto.getSubModuleName(),
                userEnvironmentDto.getAuthor(),
                userEnvironmentDto.getVersion());
        // 执行修改
        userEnvironmentRepository.updateUserEnvironment(userEnvironment, userId);
    }

    @Override
    public void deleteUserEnvironment(Long userEnvironmentId, Long userId) {
        // 查询是否存在
        boolean exists = userEnvironmentRepository
                .existsUserEnvironmentByUserEnvironmentIdAndUserId(userEnvironmentId, userId);

        // 查询当前的环境个数
        Long size = userEnvironmentRepository.countUserEnvironmentNumByUserId(userId);
        Assert.isTrue(size != null && size <= 1, "至少需要保留一个环境配置");

        // 执行删除
        if (exists) {
            userEnvironmentRepository.deleteUserEnvironmentByUserEnvironmentId(userEnvironmentId, userId);
        }
    }

    @Override
    public void sortUserEnvironments(List<Long> userEnvironmentIds, Long userId) {
        // 确认环境是否全部存在
        List<UserEnvironment> userEnvironments = userEnvironmentRepository.listUserEnvironmentByUserId(userId);
        if (CollUtil.isEmpty(userEnvironments)) {
            return;
        }

        // 收集全部id集合
        Set<Long> userEnvironmentSet = userEnvironments.stream()
                .map(UserEnvironment::getUserEnvironmentId)
                .collect(Collectors.toSet());
        Assert.isTrue(userEnvironmentSet.size() != userEnvironmentIds.size(), "环境信息异常");
        for (Long userEnvironmentId : userEnvironmentIds) {
            if (!userEnvironmentSet.contains(userEnvironmentId)) {
                Assert.isTrue("环境信息异常");
            }
        }

        // 重新排序
        userEnvironmentRepository.sortUserEnvironments(userEnvironmentIds, userId);
    }
}
