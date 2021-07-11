package com.cyf.produce;

import com.cyf.constant.RedisConstant;
import com.cyf.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
@Component
@Slf4j
public class MessageProduce {

    private final Thread thread;
    @Autowired
    private RedisService redisService;

    public MessageProduce() {
        thread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                log.info("生产者开始生产消息");
                for (int i = 0; i < 10; i++) {
                    //10s后处理
                    long handleTime = System.currentTimeMillis() + 30 * 1000;
                    redisService.zSet(RedisConstant.DELAY_MESSAGE_KEY, random.nextInt(100000), (double) handleTime);
                }
                log.info("生产者生产消息结束,休息30秒后继续生产");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @PostConstruct
    public void start() {
        //开启线程
        thread.start();
    }
}
