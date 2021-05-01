package com.cyf.process;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 引擎类
 *
 * @author 陈一锋
 * @date 2021/5/1.
 */
@Service
public class FlowEngine {

    private static ThreadPoolExecutor threadPoolExecutor = new
            ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500),
            new NamedThreadFactory("engine process", false), new ThreadPoolExecutor.AbortPolicy() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    e.toString());
        }
    });

    /**
     * 引擎执行入口
     */
    public void execute(FlowNode flowNode, RunData runData, Context context) throws Exception {

        Map<String, List<String>> nodeGroup = groupByGroupName(flowNode);

        Map<String, FlowNode.NodeConf> nodeMap = flowNode.getNodeMap();

        for (String groupName : nodeGroup.keySet()) {
            boolean needThreadExp = false;
            List<String> nodeNameList = nodeGroup.get(groupName);

            if (nodeNameList.size() == 1) {
                //只有一个节点,串行执行
                String nodeName = nodeNameList.get(0);
                FlowNodeInterface detailNode = (FlowNodeInterface) BeanService.getSingleBeanByType(Class.forName(nodeName));
                NodeExecuteTask nodeExecuteTask = new NodeExecuteTask(detailNode, runData, context);

                try {
                    Object result = nodeExecuteTask.execute();
                    //缓存结果
                    context.getAdaptorMap().put(detailNode.resultKey(), result);
                } catch (Exception e) {
                    needThreadExp = true;
                }
            } else {
                //多个节点,并行执行
                List<Future> resultList = new ArrayList<>();
                List<String> executeNodeNameList = new ArrayList<>();
                List<NodeExecuteTask> executeTaskList = new ArrayList<>();

                for (String nodeName : nodeNameList) {
                    FlowNodeInterface detailNode = (FlowNodeInterface) BeanService.getSingleBeanByType(Class.forName(nodeName));
                    NodeExecuteTask nodeExecuteTask = new NodeExecuteTask(detailNode, runData, context);

                    executeTaskList.add(nodeExecuteTask);
                    executeNodeNameList.add(nodeName);
                    resultList.add(threadPoolExecutor.submit(nodeExecuteTask));
                }

                for (int i = 0; i < resultList.size(); i++) {
                    String nodeName = executeNodeNameList.get(i);
                    String nodeKey = groupName + "_" + nodeName;
                    FlowNodeInterface detailNode = (FlowNodeInterface) BeanService.getSingleBeanByType(Class.forName(nodeName));
                    FlowNode.NodeConf nodeConf = nodeMap.get(nodeKey);
                    int timeout = nodeConf.getTimeout();
                    Future future = resultList.get(i);

                    try {
                        Object result = future.get(timeout, TimeUnit.MILLISECONDS);
                        context.getAdaptorMap().put(detailNode.resultKey(), result);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                        needThreadExp = true;
                    }
                }

            }

            if (needThreadExp) {
                throw new RuntimeException();
            }

        }

    }


    /**
     * 流程中的参数
     */
    public static class RunData {
        private String paramOne;
        private String paramTwo;

        public String getParamOne() {
            return paramOne;
        }

        public void setParamOne(String paramOne) {
            this.paramOne = paramOne;
        }

        public String getParamTwo() {
            return paramTwo;
        }

        public void setParamTwo(String paramTwo) {
            this.paramTwo = paramTwo;
        }
    }

    private Map<String, List<String>> groupByGroupName(FlowNode flowNode) {

        Map<String, List<String>> nodeGroup = new LinkedHashMap<>();

        for (String nodeKey : flowNode.getNodeList()) {

            String groupName = getGroupName(nodeKey);
            String nodeName = getNodeName(nodeKey);

            if (StrUtil.isBlank(groupName)) {
                ArrayList<String> nodeNameList = new ArrayList<>();
                nodeNameList.add(nodeName);
                nodeGroup.put(nodeName, nodeNameList);
            } else {
                List<String> nodeNameList = nodeGroup.get(groupName);
                if (nodeNameList == null) {
                    nodeNameList = new ArrayList<>();
                }
                nodeNameList.add(nodeName);
                nodeGroup.put(groupName, nodeNameList);
            }
        }
        return nodeGroup;
    }

    private String getGroupName(String nodeKey) {
        String[] arr = nodeKey.split("_");
        return arr.length == 2 ? arr[1] : null;
    }

    private String getNodeName(String nodeKey) {
        String[] arr = nodeKey.split("_");
        return arr.length == 2 ? arr[1] : arr[0];
    }


}
