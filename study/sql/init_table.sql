/*用户表*/
CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `nickname` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `status` char(1) NOT NULL COMMENT '0-禁用，1-正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
