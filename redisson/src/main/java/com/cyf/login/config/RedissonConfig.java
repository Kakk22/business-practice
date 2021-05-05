package com.cyf.login.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by cyf
 * @date 2020/12/6.
 */
@Configuration
public class RedissonConfig {

//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson() throws IOException {
//        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
//    }

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                //.setAddress("redis://127.0.0.1:6379").setPassword("redispassword")
                .setAddress("redis://127.0.0.1:6379")
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);
        return Redisson.create(config);
    }
}
