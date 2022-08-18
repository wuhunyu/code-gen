package com.wuhunyu.code_gen.system.listener;

import com.wuhunyu.code_gen.system.class_type.service.ClassTypeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 环境初始化事件监听
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 20:43
 */

@Component("userEnvironmentInitListener")
@RequiredArgsConstructor
@Slf4j
public class UserEnvironmentInitListener {

    @Data
    @AllArgsConstructor
    public static class UserEnvironmentInitDto {

        /**
         * 环境id
         */
        private Long userEnvironmentId;

    }

    private final ClassTypeService classTypeService;

    @Async
    @EventListener(UserEnvironmentInitDto.class)
    public void initUserEnvironment(UserEnvironmentInitDto userEnvironmentInitDto) {
        // 初始化类型映射配置
        classTypeService.initClassType(userEnvironmentInitDto.getUserEnvironmentId());
    }

}
