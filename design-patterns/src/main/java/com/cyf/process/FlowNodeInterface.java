package com.cyf.process;

/**
 * @author 陈一锋
 * @date 2021/5/1.
 */
public interface FlowNodeInterface<T> {

    /**
     * Node的执行方法
     *
     * @param nodeData 数据
     * @param context  上下文
     * @return /
     */
    T invokeNode(FlowEngine.RunData nodeData, Context context);

    /**
     * node执行后的方法
     *
     * @param nodeData 数据
     * @param context  上下文
     */
    void afterInvoke(FlowEngine.RunData nodeData, Context context);

    /**
     * 从context中获取node结果的key
     *
     * @return key
     */
    String resultKey();
}

