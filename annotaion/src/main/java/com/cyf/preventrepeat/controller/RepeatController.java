package com.cyf.preventrepeat.controller;

import com.cyf.preventrepeat.annotaion.PreventRepeatSubmit;
import com.cyf.preventrepeat.enums.RepeatLimitTimeLevel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈一锋
 * @date 2021/5/26.
 */
@RequestMapping("/t1")
@RestController
public class RepeatController {

    @PreventRepeatSubmit(timeLevel = RepeatLimitTimeLevel.THREE)
    @RequestMapping("/test1")
    public void test1(String params1, String params2) {
        System.out.println("请求参数1:"+params1);
        System.out.println("请求参数2:"+params2);
    }
}
