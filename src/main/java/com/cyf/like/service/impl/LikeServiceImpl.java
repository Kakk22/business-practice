package com.cyf.like.service.impl;

import com.cyf.like.common.RedisConstant;
import com.cyf.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 点赞实现类
 *
 * @author by cyf
 * @date 2020/10/17.
 */
@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private RedisTemplate redisTemplate;

    /**
     * 获取评价的 点赞数
     * @param commentId
     * @return
     */
    @Override
    public Long getLikeCount(Long commentId) {
        return redisTemplate.opsForSet().size(RedisConstant.COMMENT_LIKE+commentId);
    }

    /**
     * 点赞
     *
     * @param userId    用户id
     * @param commentId 评价id
     * @return
     */
    @Override
    public String like(Long userId, Long commentId) {
        //实现逻辑，每个用户对一条评价只能点一次赞
        //将评价id作为key 用户id作为value set存入 redis中
        // TODO: 2020/10/17  参数校验

        //是否点过赞
        Boolean result = redisTemplate.opsForSet().isMember(RedisConstant.COMMENT_LIKE + commentId, userId);
        if (!result){
            redisTemplate.opsForSet().add(RedisConstant.COMMENT_LIKE + commentId, userId);
            return "点赞成功";
        }
        return "已经点过赞了";
    }
}
