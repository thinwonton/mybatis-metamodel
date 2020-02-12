drop table if exists music;
CREATE table music
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name        varchar(100) NOT NULL,
    authorName varchar(100),
    create_date DATETIME,
    update_date DATETIME,
    length INTEGER,
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8  AUTO_INCREMENT = 6;

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

-- 用户登录表, logid 和 username复合主键
drop table if exists user_login;
create table user_login(
    logid     BIGINT(20)  NOT NULL AUTO_INCREMENT,
    username  varchar(32) NOT NULL,
    logindate DATETIME,
    loginip   varchar(16),
    PRIMARY KEY (logid, username)
);
