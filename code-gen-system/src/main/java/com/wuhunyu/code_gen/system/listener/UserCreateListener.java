package com.wuhunyu.code_gen.system.listener;

import com.wuhunyu.code_gen.system.environment.service.UserEnvironmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户创建事件监听
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 17:08
 */

@Component("userCreateListener")
@RequiredArgsConstructor
@Slf4j
public class UserCreateListener {

    @Data
    @AllArgsConstructor
    public static class UserCreateDto {

        /**
         * userId
         */
        private Long userId;

    }

    private final UserEnvironmentService userEnvironmentService;

    /**
     * 初始化用户信息
     *
     * @param userCreateDto userId
     */
    @Async
    @EventListener(UserCreateDto.class)
    public void initUser(UserCreateDto userCreateDto) {
        // 初始化用户环境配置
        userEnvironmentService.initUserEnvironment(userCreateDto.getUserId());
    }

}
