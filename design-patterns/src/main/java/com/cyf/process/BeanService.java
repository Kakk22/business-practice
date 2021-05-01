package com.cyf.process;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author 陈一锋
 * @date 2021/5/1.
 */
@Service
public class BeanService implements ApplicationContextAware {

    protected static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanService.applicationContext = applicationContext;
    }

    public static Object getBeanByName(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 根据class类型获取bean
     *
     * @throws Exception 当有继承或接口有多个实现类时,通过比较name获取唯一的那个bean
     */
    @SuppressWarnings("all")
    public static <T> T getSingleBeanByType(Class<T> clazz) throws Exception {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        return applicationContext.getBean(clazz);
    }

}
