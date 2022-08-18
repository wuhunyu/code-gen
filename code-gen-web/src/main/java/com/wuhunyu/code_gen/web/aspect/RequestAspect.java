package com.wuhunyu.code_gen.web.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求拦截器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/18 13:19
 */

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "logging", name = "requestAspect", havingValue = "true")
public class RequestAspect {

    @Pointcut("execution(* com.wuhunyu.code_gen.web.controller.*..*(..))")
    public void pointCut() {
    }

    /**
     * 环绕通知打印请求信息
     *
     * @param point 切入点对象
     * @return 请求返回结果
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 开启日志打印
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        long startTime = System.currentTimeMillis();
        // 结果返回对象
        Object proceed = null;
        try {
            // 获取请求的参数
            this.printRequestInfo(point);
            // 执行方法主体
            proceed = point.proceed(point.getArgs());
        } catch (Throwable e) {
            // 打印异常栈信息
            log.error("请求异常：{}", e.getLocalizedMessage(), e);
            // 抛出异常，不对异常进行处理
            throw e;
        }
        log.info("请求结果：{}", proceed == null ? null : new ObjectMapper().writeValueAsString(proceed));
        log.info("请求接口耗时: {} ms", System.currentTimeMillis() - startTime);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return proceed;
    }

    /**
     * 打印请求信息
     *
     * @param point 切入点对象
     */
    private void printRequestInfo(ProceedingJoinPoint point) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取ServletRequestAttributes对象失败则直接返回
        if (requestAttributes == null) {
            return;
        }
        // 获取请求对象
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("请求地址：{}\t{}", request.getMethod(), request.getRequestURL());

        // 获取切面的方法签名
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取方法的所有请求参数
        String[] parameterNames = methodSignature.getParameterNames();
        // 构建参数结果容器
        Map<String, String> map = new HashMap<>(16);
        // 获取请求参数
        Object[] args = point.getArgs();
        // 组装参数到map集合中
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (args[i] != null) {
                    map.put(parameterNames[i], args[i].toString());
                }
            }
        }
        log.info("请求参数：{}", map);
        log.info("请求方法：{}", point.getSignature().getDeclaringTypeName()
                + "#"
                + methodSignature.getMethod().getName());
    }

}
