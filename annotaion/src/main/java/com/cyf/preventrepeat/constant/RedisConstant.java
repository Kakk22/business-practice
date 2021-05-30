package com.cyf.preventrepeat.constant;

/**
 * @author 陈一锋
 * @date 2021/5/26.
 */
public interface RedisConstant {

    /**
     * 防止重复提交key 该key+方法名字+参数MD5加密
     */
    String REPEAT_SUBMIT_KEY = "REPEAT_SUBMIT_PARAMS";

    /**
     * 获取重复提交key
     *
     * @param methodName 方法名
     * @param paramsMd5  方法值md5加密
     * @return key
     */
    static String getRepeatKey(String methodName, String paramsMd5) {
        return REPEAT_SUBMIT_KEY + ":" + methodName + ":" + paramsMd5;
    }
}
