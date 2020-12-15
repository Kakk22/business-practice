package com.cyf.thread.threadLocal;

/**
 * @author 陈一锋
 * @date 2020/12/14.
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            task.calc(10);
            if (i == 80) {
                System.gc();
            }
        }

    }

    static class Task {
        ThreadLocal<Integer> value;

        public int calc(int i) {
            value = new ThreadLocal<>();
            value.set(value.get() == null ? 0 : value.get() + i);
            return value.get();
        }
    }
}
