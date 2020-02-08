-- 用户信息表
insert into user_info (id, username, password, user_type) values (1, 'test1', '12345678', '1');
insert into user_info (id, username, password, user_type) values (2, 'test2', 'aaaa', '2');
insert into user_info (id, username, password, user_type) values (3, 'test3', 'bbbb', '1');
insert into user_info (id, username, password, user_type) values (4, 'test4', 'cccc', '2');
insert into user_info (id, username, password, user_type) values (5, 'test5', 'dddd', '1');

-- 用户登录表, logid 和 username复合主键
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
