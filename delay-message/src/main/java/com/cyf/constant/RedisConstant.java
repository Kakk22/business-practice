package com.cyf.constant;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
public interface RedisConstant {

    /**
     * 延时任务redis key
     */
    String DELAY_MESSAGE_KEY = "delay_message";

    /**
     * redis 任务队列集合
     */
    String LIST_MESSAGE_KEY  = "list_message";
}
