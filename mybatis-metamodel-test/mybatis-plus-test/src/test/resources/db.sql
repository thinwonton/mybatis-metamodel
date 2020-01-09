drop table if exists music;
CREATE table music
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name        varchar(100) NOT NULL,
    create_date DATETIME,
    update_date DATETIME,
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8  AUTO_INCREMENT = 6;

-- 用户信息表
drop table if exists user_info;
create table user_info
(
    id       BIGINT(20)  NOT NULL AUTO_INCREMENT,
    username varchar(32) NOT NULL,
    password varchar(32) DEFAULT '12345678',
    user_type varchar(2),
    enabled  integer,
    realname varchar(50),
    qq       varchar(12),
    email    varchar(100),
    address  varchar(200),
    tel      varchar(30),
    PRIMARY KEY (id)
)DEFAULT CHARSET = utf8  AUTO_INCREMENT = 6;

insert into user_info (id, username, password, user_type) values (1, 'test1', '12345678', '1');
insert into user_info (id, username, password, user_type) values (2, 'test2', 'aaaa', '2');
insert into user_info (id, username, password, user_type) values (3, 'test3', 'bbbb', '1');
insert into user_info (id, username, password, user_type) values (4, 'test4', 'cccc', '2');
insert into user_info (id, username, password, user_type) values (5, 'test5', 'dddd', '1');

-- 用户登录表, logid 和 username复合主键
drop table if exists user_login;
create table user_login(
    logid     BIGINT(20)  NOT NULL AUTO_INCREMENT,
    username  varchar(32) NOT NULL,
    logindate DATETIME,
    loginip   varchar(16),
    PRIMARY KEY (logid, username)
);

insert into user_login (logid, username, logindate, loginip) values (1, 'test1', '2014-10-11 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (2, 'test1', '2014-10-21 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (3, 'test1', '2014-10-21 14:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (4, 'test1', '2014-11-21 11:20:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (5, 'test1', '2014-11-21 13:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (6, 'test2', '2014-11-21 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (7, 'test2', '2014-11-21 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (8, 'test3', '2014-11-21 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (9, 'test4', '2014-11-21 12:00:00', '192.168.1.123');
insert into user_login (logid, username, logindate, loginip) values (10, 'test5', '2014-11-21 12:00:00', '192.168.1.123');
