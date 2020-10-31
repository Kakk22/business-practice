package com.cyf.service;

import java.util.List;
import java.util.Map;

/**
 * 用户关注服务类
 *
 * @author by cyf
 * @date 2020/10/26.
 */
public interface AttentionService {

    /**
     * 关注
     *
     * @param userId   userId
     * @param authorId 作者id
     */
    void attention(Long userId, Long authorId);

    /**
     * 取消关注
     *
     * @param userId   userId
     * @param authorId 作者id
     */
    void unAttention(Long userId, Long authorId);

    /**
     * 查询作者的粉丝量
     *
     * @param authorId 作者id
     * @param type     请求来源 pc还是小程序
     * @return 粉丝数量
     */
    Map<String, Object> queryFansCount(Long authorId, Integer type);

    /**
     * 是否已经关注
     *
     * @param userId   userId
     * @param authorId 作者id
     * @return true/false
     */
    boolean isAttention(Long userId, Long authorId);

    /**
     * 查看用户关注列表
     *
     * @param userId userId
     * @return /
     */
    List queryList(Long userId);


}
