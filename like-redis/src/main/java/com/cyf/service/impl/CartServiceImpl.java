package com.cyf.service.impl;

import com.cyf.common.RedisConstant;
import com.cyf.service.CartService;
import com.cyf.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author by cyf
 * @date 2020/10/28.
 */
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private RedisService redisService;


    @Override
    public void add(Long userId, Long goodId) {
        String key = RedisConstant.CART_USER_ID + userId;
        redisService.hSet(key, String.valueOf(goodId), 1);
    }

    @Override
    public void modifyCount(Long userId, Long goodId, Integer type) {
        String key = RedisConstant.CART_USER_ID + userId;
        if (type.equals(0)) {
            redisService.hIncr(key, String.valueOf(goodId), 1L);
        } else {
            redisService.hDecr(key, String.valueOf(goodId), 1L);
        }
    }

    @Override
    public Object getGoodsCount(Long userId, Long goodId) {
        String key = RedisConstant.CART_USER_ID + userId;
        return redisService.hGet(key, String.valueOf(goodId));
    }

    @Override
    public void delGoods(Long userId, List<Long> goodIds) {
        String key = RedisConstant.CART_USER_ID + userId;
        for (Long goodId : goodIds) {
            redisService.hDel(key, String.valueOf(goodId));
        }
    }

    @Override
    public List getGoodsDetail(Long userId) {
        String key = RedisConstant.CART_USER_ID + userId;
        Map<Object, Object> map = redisService.hGetAll(key);
        Set<Object> goodIds = map.keySet();
        //根据商品id去查询详细
        goodIds.forEach(id -> System.out.printf("userId:%s-----goodId:%s %s%n", userId, goodIds));
        return null;
    }
}
