package com.wuhunyu.code_gen.web.annotation;

import java.lang.annotation.*;

/**
 * 不封装返回结果
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 9:15
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoPackage {

}
