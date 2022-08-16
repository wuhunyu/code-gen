package com.wuhunyu.code_gen.web.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.common.response.Result;
import com.wuhunyu.code_gen.common.response.TableResult;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.web.annotation.NoPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回值封装处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 9:06
 */

@RestControllerAdvice(basePackages = {"com.wuhunyu.code_gen.web"})
@Slf4j
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 对所有未被标记 @NoPackage 的方法进行返回结果封装
        return !returnType.hasMethodAnnotation(NoPackage.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 返回值为空时，直接返回
        if (body == null) {
            return Result.ok();
        }

        // 修改响应类型为 application/json
        HttpHeaders headers = response.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 对已经封装好的结果直接返回
        Class<?> parameterType = returnType.getParameterType();
        if (parameterType.isAssignableFrom(String.class)) {
            // String
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(Result.ok(body));
            } catch (JsonProcessingException e) {
                log.error("全局返回结果序列化异常: {}", e.getLocalizedMessage(), e);
                Assert.isTrue(CommonConstant.DEFAULT_EXCEPTION_MSG);
            }
        } else if (parameterType.isAssignableFrom(Result.class) ||
                parameterType.isAssignableFrom(TableResult.class)) {
            // Result || TableResult
            return body;
        }
        // 其他
        return Result.ok(body);
    }
}
