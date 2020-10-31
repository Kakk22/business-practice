package com.cyf.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by cyf
 * @date 2020/10/25.
 */
@Component
@Slf4j
public class SpringProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMsg(String topic,String msg){
        System.out.println("发送报文:"+msg);
        rocketMQTemplate.convertAndSend(topic,msg);
    }
}

