package com.cyf.thread.threadLocal;

/**
 * @author 陈一锋
 * @date 2020/12/14.
 */
public class ThreadLocalTest1 {

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            new Thread(() -> {
                task task = new task();
                System.out.println(task.getInteger());
            }).start();
        }
    }


    static class task {
        private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 10);

        public Integer getInteger() {
            Integer result = threadLocal.get();
            threadLocal.remove();
            System.out.println(Thread.currentThread().getName() +"-----"+"value:"+threadLocal.get());
            return result;
        }
    }
}
