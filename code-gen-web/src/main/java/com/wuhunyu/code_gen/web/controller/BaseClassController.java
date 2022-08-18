package com.wuhunyu.code_gen.web.controller;

import com.wuhunyu.code_gen.auth.AuthContextHolder;
import com.wuhunyu.code_gen.common.domain.SelectData;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassDto;
import com.wuhunyu.code_gen.system.base_class.domain.dto.BaseClassVo;
import com.wuhunyu.code_gen.system.base_class.service.BaseClassService;
import com.wuhunyu.code_gen.system.operation_type.OperationTypeGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基类管理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 14:01
 */

@RestController
@RequestMapping("/baseClass")
@RequiredArgsConstructor
@Slf4j
public class BaseClassController {

    private final BaseClassService baseClassService;

    /**
     * 查询当前用户的全部基类配置
     *
     * @return 基类配置
     */
    @GetMapping
    public List<SelectData> listBaseClasses() {
        return baseClassService.listBaseClasses(AuthContextHolder.getUserId());
    }

    /**
     * 查询指定的基类配置
     *
     * @param baseClassId 基类id
     * @return 基类配置
     */
    @GetMapping("/{baseClassId}")
    public BaseClassVo findBaseClassVoByBaseClassIdAndUserId(@PathVariable("baseClassId") Long baseClassId) {
        return baseClassService.findBaseClassVoByBaseClassIdAndUserId(baseClassId, AuthContextHolder.getUserId());
    }

    /**
     * 新增一个基类配置
     *
     * @param baseClassDto 基类配置
     */
    @PostMapping
    public void insertBaseClass(@RequestBody @Validated(OperationTypeGroup.Insert.class) BaseClassDto baseClassDto) {
        baseClassService.insertBaseClass(baseClassDto, AuthContextHolder.getUserId());
    }

    /**
     * 修改一个基类配置
     *
     * @param baseClassDto 基类配置
     */
    @PutMapping
    public void updateBaseClass(@RequestBody @Validated(OperationTypeGroup.Update.class) BaseClassDto baseClassDto) {
        baseClassService.updateBaseClass(baseClassDto, AuthContextHolder.getUserId());
    }

    /**
     * 删除一个基类配置
     *
     * @param baseClassId 基类id
     */
    @DeleteMapping("/{baseClassId}")
    public void deleteBaseClassByBaseClassIdAndUserId(@PathVariable("baseClassId") Long baseClassId) {
        baseClassService.deleteBaseClassByBaseClassIdAndUserId(baseClassId, AuthContextHolder.getUserId());
    }

}
