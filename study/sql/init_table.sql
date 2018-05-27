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
    t_article_check;
CREATE TABLE
    t_article_check
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        article_id VARCHAR(64) NOT NULL,
        title VARCHAR(64) NOT NULL,
        content text COMMENT '文章内容',
        complete_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '是否审核完成，0-否，1-是',
        check_type CHAR(1) NOT NULL COMMENT '审核类型，0-创建，1-修改',
        check_user_id VARCHAR(64) COMMENT '审核人id',
        check_username VARCHAR(64) COMMENT '审核人名',
        check_time DATETIME COMMENT '审核时间',
        create_time DATETIME NOT NULL COMMENT '创建时间',
        approve_flag VARCHAR(1) COMMENT '是否通过，0-否，1-是',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章审核表';
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
INSERT INTO t_user (id, user_id, sex, username, nickname, password, create_time, last_login_time, status, telephone, update_time, user_portrait, update_user) VALUES (1, '20180406823da5754261', '0', 'admin', '管理员', 'B8126D979040396255441D6133A8B3A28265BED4DF055A6525D52877', '2018-04-06 21:17:33', '2018-05-05 13:26:38', '1', null, '2018-05-27 10:14:55', null, null);
INSERT INTO t_user_role (id, user_id, role_id, create_time) VALUES (1, '20180406823da5754261', 'r10006', null);
INSERT INTO t_role (id, role_id, role_name, create_time, create_user, update_time, update_user, super_admin_flag) VALUES (7, 'r10006', '超级管理员', '2018-03-19 22:52:20', '', '2018-04-01 20:42:57', '20180107a049b606cacd', '1');
INSERT INTO t_role (id, role_id, role_name, create_time, create_user, update_time, update_user, super_admin_flag) VALUES (8, 'r1001308', '普通用户', '2018-04-01 20:46:46', '20180107a049b606cacd', null, null, '0');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (39, 'p10038', '基本设置', '/user/set', '2018-03-18 18:13:14', '0', 1, 6, '&#xe652;', '1', '', '2018-05-15 22:33:54', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (40, 'p10039', '我的文章', '/user/article/mine', '2018-03-18 18:13:35', '0', 1, 1, '&#xe705;', '1', '', '2018-04-21 20:48:47', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (41, 'p10040', '我的消息', '/user/message', '2018-03-18 18:13:52', '0', 1, 2, '&#xe756;', '0', '', '2018-04-21 20:54:10', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (42, 'p10041', '审核文章', '/user/check', '2018-03-18 18:14:09', '0', 1, 3, '&#xe69c;', '0', '', '2018-04-21 20:54:15', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (43, 'p10042', '发送消息', '/user/systemMessage', '2018-03-18 18:14:33', '0', 1, 4, '&#xe6fc;', '0', '', '2018-04-21 20:54:24', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (44, 'p10043', '权限管理', '', '2018-03-18 18:14:48', '0', 1, 5, '&#xe68e;', '0', '', '2018-04-21 20:54:30', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (45, 'p10044', '权限列表', '/user/permission', '2018-03-18 18:15:08', 'p10043', 1, 51, '&#xe600;', '0', '', '2018-04-21 20:54:37', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (46, 'p10045', '角色管理', '/user/role', '2018-03-18 18:15:51', 'p10043', 1, 52, '&#xe62e;', '1', '', '2018-04-21 21:00:36', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (48, 'p988454', '用户管理', '/user/manage', '2018-03-23 21:47:29', 'p10043', 1, 53, '&#xe634;', '0', '', '2018-04-21 21:01:07', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (49, 'p1001448', '文章编辑', '/user/article/doEdit', '2018-04-01 21:55:48', 'p10039', 2, 13, '&#xe705;', '2', '20180107a049b606cacd', '2018-04-21 20:49:04', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (50, 'p1002918', '新增权限', '/user/permission/doAdd', '2018-04-02 22:24:41', 'p10044', 2, 513, '&#xe68e;', '2', '20180107a049b606cacd', '2018-04-21 21:00:20', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (51, 'p1002949', '编辑权限', '/user/permission/doEdit', '2018-04-02 22:54:20', 'p10044', 2, 512, '&#xe68e;', '2', '20180107a049b606cacd', '2018-04-21 21:00:15', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (52, 'p1002959', '删除权限', '/user/permission/doDelete', '2018-04-02 23:03:25', 'p10044', 2, 511, '&#xe68e;', '2', '20180107a049b606cacd', '2018-04-21 21:00:08', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (53, 'p1007366', '发表新文章', '/user/article/doSave', '2018-04-06 00:30:33', 'p10039', 2, 11, '&#xe705;', '2', '20180107a049b606cacd', '2018-04-21 20:48:52', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (54, 'p1008656', '分配角色', '/user/role/allocateRole', '2018-04-06 21:59:38', 'p988454', 2, 532, '&#xe68e;', '2', '20180406823da5754261', '2018-04-21 21:01:22', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (55, 'p1008658', '分配权限', '/user/role/allocatePermission', '2018-04-06 22:00:39', 'p10045', 2, 521, '&#xe62e;', '2', '20180406823da5754261', '2018-04-21 21:00:43', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (56, 'p1008660', '编辑角色', '/user/role/doEdit', '2018-04-06 22:01:16', 'p10045', 2, 522, '&#xe68e;', '2', '20180406823da5754261', '2018-04-21 21:00:52', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (57, 'p1008661', '删除角色', '/user/role/doDelete', '2018-04-06 22:01:33', 'p10045', 2, 523, '&#xe68e;', '2', '20180406823da5754261', '2018-04-21 21:00:57', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (58, 'p1008682', '删除文章', '/user/article/doDelete', '2018-04-06 22:21:55', 'p10039', 2, 12, '&#xe68e;', '2', '20180406823da5754261', '2018-04-21 20:49:00', '20180406823da5754261');
INSERT INTO t_permission (id, permission_id, permission_name, url, create_time, pid, level, sort, icon, type, create_user, update_time, update_user) VALUES (59, 'p1008684', '修改用户状态', '/user/manage/changeStatus', '2018-04-06 22:23:07', 'p988454', 2, 531, '&#xe607;', '2', '20180406823da5754261', '2018-04-21 21:01:16', '20180406823da5754261');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (75, 'r10006', 'p10038', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (76, 'r10006', 'p10039', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (77, 'r10006', 'p10040', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (78, 'r10006', 'p10041', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (79, 'r10006', 'p10042', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (80, 'r10006', 'p10043', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (81, 'r10006', 'p10044', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (82, 'r10006', 'p10045', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (83, 'r10006', 'p988454', '2018-03-23 22:05:02');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (87, 'r10006', 'p1001448', '2018-04-01 21:55:48');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (90, 'r10006', 'p1002918', '2018-04-02 22:24:41');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (91, 'r10006', 'p1002949', '2018-04-02 22:54:20');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (92, 'r10006', 'p1002959', '2018-04-02 23:03:25');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (93, 'r10006', 'p1007366', '2018-04-06 00:30:34');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (94, 'r10006', 'p1008656', '2018-04-06 21:59:38');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (95, 'r10006', 'p1008658', '2018-04-06 22:00:39');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (96, 'r10006', 'p1008660', '2018-04-06 22:01:16');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (97, 'r10006', 'p1008661', '2018-04-06 22:01:33');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (107, 'r10006', 'p1008682', '2018-04-06 22:21:55');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (108, 'r10006', 'p1008684', '2018-04-06 22:23:07');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (187, 'r10006', 'p1030189', '2018-04-21 20:47:07');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (188, 'r10006', 'p1030191', '2018-04-21 20:47:41');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (189, 'r10006', 'p1030197', '2018-04-21 20:52:18');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (190, 'r1001308', 'p10039', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (191, 'r1001308', 'p1007366', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (192, 'r1001308', 'p1008682', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (193, 'r1001308', 'p1001448', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (194, 'r1001308', 'p10040', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (195, 'r1001308', 'p10041', '2018-05-13 19:56:44');
INSERT INTO t_role_permission (id, role_id, permission_id, create_time) VALUES (196, 'r1001308', 'p10038', '2018-05-13 19:56:44');
