package com.cyf.consumer;

import com.cyf.constant.RedisConstant;
import com.cyf.service.RedisService;
import com.cyf.service.impl.ThreadPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
@Component
@Slf4j
public class MessageConsumer {

    private final Thread thread;
    @Autowired
    private RedisService redisService;

    public MessageConsumer() {
        thread = new Thread(() -> {
            while (true) {
                String listMessageKey = RedisConstant.LIST_MESSAGE_KEY;
                Object o = redisService.rPop(listMessageKey);
                if (o == null) {
                    log.info("从消息队列中取出数据为空,准备睡眠2s");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //任务不为空
                    ThreadPoolService.newTask(() -> {
                        try {
                            //模拟业务处理
                            log.info("进行业务处理:{}", o);
                            if ((Integer) o % 10 == 2) {
                                log.error("模10余数为2,模拟抛出异常");
                                throw new RuntimeException("业务异常");
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            log.info("业务处理完成");
                        } catch (Exception e) {
                            log.error("延时任务处理异常:", e);
                            //出现任务异常，将任务重新丢回redis
                            String delayMessageKey = RedisConstant.DELAY_MESSAGE_KEY;
                            //2分钟后再次处理
                            double handleTime = System.currentTimeMillis() + 2 * 60 * 1000;
                            redisService.zSet(delayMessageKey, o, handleTime);
                            log.info("将异常任务重新返回延时队列成功");
                        }
                        return null;
                    });
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
