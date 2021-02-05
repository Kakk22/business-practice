package com.cyf.service;

/**
 * @author by cyf
 * @date 2020/12/6.
 */
public interface ProductService {

    /**
     * 减少商品库存
     *
     * @param id 商品id
     * @param count     数量
     * @return  /
     */
    String decrease(Integer id, Integer count);
}
