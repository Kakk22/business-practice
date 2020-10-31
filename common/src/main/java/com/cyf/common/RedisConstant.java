package com.cyf.common;

/**
 * redis key
 *
 * @author by cyf
 * @date 2020/10/17.
 */
public class RedisConstant {
    /**
     * 用户点赞的评论 set结构
     */
    public static final String LIKE_COMMENT_USERID = "like:comment:userId:";
    /**
     * 记录评价模块每条评价的点赞总数
     */
    public static final String COMMENT_LIKE_COUNT = "like:commentCount";
    /**
     * 记录用户点赞哪些评论 hash结构
     */
    public static final String USER_LIKE = "user:like";
    /**
     * 记录用户取消点赞哪些评论 用于定时任务时删除数据库中的数据 hash结构
     */
    public static final String USER_UNLIKE = "user:unlike";


    //------------------- 购物车模块---------------------------
    /**
     * 用户购物车key  hash结构 hkey为商品id value为数量
     */
    public static final String CART_USER_ID = "cart:user:id:";
}
