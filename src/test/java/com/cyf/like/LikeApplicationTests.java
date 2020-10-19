package com.cyf.like;

import com.cyf.like.entity.UserLike;
import com.cyf.like.mapper.LikeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LikeApplicationTests {
    @Autowired
    private LikeMapper likeMapper;

    @Test
    void contextLoads() {
        List<UserLike> likes = likeMapper.selectList(null);
        likes.forEach(System.out::println);
    }

}
