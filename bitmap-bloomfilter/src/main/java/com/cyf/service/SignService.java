package com.cyf.service;

import java.time.LocalDate;
import java.util.Map;

/**
 * 签到服务类
 *
 * @author 陈一锋
 * @date 2020/12/13.
 */
public interface SignService {

    /**
     * 签到
     *
     * @param userId/
     * @param date/
     */
    void checkIn(Long userId, LocalDate date);

    /**
     * 检查是否签到
     *
     * @param userId /
     * @param date   /
     * @return /
     */
    Boolean isCheckIn(Long userId, LocalDate date);

    /**
     * 获取当月签到总数
     *
     * @param userId /
     * @param date   /
     * @return /
     */
    Long getSignCount(Long userId, LocalDate date);

    /**
     * 获取当月连续签到总数
     *
     * @param userId /
     * @param date   /
     * @return /
     */
    long getContinuousSignCount(Long userId, LocalDate date);

    /**
     * 获取用户签到日历
     *
     * @param userId /
     * @param date   /
     * @return /
     */
    Map<String, Boolean> getSignInfo(Long userId, LocalDate date);

    /**
     * 获取当月首次签到的日期
     *
     * @param userId /
     * @param date   /
     * @return /
     */
    LocalDate getFirstSignDate(Long userId, LocalDate date);

}
