package com.cyf.controller;


import com.cyf.service.AttentionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by cyf
 * @date 2020/10/29.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/attention")
public class AttentionController {
    private AttentionService attentionService;

    @PostMapping("/create")
    public void create(Long userId,Long authorId){
        attentionService.attention(userId, authorId);
    }

}
