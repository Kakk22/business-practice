package com.cyf.preventrepeat.annotaion;

import com.cyf.preventrepeat.enums.RepeatLimitTimeLevel;

import java.lang.annotation.*;

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
     * 防重复提交间隔时间
     */
    RepeatLimitTimeLevel timeLevel() default RepeatLimitTimeLevel.ONE;
}
