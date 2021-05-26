package com.cyf.preventrepeat.enums;

/**
 * 重复提交限制级别
 * @author 陈一锋
 * @date 2021/5/26.
 */
public enum RepeatLimitTimeLevel {

    /**
     * 级别一:50ms
     */
    ONE(50),
    /**
     * 级别二:100ms
     */
    TWO(100),
    /**
     * 200ms
     */
    THREE(200),
    /**
     * 400ms
     */
    FORE(400),
    /**
     * 600ms
     */
    FIVE(600),
    /**
     * 1000ms
     */
    SIX(1000),
    /**
     * 2s
     */
    SEVEN(2000),
    /**
     * 4s
     */
    EIGHT(4000),
    /**
     * 8s
     */
    NICE(8000),
    /**
     * 10s
     */
    TEN(10000);

    private final int millisecond;

    RepeatLimitTimeLevel(int millisecond) {
        this.millisecond = millisecond;
    }

    public int getMillisecond() {
        return millisecond;
    }
}
