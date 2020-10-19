package com.cyf.like.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.like.common.RedisConstant;
import com.cyf.like.entity.Comment;
import com.cyf.like.entity.UserLike;
import com.cyf.like.mapper.LikeMapper;
import com.cyf.like.service.LikeService;
import com.cyf.like.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 点赞实现类
 *
 * @author by cyf
 * @date 2020/10/17.
 */
@Service
@Slf4j
@AllArgsConstructor
public class LikeServiceImpl extends ServiceImpl<LikeMapper, UserLike> implements LikeService {

    private RedisService redisService;

    private CommentServiceImpl commentService;

    /**
     * 获取评价列表的点赞数
     *
     * @param commentIds 评价id列表
     * @return map key为评价id value 为点赞数
     */
    @Override
    public Map<Long, Integer> getLikeCount(List<Long> commentIds) {
        //实现思路，从redis中取出点赞数，如果有值，则直接返回，
        //如果为空 则去mysql中找到点赞数并存入redis后返回
        Map<Long, Integer> map = new HashMap<>(16);
        for (Long id : commentIds) {
            Integer likeCount = (Integer) redisService.hGet(RedisConstant.COMMENT_LIKE_COUNT, String.valueOf(id));
            if (likeCount == null) {
                //去mysql中取并存入redis
                likeCount = 0;
                redisService.hSet(RedisConstant.COMMENT_LIKE_COUNT, String.valueOf(id), likeCount);
            }
            map.put(id, likeCount);
        }
        return map;
    }


    /**
     * 取消点赞
     *
     * @param userId    用户id
     * @param commentId 评价id
     * @return
     */
    @Override
    public void unLike(Long userId, Long commentId) {
        // TODO: 2020/10/19 参数校验 只有点过赞才能取消

        Set set = (Set<Long>) redisService.hGet(RedisConstant.USER_LIKE_COMMENT_KEY, String.valueOf(userId));
        //移除用户点赞的评论
        set.remove(commentId);
        redisService.hSet(RedisConstant.USER_LIKE_COMMENT_KEY, String.valueOf(userId), set);
        //评论总点赞数减1
        redisService.hDecr(RedisConstant.COMMENT_LIKE_COUNT, String.valueOf(commentId), 1L);
    }


    /**
     * 点赞
     *
     * @param userId    用户id
     * @param commentId 评价id
     * @return
     */
    @Override
    public void like(Long userId, Long commentId) {
        //实现逻辑，每个用户对一条评价只能点一次赞
        //记录用户点赞了哪些评论 key为用户id value set<评价id>
        //记录评论点赞总数      key为评价id value 为String 存入redis中
        // TODO: 2020/10/17  参数校验 是否点过赞


        Set set = (Set<Long>) redisService.hGet(RedisConstant.USER_LIKE_COMMENT_KEY, String.valueOf(userId));
        if (CollectionUtil.isEmpty(set)) {
            set = new HashSet<Long>();
        }
        //记录用户点赞哪些评论 key为用户id value为Set<评论id>
        set.add(commentId);
        redisService.hSet(RedisConstant.USER_LIKE_COMMENT_KEY, String.valueOf(userId), set);
        //记录评论点赞总数
        redisService.hIncr(RedisConstant.COMMENT_LIKE_COUNT, String.valueOf(commentId), 1L);
    }


    /**
     * 将新点赞的数据更新到数据库
     * 并将redis中的值清除，下一次获取点赞信息会把mysql中的点赞加上redis的数据返回
     * 所以每次只同步到有更新的数据
     * 2小时同步一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    //@Scheduled(cron = "0 0 0/2 * * ?")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asynchronousUpdate() {
        log.info("time:{},开始执行redis数据持久化到mysql定时任务",DateUtil.now());
        //redis获取评论点赞信息
        Map map = redisService.hGetAll(RedisConstant.COMMENT_LIKE_COUNT);
        if (CollectionUtil.isNotEmpty(map)) {
            List<Comment> commentList = new ArrayList<>();
            Set<Map.Entry<String, Long>> set = map.entrySet();
            for (Map.Entry<String, Long> entry : set) {
                Comment comment = new Comment();
                comment.setId(Long.parseLong(entry.getKey()));
                comment.setLike(entry.getValue());
                commentList.add(comment);
            }
            //更新评论点赞数
            commentService.updateBatchById(commentList);
        }
        //redis获取用户喜欢的文章
        Map likeMap = redisService.hGetAll(RedisConstant.USER_LIKE_COMMENT_KEY);
        if (CollectionUtil.isNotEmpty(likeMap)) {
            List<UserLike> userLikes = new ArrayList<>();
            Set<Map.Entry<String, Long>> entries = likeMap.entrySet();
            for (Map.Entry<String, Long> entry : entries) {
                UserLike userLike = new UserLike();
                userLike.setUserId(Long.parseLong(entry.getKey()));
                userLike.setCommentId(entry.getValue());
                userLikes.add(userLike);
            }
            //更新用户喜欢文章到db
            saveOrUpdateBatch(userLikes);
        }
        log.info("time:{},redis数据持久化到mysql定时任务完成",DateUtil.now());
    }
}
