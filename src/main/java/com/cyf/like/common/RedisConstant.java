package com.cyf.like.common;

/**
 * redis key
 *
 * @author by cyf
 * @date 2020/10/17.
 */
public class RedisConstant {
    /**
     * 记录单条评价点击过的用户id
     */
    public static final String COMMENT_LIKE = "like:commentId:";
    /**
     * 用户点赞的评论
     */
    public static final String USER_LIKE_COMMENT_KEY = "like:user:comment:key";
    /**
     *记录评价模块每条评价的点赞总数
     */
    public static final String COMMENT_LIKE_COUNT = "like:commentCount";
}
