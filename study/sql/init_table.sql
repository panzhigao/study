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
        PRIMARY KEY (id)
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