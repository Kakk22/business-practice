package com.cyf.mq.consumer;


import com.cyf.mapper.AttentionMapper;
import com.cyf.mq.AttentionSaveMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by cyf
 * @date 2020/10/29.
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = AttentionSaveMessage.TOPIC, consumerGroup = "${rocketmq.producer.group}")
public class AttentionSaveConsumer implements RocketMQListener<AttentionSaveMessage> {

    @Autowired
    private AttentionMapper attentionMapper;

    @Override
    public void onMessage(AttentionSaveMessage message) {
        log.info("rocketMQ receive message:" + message.getAttention().toString());
        attentionMapper.insert(message.getAttention());
    }
}
