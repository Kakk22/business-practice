package com.cyf.like;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.cyf.like.mapper")
@SpringBootApplication
@EnableScheduling
public class LikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeApplication.class, args);
    }

}
