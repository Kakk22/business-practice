package com.cyf.like.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyf.like.entity.Comment;
import org.apache.ibatis.annotations.Param;


/**
 * 评价mapper
 *
 * @author by cyf
 * @date 2020/10/19.
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 更新点赞总数
     *
     * @param comment /
     * @return /
     */
    int updateLikeCount(@Param("comment")Comment comment);
}
