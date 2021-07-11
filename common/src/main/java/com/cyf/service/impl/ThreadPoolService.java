package com.cyf.service.impl;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author by cyf
 * @date 2020/10/26.
 */
@Service
public class ThreadPoolService {

    private static ThreadFactory nameThreadFactory = new ThreadFactoryBuilder()
            .setNamePrefix("threadLocal-pool-%d")
            .build();

    /**
     * corePoolSize    线程池核心池的大小
     * maximumPoolSize 线程池中允许的最大线程数量
     * keepAliveTime   当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     * unit            keepAliveTime 的时间单位
     * workQueue       用来储存等待执行任务的队列
     * threadFactory   创建线程的工厂类
     * handler         拒绝策略类,当线程池数量达到上线并且workQueue队列长度达到上限时就需要对到来的任务做拒绝处理
     */
    private static ExecutorService service = new ThreadPoolExecutor(
            4,
            40,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            nameThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 获取线程池
     *
     * @return /
     */
    public static ExecutorService getService() {
        return service;
    }

    /**
     * 使用线程池创建并异步
     *
     * @param callable /
     */
    public static <V> Future<V> newTask(Callable<V> callable) {
        return service.submit(callable);
    }
}
