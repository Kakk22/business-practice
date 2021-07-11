package com.cyf.service.impl;

import com.cyf.constant.RedisConstant;
import com.cyf.service.DelayMessageService;
import com.cyf.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
@Slf4j
@Service
public class DelayMessageServiceImpl implements DelayMessageService {

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0/1 * * * ?")
    @Override
    public void execute() {
        log.info("开始扫描redis延时任务");
        long time = System.currentTimeMillis();
        String delayMessageKey = RedisConstant.DELAY_MESSAGE_KEY;
        Set<Object> data = redisService.zRangeScore(delayMessageKey, 0.0, time);

        if (data == null || data.size() == 0) {
            log.info("redis获取延时任务数量为0");
            return;
        }
        log.info("开始将任务存入任务队列");

        redisService.lPushAll(RedisConstant.LIST_MESSAGE_KEY, data.toArray());

        log.info("任务存入任务队列成功");

        redisService.zRemoveByScore(delayMessageKey, 0.0, time);
    }
}
