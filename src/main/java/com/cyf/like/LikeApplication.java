package com.cyf.like;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeApplication.class, args);
    }

}
