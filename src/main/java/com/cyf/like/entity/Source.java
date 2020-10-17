package com.cyf.like.entity;

import lombok.Data;

import java.util.Date;

/** 课程实体类
 * @author by cyf
 * @date 2020/10/17.
 */
@Data
public class Source {
    private Long id;
    private Date createTime;
    private String name;
}
