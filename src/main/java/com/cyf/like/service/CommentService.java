package com.cyf.like.service;

import com.cyf.like.entity.Comment;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/10/17.
 */
public interface CommentService {

    /**
     * 获取评论列表
     * @return
     */
    List<Comment> list();

    /**
     * 定时任务 更新redis 点赞数到mysql
     */
    void updateLike(Comment comment);
}
