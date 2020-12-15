package com.cyf.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.cyf.service.RedisService;
import com.cyf.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 签到实现:
 * 需求:1.用户每天可签到一次
 * 2.可获取用户当月连续签到多少天
 * 3.可以检查用户当天是否签到
 * 4.获取当月的签到情况
 * 5.获取当月签到次数
 *
 * @author 陈一锋
 * @date 2020/12/13.
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void checkIn(Long userId, LocalDate date) {
        long offset = getOffset(date);
        redisService.setBit(getKey(userId, date), offset, true);
    }

    @Override
    public Boolean isCheckIn(Long userId, LocalDate date) {
        Long offset = getOffset(date);
        String key = getKey(userId, date);
        return redisService.getBit(key, offset);
    }

    @Override
    public Long getSignCount(Long userId, LocalDate date) {
        Long result = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(getKey(userId, date).getBytes());
            }
        });
        return Optional.ofNullable(result).orElse(0L);
    }

    /**
     * 查询今天之前最近的连续签到的天数
     *
     * @param userId /
     * @param date   /
     * @return
     */
    @Override
    public long getContinuousSignCount(Long userId, LocalDate date) {
        int signCount = 0;
        List<Long> list = redisService.bitfield(getKey(userId, date), date.getDayOfMonth(), 0);
        if (CollUtil.isNotEmpty(list)) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            Long v = list.get(0) == null ? 0L : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                //先右移再左移 如果相等则低位为0
                if (v >> 1 << 1 == v) {
                    // 低位为0且非当天 说明连续签到中断了
                    if (i > 0) break;
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }

    @Override
    public Map<String, Boolean> getSignInfo(Long userId, LocalDate date) {
        Map<String, Boolean> signMap = new TreeMap<>();
        List<Long> list = redisService.bitfield(getKey(userId, date), date.lengthOfMonth(), 0);
        if (CollUtil.isNotEmpty(list)) {
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate day = date.withDayOfMonth(i);
                signMap.put(formatDate(day, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return signMap;
    }

    @Override
    public LocalDate getFirstSignDate(Long userId, LocalDate date) {
        Long result = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitPos(getKey(userId, date).getBytes(), true));
        return result < 0 ? null : date.withDayOfMonth((int) (result + 1L));
    }

    private static String getKey(Long userId, LocalDate date) {
        return String.format("user:sign:%d:%s", userId, formatDate(date));
    }

    private static Long getOffset(LocalDate date) {
        int i = date.getDayOfMonth() - 1;
        return Long.parseLong(String.valueOf(i));
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
