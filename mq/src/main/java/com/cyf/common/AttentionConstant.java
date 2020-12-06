package com.cyf.common;

/**
 * @author by cyf
 * @date 2020/10/26.
 */
public class AttentionConstant {
    public static final String BASE = "mq:";
    /**
     * 用户关注了那些作者 Set集合 key  value 为作者id
     */
    public static final String ATTENTION_OPEN_ID = BASE + "attention:openId:";
    /**
     * 存放作者的关注量
     */
    public static final String ATTENTION_AUTHOR_FANS = BASE + "attention:fans:author:";
}
