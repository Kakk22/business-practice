package com.cyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author by cyf
 * @date 2020/12/6.
 */
@MapperScan("com.cyf.mapper")
@SpringBootApplication
public class RedissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissionApplication.class, args);
    }
}
