-- 用户信息表
drop table if exists user_info;
create table user_info
(
    id       BIGINT(20)  NOT NULL AUTO_INCREMENT,
    username varchar(32) NOT NULL,
    password varchar(32) DEFAULT '12345678',
    user_type varchar(10),
    state  varchar(12),
    realname varchar(50),
    address  varchar(200),
    PRIMARY KEY (id)
)DEFAULT CHARSET = utf8  AUTO_INCREMENT = 6;
