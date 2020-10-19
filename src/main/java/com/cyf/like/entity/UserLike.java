package com.cyf.like.entity;

import lombok.Data;

/** 点赞实体类
 * @author by cyf
 * @date 2020/10/18.
 */
@Data
public class UserLike {
    private Long id;
    private Long userId;
    private Long commentId;
}
