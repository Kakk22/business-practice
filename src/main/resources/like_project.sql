CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `content` text NOT NULL COMMENT '评价内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `like` bigint NULL COMMENT '点赞数',
  `sourse_id` bigint NOT NULL COMMENT '课程id',
  `replay_count` int NOT NULL COMMENT '回复数',
  PRIMARY KEY (`id`)
);

CREATE TABLE `source`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime NULL COMMENT '创建时间',
  `name` varchar(64) NULL COMMENT '课程名称',
  PRIMARY KEY (`id`)
);

CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL,
  PRIMARY KEY (`id`)
);

