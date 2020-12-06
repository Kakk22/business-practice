create table attention(
  id         bigint not null auto_increment,
  user_id    bigint comment '用户id',
  author_id  bigint comment '作者id',
  primary key (id)
)