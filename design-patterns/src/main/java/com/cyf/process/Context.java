package com.cyf.process;

import java.util.HashMap;
import java.util.Map;

/**
 * Context上下文
 * 作为调用下游服务返回结果
 *
 * @author 陈一锋
 * @date 2021/5/1.
 */
public class Context {

    /**
     * 结果缓存
     */
    private Map<String, Object> resultMap = new HashMap<>();

    public Map<String, Object> getAdaptorMap() {
        return resultMap;
    }

    public void setAdaptorMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
