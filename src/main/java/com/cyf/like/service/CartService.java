package com.cyf.like.service;

import java.util.List;

/**
 * 购物车实现类 用redis实现
 * 添加商品
 * 添加数量
 * 商品总数
 * 删除商品
 * 获取购物车的所有商品
 * key为userId hkey为商品id value为数量
 *
 * @author by cyf
 * @date 2020/10/28.
 */
public interface CartService {

    /**
     * 添加购物车
     * @param userId /
     * @param goodId /
     */
    void add(Long userId, Long goodId);

    /**
     * 增加或减少商品数量
     * @param userId /
     * @param goodId /
     */
    void modifyCount(Long userId, Long goodId,Integer type);

    /**
     * 获取商品总数
     * @param userId /
     * @return 商品总数
     */
    List getGoodsCount(Long userId,Long goodId);

    /**
     * 删除商品
     * @param userId /
     * @param goodIds /
      */
    void delGoods(Long userId, List<Long> goodIds);

    /**
     * 获取购物车列表详细
     * @param userId /
     * @return 购物车列表详细
     */
    List getGoodsDetail(Long userId);

}
