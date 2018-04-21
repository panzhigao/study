DROP TABLE
    t_article;
CREATE TABLE
    t_article
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        article_id VARCHAR(64) NOT NULL COMMENT '文章id',
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        status CHAR(1) NOT NULL COMMENT '0-审核未通过，1-草稿，2-审核中，3-发布成功',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        update_time DATETIME COMMENT '修改时间',
        publish_time DATETIME COMMENT '发布时间',
        title VARCHAR(64) NOT NULL,
        content text NOT NULL COMMENT '文章内容',
        outline VARCHAR(300) COMMENT '文章概要',
        image VARCHAR(100) COMMENT '缩略图',
        comment_count INT DEFAULT '0' COMMENT '评论数',
        view_count INT DEFAULT '0' COMMENT '阅读数',
        type CHAR(1) DEFAULT '1' COMMENT '1-文章 2系统消息',
        PRIMARY KEY (id),
        CONSTRAINT article_id UNIQUE (article_id),
        INDEX t_article_ix2 (user_id, status, create_time, type)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';
DROP TABLE
    t_collection;
CREATE TABLE
    t_collection
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        collection_id VARCHAR(64) NOT NULL COMMENT '收藏id',
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        article_id VARCHAR(64) NOT NULL COMMENT '文章id',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        title VARCHAR(64) COMMENT '标题',
        PRIMARY KEY (id),
        CONSTRAINT user_id UNIQUE (user_id, article_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏表';
DROP TABLE
    t_comment;
CREATE TABLE
    t_comment
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        comment_id VARCHAR(64) NOT NULL COMMENT '评论id',
        user_id VARCHAR(64) NOT NULL COMMENT '评论者id',
        article_id VARCHAR(64) NOT NULL COMMENT '文章id',
        comment_content VARCHAR(300) NOT NULL COMMENT '评论内容',
        reply_to_user_id VARCHAR(64) COMMENT '接收评论者的userId',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        praise_counts bigint DEFAULT '0' NOT NULL COMMENT '点赞数',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论信息';
DROP TABLE
    t_message;
CREATE TABLE
    t_message
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        message_id VARCHAR(64) NOT NULL COMMENT '消息id',
        status CHAR(1) DEFAULT '0' NOT NULL COMMENT '0-未读 1-已读',
        receiver_user_id VARCHAR(64) NOT NULL COMMENT '接收者id',
        sender_name VARCHAR(64) COMMENT '发送者姓名',
        sender_user_id VARCHAR(64) COMMENT '发送消息者id',
        message_type CHAR(1) NOT NULL COMMENT '消息类别 1-评论 2-点赞 3-系统消息',
        content_id VARCHAR(64) COMMENT '内容id',
        content_name VARCHAR(64) COMMENT '内容名称',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        comment_content VARCHAR(300) COMMENT '评论内容',
        PRIMARY KEY (id),
        CONSTRAINT message_id UNIQUE (message_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息通知表';
DROP TABLE
    t_permission;
CREATE TABLE
    t_permission
    (
        id INT unsigned NOT NULL AUTO_INCREMENT,
        permission_id VARCHAR(64) NOT NULL COMMENT '权限id',
        permission_name VARCHAR(64) NOT NULL COMMENT '权限名',
        url VARCHAR(100) NOT NULL COMMENT 'url路径',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        pid VARCHAR(64) DEFAULT '0' NOT NULL COMMENT '父级id',
        level INT(10) unsigned DEFAULT '1' NOT NULL COMMENT '层级数',
        sort bigint unsigned DEFAULT '0' COMMENT '排序',
        icon VARCHAR(20) COMMENT '图标',
        type CHAR(1) NOT NULL COMMENT '类型 0-菜单 1-链接',
        create_user VARCHAR(64) NOT NULL COMMENT '创建者id',
        update_time DATETIME COMMENT '更新时间',
        update_user VARCHAR(64) COMMENT '修改人',
        PRIMARY KEY (id),
        CONSTRAINT permission_id UNIQUE (permission_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE
    t_picture;
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
DROP TABLE
    t_praise;
CREATE TABLE
    t_praise
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        praise_id VARCHAR(64) NOT NULL COMMENT '点赞id',
        comment_id VARCHAR(64) NOT NULL COMMENT '评论id',
        user_id VARCHAR(64) NOT NULL COMMENT '用户Id',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';
DROP TABLE
    t_role;
CREATE TABLE
    t_role
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        role_id VARCHAR(32) NOT NULL COMMENT '角色id',
        role_name VARCHAR(32) NOT NULL COMMENT '角色名',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        create_user VARCHAR(64) NOT NULL COMMENT '创建者',
        update_time DATETIME COMMENT '修改时间',
        update_user VARCHAR(64) COMMENT '修改人',
        super_admin_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '是否是管理员角色',
        PRIMARY KEY (id),
        CONSTRAINT role_id UNIQUE (role_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
DROP TABLE
    t_role_permission;
CREATE TABLE
    t_role_permission
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        role_id VARCHAR(32) NOT NULL COMMENT '角色id',
        permission_id VARCHAR(32) NOT NULL COMMENT '权限id',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';
DROP TABLE
    t_test;
CREATE TABLE
    t_test
    (
        id bigint NOT NULL AUTO_INCREMENT,
        value DECIMAL(16,4),
        PRIMARY KEY (id),
        CONSTRAINT t_test_ix1 UNIQUE (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE
    t_user;
CREATE TABLE
    t_user
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        sex CHAR(1) COMMENT '性别 0 男 1女',
        username VARCHAR(64) NOT NULL COMMENT '昵称',
        nickname VARCHAR(64) NOT NULL,
        password VARCHAR(64) NOT NULL COMMENT '密码',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        last_login_time DATETIME COMMENT '最近登陆时间',
        status CHAR(1) NOT NULL COMMENT '0-禁用，1-正常',
        telephone VARCHAR(11) COMMENT '手机号',
        update_time DATETIME COMMENT '修改时间',
        user_portrait text COMMENT '用户头像',
        update_user VARCHAR(64),
        PRIMARY KEY (id),
        CONSTRAINT telephone UNIQUE (telephone)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
DROP TABLE
    t_user_extension;
CREATE TABLE
    t_user_extension
    (
        id bigint unsigned NOT NULL AUTO_INCREMENT,
        user_id VARCHAR(64) NOT NULL COMMENT '用户id',
        user_brief VARCHAR(500) COMMENT '用户简介',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        update_time DATETIME COMMENT '修改时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户拓展表';
DROP TABLE
    t_user_role;
CREATE TABLE
    t_user_role
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        user_id VARCHAR(32) NOT NULL COMMENT '用户id',
        role_id VARCHAR(32) NOT NULL COMMENT '角色id',
        create_time DATETIME COMMENT '创建时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
