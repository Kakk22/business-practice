package com.cyf.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author by cyf
 * @date 2020/10/25.
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.producer.topic}", consumerGroup = "${rocketmq.producer.group}")
public class SpringConsumer implements RocketMQListener<String>
{
    @Override
    public void onMessage(String msg)
    {
        log.info("收到消息:" + msg);
    }


}
