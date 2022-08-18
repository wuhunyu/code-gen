package com.wuhunyu.code_gen.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.wuhunyu.code_gen.auth.AuthContextHolder;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.system.environment.domain.dto.UserEnvironmentDto;
import com.wuhunyu.code_gen.system.environment.domain.vo.UserEnvironmentVo;
import com.wuhunyu.code_gen.system.environment.service.UserEnvironmentService;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 环境管理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/17 20:51
 */

@RestController
@RequestMapping("/userEnvironment")
@RequiredArgsConstructor
@Slf4j
public class UserEnvironmentController {

    private final UserEnvironmentService userEnvironmentService;

    /**
     * 查询当前用户的所有 环境配置
     *
     * @return 环境配置
     */
    @GetMapping
    public List<SelectData> listUserEnvironmentsByUserId() {
        return userEnvironmentService.listUserEnvironmentsByUserId(AuthContextHolder.getUserId());
    }

    /**
     * 查询环境配置信息(修改使用)
     *
     * @param userEnvironmentId 环境配置id
     * @return 环境配置信息
     */
    @GetMapping("/{userEnvironmentId}")
    public UserEnvironmentDto findUserEnvironmentDtoByUserEnvironmentId(
            @PathVariable("userEnvironmentId") Long userEnvironmentId) {
        return userEnvironmentService
                .findUserEnvironmentDtoByUserEnvironmentId(userEnvironmentId, AuthContextHolder.getUserId());
    }

    /**
     * 查询环境配置信息(详情使用)
     *
     * @param userEnvironmentId 环境配置id
     * @return 环境配置信息
     */
    @GetMapping("/info/{userEnvironmentId}")
    public UserEnvironmentVo findUserEnvironmentVoByUserEnvironmentId(
            @PathVariable("userEnvironmentId") Long userEnvironmentId) {
        return userEnvironmentService
                .findUserEnvironmentVoByUserEnvironmentId(userEnvironmentId, AuthContextHolder.getUserId());
    }

    /**
     * 为当前用户新增一个环境
     *
     * @param userEnvironmentDto 环境配置
     */
    @PostMapping
    public void insertUserEnvironment(@RequestBody @Validated(OperationTypeGroup.Insert.class)
                                              UserEnvironmentDto userEnvironmentDto) {
        userEnvironmentService.insertUserEnvironment(userEnvironmentDto, AuthContextHolder.getUserId());
    }

    /**
     * 为当前用户修改一个环境
     *
     * @param userEnvironmentDto 环境配置
     */
    @PutMapping
    public void updateUserEnvironment(@RequestBody @Validated(OperationTypeGroup.Update.class)
                                              UserEnvironmentDto userEnvironmentDto) {
        userEnvironmentService.updateUserEnvironment(userEnvironmentDto, AuthContextHolder.getUserId());
    }

    /**
     * 删除当前用户的一个环境配置
     *
     * @param userEnvironmentId 环境配置id
     */
    @DeleteMapping("/{userEnvironmentId}")
    public void deleteUserEnvironment(@PathVariable("userEnvironmentId") Long userEnvironmentId) {
        userEnvironmentService.deleteUserEnvironment(userEnvironmentId, AuthContextHolder.getUserId());
    }

    /**
     * 重新排序环境
     *
     * @param userEnvironmentIds 环境配置id集合
     */
    @PostMapping("/sort")
    public void sortUserEnvironments(@RequestBody List<Long> userEnvironmentIds) {
        Assert.isTrue(CollUtil.isEmpty(userEnvironmentIds), "环境配置id不能为空");
        userEnvironmentService.sortUserEnvironments(userEnvironmentIds, AuthContextHolder.getUserId());
    }

}
