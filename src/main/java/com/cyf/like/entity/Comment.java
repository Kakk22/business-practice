package com.cyf.like.entity;

import lombok.Data;

import java.util.Date;

/** 评价
 * @author by cyf
 * @date 2020/10/17.
 */
@Data
public class Comment {
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 点赞数
     */
    private Long like;
    /**
     * 课程id
     */
    private Long SourceId;
    /**
     * 回复数量
     */
    private Integer replayCount;
}
