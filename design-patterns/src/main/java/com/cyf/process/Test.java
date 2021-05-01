package com.cyf.process;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author 陈一锋
 * @date 2021/5/2.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("classpath:application.xml");
        FlowNode testFlow = Flow.getTestFlow();
        FlowEngine flowEngine = (FlowEngine) applicationContext.getBean("flowEngine");
        FlowEngine.RunData runData = new FlowEngine.RunData();
        runData.setParamOne("one");
        runData.setParamTwo("tow");
        Context context = new Context();
        flowEngine.execute(testFlow,runData,context);
        Map<String, Object> adaptorMap = context.getAdaptorMap();
        //返回值结果
        System.out.println(adaptorMap.get("NodeOne"));
        System.out.println(adaptorMap.get("NodeTwo"));
    }

    public static class Flow {
        private static FlowNode testFlow = new FlowNode();

        static {
            testFlow.add(NodeOne.class, new FlowNode.NodeConf());
            testFlow.add(NodeTwo.class, new FlowNode.NodeConf());
            testFlow.add("three", NodeOne.class, new FlowNode.NodeConf());
            testFlow.add("three", NodeTwo.class, new FlowNode.NodeConf());
        }

        public static FlowNode getTestFlow() {
            return testFlow;
        }
    }
}
