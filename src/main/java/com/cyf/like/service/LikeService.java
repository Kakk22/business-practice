package com.cyf.like.service;

import java.util.List;
import java.util.Map;

/**
 * @author by cyf
 * @date 2020/10/17.
 */
public interface LikeService {

    /**
     * 点赞
     * @param userId 用户id
     * @param commentId 评价id
     * @return
     */
    void like(Long userId,Long commentId);

    /**
     * 获取评价列表对应的点赞数
     * @param commentId 评价id
     * @return
     */
    Map getLikeCount(List<Long> commentId);





    /**
     * 取消点赞
     * @param userId 用户id
     * @param commentId 评价id
     * @return
     */
    void unLike(Long userId,Long commentId);

    /**
     * 定时任务异步更新redis点赞数到数据库
     */
    void asynchronousUpdate();
}
