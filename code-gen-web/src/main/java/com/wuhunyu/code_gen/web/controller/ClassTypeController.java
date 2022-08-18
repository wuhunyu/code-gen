package com.wuhunyu.code_gen.web.controller;

import com.wuhunyu.code_gen.auth.AuthContextHolder;
import com.wuhunyu.code_gen.system.class_type.domain.ClassTypeVo;
import com.wuhunyu.code_gen.system.class_type.domain.dto.ClassTypeDto;
import com.wuhunyu.code_gen.system.class_type.service.ClassTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类型映射
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 20:28
 */

@RestController
@RequestMapping("/classType")
@RequiredArgsConstructor
@Slf4j
public class ClassTypeController {

    private final ClassTypeService classTypeService;

    /**
     * 查询指定环境下的类型映射关系
     *
     * @param userEnvironmentId 环境id
     * @return 类型映射关系
     */
    @GetMapping("/{userEnvironmentId}")
    public List<ClassTypeVo> listClassTypeByUserEnvironmentId(@PathVariable("userEnvironmentId") Long userEnvironmentId) {
        return classTypeService.listClassTypeVosByUserEnvironmentId(userEnvironmentId, AuthContextHolder.getUserId());
    }

    /**
     * 更新类型映射关系
     */
    @PostMapping
    public void refreshClassType(@RequestBody ClassTypeDto classTypeDto) {
        classTypeService.refreshClassTypeDto(classTypeDto, AuthContextHolder.getUserId());
    }

}
