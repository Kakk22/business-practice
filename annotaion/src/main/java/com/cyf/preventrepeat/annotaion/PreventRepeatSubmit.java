package com.cyf.preventrepeat.annotaion;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交注解
 *
 * @author 陈一锋
 * @date 2021/5/26.
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface PreventRepeatSubmit {

    /**
     * 防重复提交时间
     */
    long time() default 500L;

    /**
     * 时间类型 默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
