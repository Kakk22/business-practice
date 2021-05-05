package com.cyf.contorller;

import com.cyf.annotaion.RequireLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈一锋
 * @date 2021/5/5.
 */
@RestController
@RequestMapping("/test")
//@RequireLogin
public class TestController {


    @RequestMapping("/t1")
    @RequireLogin()
    public void t1() {
        System.out.println("需要登录的接口t1,请求成功");
    }

    @RequestMapping("/t2")
    public void t2() {
        System.out.println("不需要登录的接口t2,请求成功");
    }
}
