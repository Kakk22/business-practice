package com.cyf.login.interceptor;

import com.cyf.login.annotaion.RequireLogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author 陈一锋
 * @date 2021/5/5.
 */
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法上注解
        RequireLogin methodAnnotation = handlerMethod.getMethod().getAnnotation(RequireLogin.class);
        //获取类上注解
        RequireLogin clazzAnnotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequireLogin.class);

        //优先取方法上注解
        RequireLogin annotation = methodAnnotation != null ? methodAnnotation : clazzAnnotation;

        //没有该注解则放行
        if (annotation == null) {
            return true;
        }

        boolean check = annotation.check();
        if (!check) {
            return true;
        }

        //这里需要检查登录
        //根据具体系统的登录验证 如jwt,session等
        //这里假设为token 假设验证
        String header = request.getHeader("token");
        if ("tokenTest".equals(header)) {
            return true;
        }

        System.out.println("登录拦截器:请求需要登录,验证用户信息失败");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("请求未登录");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
