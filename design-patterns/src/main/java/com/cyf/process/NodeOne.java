package com.cyf.process;

import org.springframework.stereotype.Service;

/**
 * @author 陈一锋
 * @date 2021/5/2.
 */
@Service
public class NodeOne  implements FlowNodeInterface{

    @Override
    public Object invokeNode(FlowEngine.RunData nodeData, Context context) {
        System.out.println("执行方法"+nodeData.getParamOne());
        return nodeData.getParamOne();
    }

    @Override
    public void afterInvoke(FlowEngine.RunData nodeData, Context context) {

    }

    @Override
    public String resultKey() {
        return "NodeOne";
    }
}
