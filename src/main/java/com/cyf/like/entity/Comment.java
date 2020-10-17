package com.cyf.like.entity;

import lombok.Data;

import java.util.Date;

/** 评价
 * @author by cyf
 * @date 2020/10/17.
 */
@Data
public class Comment {
    private Long id;
    private String username;
    private String content;
    private Date createTime;
    private Long like;
    private Long SourceId;
    /**
     * 回复数量
     */
    private Integer replayCount;
}
