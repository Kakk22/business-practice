package com.cyf.like.service;

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
    String like(Long userId,Long commentId);

    /**
     * 获取某个评价的点赞数
     * @param commentId
     * @return
     */
    Long getLikeCount(Long commentId);
}
