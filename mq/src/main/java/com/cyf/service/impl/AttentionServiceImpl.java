package com.cyf.service.impl;


import com.cyf.common.AttentionConstant;
import com.cyf.mq.producer.AttentionSaveProducer;
import com.cyf.service.AttentionService;
import com.cyf.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关注实现类
 *
 * @author by cyf
 * @date 2020/10/26.
 */
@Service
@RequiredArgsConstructor
public class AttentionServiceImpl implements AttentionService {

    private final RedisService redisService;
    private final AttentionSaveProducer attentionSaveProducer;

    /**
     * 关注功能
     *
     */
    @Override
    public void attention(Long userId, Long authorId) {
        String key = AttentionConstant.ATTENTION_OPEN_ID + userId;
        if (redisService.sAdd(key, authorId) == 0) {
           // Asserts.fail("已经关注过该作者");
        }
        //成功 作者关注量+1
        redisService.sAdd(AttentionConstant.ATTENTION_AUTHOR_FANS + authorId, userId);
        attentionSaveProducer.sendMsg(userId,authorId);
    }

    /**
     * 取消关注
     *
     * @param userId   userId
     * @param authorId 作者id
     */
    @Override
    public void unAttention(Long userId, Long authorId) {
        String key = AttentionConstant.ATTENTION_OPEN_ID + userId;
        long count = redisService.sRemove(key, authorId);
        if (count == 0) {
            //Asserts.fail("未关注该作者,操作失败");
        }
        //作者 关注量-1
        redisService.sRemove(AttentionConstant.ATTENTION_AUTHOR_FANS + authorId, userId);
    }

    /**
     * 查询是否关注
     *
     * @param userId   userId
     * @param authorId 作者id
     * @return true/false
     */
    @Override
    public boolean isAttention(Long userId, Long authorId) {
        String key = AttentionConstant.ATTENTION_OPEN_ID + userId;
        return redisService.sIsMember(key, authorId);
    }

    /**
     * 查询粉丝数量
     *
     * @param authorId 作者id
     * @param type     请求来源 pc还是小程序
     * @return 粉丝数量和详细信息
     */
    @Override
    public Map<String, Object> queryFansCount(Long authorId, Integer type) {
        long count = redisService.sSize(AttentionConstant.ATTENTION_AUTHOR_FANS + authorId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("count", count);
        return map;
    }

    /**
     * 查看我的关注列表
     *
     * @param userId userId
     * @return /
     */
    @Override
    public List queryList(Long userId) {
        return null;
    }
}
