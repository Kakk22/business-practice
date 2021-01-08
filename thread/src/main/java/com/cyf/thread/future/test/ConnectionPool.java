package com.cyf.thread.future.test;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 陈一锋
 * @date 2021/1/8.
 */
public class ConnectionPool {
    public static final Map<String, FutureTask<Connection>> pool = new ConcurrentHashMap<>();

    public Connection getConnect(String key) throws ExecutionException, InterruptedException {
        FutureTask<Connection> connectionFutureTask = pool.get(key);
        if (connectionFutureTask != null) {
            return connectionFutureTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return createConnection();
                }
            };
            FutureTask<Connection> newTask = new FutureTask<>(callable);
            connectionFutureTask = pool.putIfAbsent(key, newTask);
            // 这个案例会出现多个连接池同用一个数据库连接
            // 但是自始至终自会创建一个连接
            // 改造可以实用sync锁 这里只是使用Future的一个小案例
            if (connectionFutureTask == null) {
                connectionFutureTask = newTask;
                connectionFutureTask.run();
            }
            return connectionFutureTask.get();
        }
    }


    public Connection createConnection() {
        return new Connection();
    }

    class Connection {
    }
}