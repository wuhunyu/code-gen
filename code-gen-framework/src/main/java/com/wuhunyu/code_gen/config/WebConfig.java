package com.wuhunyu.code_gen.config;

import com.wuhunyu.code_gen.auth.AuthHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求过滤
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 14:14
 */

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 认证
        registry.addInterceptor(new AuthHandlerInterceptor())
                .excludePathPatterns("/user/login")
                .order(1);
    }

}
