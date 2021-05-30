package com.cyf.preventrepeat.aspect;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.cyf.preventrepeat.annotaion.PreventRepeatSubmit;
import com.cyf.preventrepeat.constant.RedisConstant;
import com.cyf.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author 陈一锋
 * @date 2021/5/26.
 */
@Slf4j
@Component
@Aspect
public class RepeatAspect {

    @Resource
    private RedisService redisService;

    @Pointcut("@annotation(com.cyf.preventrepeat.annotaion.PreventRepeatSubmit)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PreventRepeatSubmit preventRepeatSubmit = method.getAnnotation(PreventRepeatSubmit.class);
        Object[] params = proceedingJoinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder();
        for (Object param : params) {
            stringBuilder.append(JSONUtil.toJsonStr(param));
        }
        //将参数进行mds加密
        String paramsMd5 = MD5.create().digestHex16(stringBuilder.toString());
        String repeatKey = RedisConstant.getRepeatKey(method.getName(), paramsMd5);

        Object o = redisService.get(repeatKey);
        if (o != null) {
            //重复提交
            log.error("重复提交,方法名:{}",method.getName());
            return null;
        }

        //redis自增原子性
        Long count = redisService.incr(repeatKey, 1);
        if (count != 1) {
            //说明重复提交 直接拒绝
            log.error("重复提交,方法名:{}",method.getName());
            return null;
        }

        boolean expire = redisService.expire(repeatKey, preventRepeatSubmit.time(), preventRepeatSubmit.timeUnit());
        log.error("设置重复提交key:{},过期时间:{},时间类型:{}", repeatKey, preventRepeatSubmit.time(), preventRepeatSubmit.timeUnit());

        Object proceed;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
            if (expire) {
                log.error("防重复提交方法出现异常,删除redis中key:{}", repeatKey);
                redisService.del(repeatKey);
            }
            throw throwable;
        }

        return proceed;
    }
}
