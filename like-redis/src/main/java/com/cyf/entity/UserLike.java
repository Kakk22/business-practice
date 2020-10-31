package com.cyf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/** 点赞实体类
 * @author by cyf
 * @date 2020/10/18.
 */
@Data
@TableName("user_like")
public class UserLike {
    private Long userId;
    private Long commentId;
}
