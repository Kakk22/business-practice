package com.cyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.entity.Product;
import com.cyf.mapper.ProductMapper;
import com.cyf.service.ProductService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by cyf
 * @date 2020/12/6.
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public String decrease(Integer id, Integer count) {
        String key = "decrease_product_amount_lock_" + id;
        RLock lock = redissonClient.getLock(key);
        System.out.println("商品id" + id + "  分布式锁lock=" + key);
        try {
            lock.lock();
            //加锁 操作很类似Java的ReentrantLock机制
            Product product = getById(id);
            if (product.getAmount() == 0) {
                System.out.println("该商品已卖光啦~");
                return "该商品已卖光啦~";
            }
            if (product.getAmount() < count) {
                System.out.println("商品数量不足");
                return "商品数量不足";
            }
            product.setAmount(product.getAmount() - count);
            updateById(product);
            System.out.println("当前线程+" + Thread.currentThread().getName() + " 商品id为" + id + " 当前库存:" + product.getAmount());
            return "当前库存:" + product.getAmount();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //解锁
            lock.unlock();
        }
        return "";
    }
}
