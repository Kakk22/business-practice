package com.cyf.service;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
public interface DelayMessageService {

    /**
     * 定时执行扫描任务
     */
    void execute();
}
