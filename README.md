# business practice

## like-redis

使用redis实现 点赞功能

使用定时任务实现redis 异步同步数据至MySQL数据库

## MQ 
业务场景：
关注功能,关注数据存在redis中,由于数据比较重要,再数据加入redis中可使用MQ发送一条信息异步存入数据库中


## bitmap
使用redis bitmap实现用户每日签到功能

## redisson
使用redisson框架实现分布式锁