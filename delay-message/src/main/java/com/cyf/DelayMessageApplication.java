package com.cyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 陈一锋
 * @date 2021/7/11.
 */
@EnableScheduling
@SpringBootApplication
public class DelayMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DelayMessageApplication.class, args);
    }
}
