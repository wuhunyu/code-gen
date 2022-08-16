package com.wuhunyu.code_gen.config;

import com.wuhunyu.code_gen.auth.AuthHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求过滤
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 14:14
 */

@Component("webConfig")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthHandlerInterceptor authHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        // 认证，拦截除 /user/login, /user/register 以外的所有请求
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register")
                .order(1);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*");
    }

}
