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

CREATE TABLE
    t_user_extension
    (
        id bigint unsigned NOT NULL AUTO_INCREMENT,
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        user_brief VARCHAR(500) COMMENT '用户简介',
        create_time DATETIME NOT NULL,
        update_time DATETIME,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
CREATE TABLE
    t_article
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        article_id VARCHAR(64) NOT NULL COMMENT '文章id',
        user_id VARCHAR(64) NOT NULL,
        status CHAR(1) NOT NULL COMMENT '0-审核未通过，1-草稿，2-审核中，3-发布成功',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        update_time DATETIME COMMENT '修改时间',
        publish_time DATETIME COMMENT '发布时间',
        title VARCHAR(64) NOT NULL,
        content text NOT NULL COMMENT '文章内容',
        outline VARCHAR(300) COMMENT '文章概要',
        image VARCHAR(100),
        comment_count INT DEFAULT '0' COMMENT '评论数',
        PRIMARY KEY (id),
        CONSTRAINT article_id UNIQUE (article_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';
 
CREATE TABLE
    t_picture
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        picture_id VARCHAR(64) NOT NULL COMMENT '图片id',
        create_time DATETIME NOT NULL,
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        pic_url VARCHAR(200) NOT NULL COMMENT '图片路径',
        PRIMARY KEY (id),
        CONSTRAINT picture_id UNIQUE (picture_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;    
    
CREATE TABLE
    t_role
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        role_id VARCHAR(32) NOT NULL,
        role_name VARCHAR(32) NOT NULL,
        create_time DATETIME NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT role_id UNIQUE (role_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
CREATE TABLE
    t_role_permission
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        role_id VARCHAR(32) NOT NULL,
        permission_id VARCHAR(32) NOT NULL,
        create_time DATETIME NOT NULL,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8; 
    
CREATE TABLE
    t_comment
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        comment_id VARCHAR(64) NOT NULL,
        user_id VARCHAR(64) NOT NULL,
        article_id VARCHAR(64) NOT NULL,
        comment_content VARCHAR(300) NOT NULL,
        reply_to_user_id VARCHAR(64) COMMENT '回复评论者的userId',
        create_time DATETIME NOT NULL,
        praise_counts INT(10) unsigned DEFAULT '0' COMMENT '赞的数目',
        PRIMARY KEY (id),
        CONSTRAINT comment_id UNIQUE (comment_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论信息';  
    
CREATE TABLE `t_collection` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `collection_id` varchar(64) NOT NULL COMMENT '收藏id',
  `user_id` varchar(64) NOT NULL,
  `article_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;    

CREATE TABLE
    t_message
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        message_id VARCHAR(64) NOT NULL COMMENT '消息id',
        receiver_user_id VARCHAR(64) NOT NULL COMMENT '消息接收用户id',
        sender_name VARCHAR(64) NOT NULL COMMENT '消息接收用户名称',
        sender_user_id VARCHAR(64) NOT NULL COMMENT '发送者用户id',
        message_type CHAR(1) NOT NULL COMMENT '消息类别 1-评论 2-点赞 3-系统消息',
        content_id VARCHAR(64)  COMMENT '内容id',
        content_name VARCHAR(64)  COMMENT '内容名称',
        create_time DATETIME NOT NULL COMMENT '创建时间', 
        update_time DATETIME COMMENT '修改时间',
        PRIMARY KEY (id),
        CONSTRAINT message_id UNIQUE (message_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';
    
CREATE TABLE
    t_message
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        message_id VARCHAR(64) NOT NULL COMMENT '消息id',
        status CHAR(1) DEFAULT '0' NOT NULL COMMENT '0-未读 1-已读',
        receiver_user_id VARCHAR(64) NOT NULL COMMENT '接收者id',
        sender_name VARCHAR(64) NOT NULL,
        sender_user_id VARCHAR(64) NOT NULL COMMENT '发送消息者id',
        message_type CHAR(1) NOT NULL COMMENT '消息类别 1-评论 2-点赞 3-系统消息',
        content_id VARCHAR(64),
        content_name VARCHAR(64) COMMENT '内容名称',
        create_time DATETIME NOT NULL,
        comment_content VARCHAR(300) COMMENT '评论内容',
        PRIMARY KEY (id),
        CONSTRAINT message_id UNIQUE (message_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息通知表';  