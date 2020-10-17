package com.cyf.like.controller;

import com.cyf.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 点赞控制器
 * @author by cyf
 * @date 2020/10/17.
 */
@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;

    /**
     * 点赞功能
     * @param userId 用户id
     * @param commentId 评价id
     * @return
     */
    @PostMapping("/doLike")
    public String doLike(@RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId){
        return likeService.like(userId,commentId);
    }
}
