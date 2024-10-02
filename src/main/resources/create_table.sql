-- 数据库创建

-- 创建库
create database if not exists login_test;

-- 切换库
use login_test;

-- 用户表
create table if not exists `user`
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                       not null comment '账号',
    userPassword varchar(512)                       null comment '密码',
    userName     varchar(256)                       null comment '用户昵称',
    userAvatar   varchar(1024)                      null comment '用户头像',
    wxOpenId     varchar(256)                       null comment '微信openid',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
) comment '用户' collate = utf8mb4_unicode_ci;
