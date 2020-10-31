package com.cyf.controller;

import com.cyf.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public void doLike(@RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId){
        likeService.like(userId,commentId);
    }
    /**
     * 取消点赞功能
     * @param userId 用户id
     * @param commentId 评价id
     * @return
     */
    @PostMapping("/unLike")
    public void unLike(@RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId){
        likeService.unLike(userId,commentId);
    }

    /**
     * 获取指定评价点赞数
     * @param commentId 评价id
     * @return
     */
    @GetMapping("/count")
    public Map getLikeCount(@RequestParam("commentId") List<Long> commentId){
        return likeService.getLikeCount(commentId);
    }
}
