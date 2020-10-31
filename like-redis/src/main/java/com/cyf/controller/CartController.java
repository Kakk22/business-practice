package com.cyf.controller;

import com.cyf.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by cyf
 * @date 2020/10/29.
 */
@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;


    @PostMapping("/add")
    public void add(@RequestParam Long userId,
                    @RequestParam Long goodId) {
        cartService.add(userId, goodId);
    }

    @PostMapping("/modifyCount")
    public void modifyCount(@RequestParam Long userId,
                            @RequestParam Long goodId,
                            @RequestParam Integer type) {
        cartService.modifyCount(userId, goodId, type);
    }

    @GetMapping("/getGoodsCount")
    public Object getGoodsCount(@RequestParam Long userId,
                              @RequestParam Long goodId) {
        return cartService.getGoodsCount(userId, goodId);
    }

    @PostMapping("/del")
    public void add(@RequestParam Long userId,
                    @RequestParam List<Long> goodIds) {
        cartService.delGoods(userId, goodIds);
    }

    @GetMapping("/getGoodsDetail")
    public void getGoodsDetail(@RequestParam Long userId) {
        cartService.getGoodsDetail(userId);
    }


}
