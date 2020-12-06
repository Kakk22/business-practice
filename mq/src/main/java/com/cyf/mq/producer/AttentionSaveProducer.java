package com.cyf.mq.producer;


import com.cyf.entity.Attention;
import com.cyf.mq.AttentionSaveMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by cyf
 * @date 2020/10/29.
 */
@Component
@Slf4j
public class AttentionSaveProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMsg(Long userId, Long authorId) {
        AttentionSaveMessage message = new AttentionSaveMessage();
        Attention attention = new Attention();
        attention.setAuthorId(authorId);
        attention.setUserId(userId);
        message.setAttention(attention);
        rocketMQTemplate.convertAndSend(AttentionSaveMessage.TOPIC, message);
    }

}
