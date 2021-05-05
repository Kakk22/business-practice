package com.cyf.annotaion;

import java.lang.annotation.*;

/**
 * @author 陈一锋
 * @date 2021/5/5.
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {

    /**
     * 是否检查登录
     */
    boolean check() default true;
}
