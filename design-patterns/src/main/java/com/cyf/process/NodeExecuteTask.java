package com.cyf.process;

import java.util.concurrent.Callable;

/**
 * @author 陈一锋
 * @date 2021/5/1.
 */
public class NodeExecuteTask implements Callable {

    private FlowNodeInterface flowNodeInterface;
    private FlowEngine.RunData runData;
    private Context context;

    public NodeExecuteTask(FlowNodeInterface flowNodeInterface, FlowEngine.RunData runData, Context context) {
        this.flowNodeInterface = flowNodeInterface;
        this.runData = runData;
        this.context = context;
    }

    public Object execute() {
        try {
            Object o = flowNodeInterface.invokeNode(runData, context);
            flowNodeInterface.afterInvoke(runData, context);
            return o;
        } catch (Throwable ex) {
            throw ex;
        }
    }

    @Override
    public Object call() throws Exception {
        return execute();
    }
}
