package com.cyf.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.like.entity.Comment;
import com.cyf.like.mapper.CommentMapper;
import com.cyf.like.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/10/17.
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {

    private CommentMapper commentMapper;
    @Override
    public void updateLike(Comment comment) {
        commentMapper.updateLikeCount(comment);
    }


    @Override
    public List<Comment> list() {
        return null;
    }



}
