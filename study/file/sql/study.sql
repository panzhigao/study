/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-02-26 22:44:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_article`
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` varchar(64) NOT NULL COMMENT '文章id',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `status` char(1) NOT NULL COMMENT '0-审核未通过，1-草稿，2-审核中，3-发布成功',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `title` varchar(64) NOT NULL COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `outline` varchar(300) DEFAULT NULL COMMENT '文章概要',
  `image` varchar(100) DEFAULT NULL COMMENT '缩略图',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) DEFAULT '0' COMMENT '阅读数',
  `type` char(1) DEFAULT '1' COMMENT '1-文章 2系统消息',
  `top` int(11) NOT NULL DEFAULT '0' COMMENT '置顶系数',
  `high_quality` char(1) NOT NULL DEFAULT '0' COMMENT '是否是精品贴，0-否，1-是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_article_id` (`article_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES ('1', 'a1463590', '20180406823da5754261', '3', '2019-02-16 21:28:44', '2019-02-26 22:33:00', '2019-02-16 21:40:50', '666', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190216212838042.jpeg\" alt=\"undefined\"&gt;', null, null, '5', '39', '1', '1', '1');

-- ----------------------------
-- Table structure for `t_article_check`
-- ----------------------------
DROP TABLE IF EXISTS `t_article_check`;
CREATE TABLE `t_article_check` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `article_id` varchar(64) NOT NULL DEFAULT '' COMMENT '文章id',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `complete_flag` char(1) NOT NULL DEFAULT '0' COMMENT '是否审核完成，0-否，1-是',
  `check_type` char(1) NOT NULL COMMENT '审核类型，0-创建，1-修改',
  `check_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '审核人id',
  `check_username` varchar(64) NOT NULL DEFAULT '' COMMENT '审核人名',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `approve_flag` varchar(1) DEFAULT NULL COMMENT '是否通过，0-否，1-是',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章审核表';

-- ----------------------------
-- Records of t_article_check
-- ----------------------------
INSERT INTO `t_article_check` VALUES ('1', '20180406823da5754261', 'a1463590', '666', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190216212838042.jpeg\" alt=\"undefined\"&gt;', '1', '0', '20180406823da5754261', 'admin', '2019-02-16 21:40:50', '2019-02-16 21:28:44', '1');

-- ----------------------------
-- Table structure for `t_collection`
-- ----------------------------
DROP TABLE IF EXISTS `t_collection`;
CREATE TABLE `t_collection` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `collection_id` varchar(64) NOT NULL COMMENT '收藏id',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `article_id` varchar(64) NOT NULL COMMENT '文章id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_article_id` (`user_id`,`article_id`) USING BTREE,
  UNIQUE KEY `uq_collection_id` (`collection_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of t_collection
-- ----------------------------
INSERT INTO `t_collection` VALUES ('1', 'collect1463599', '20180406823da5754261', 'a1463590', '2019-02-16 21:43:08', '666');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` varchar(64) NOT NULL COMMENT '评论id',
  `user_id` varchar(64) NOT NULL COMMENT '评论者id',
  `article_id` varchar(64) NOT NULL COMMENT '文章id',
  `comment_content` varchar(300) NOT NULL COMMENT '评论内容',
  `reply_to_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '接收评论者的userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `praise_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_comment_id` (`comment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='评论信息';

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_login_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_login_history`;
CREATE TABLE `t_login_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间，即登录时间',
  `ip` int(10) unsigned zerofill NOT NULL COMMENT '登录时的ip',
  `user_agent` varchar(2048) NOT NULL DEFAULT '' COMMENT '用户代理',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_username` (`user_id`,`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_login_history
-- ----------------------------
INSERT INTO `t_login_history` VALUES ('1', '20180406823da5754261', 'admin', '2019-02-26 22:00:38', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36');

-- ----------------------------
-- Table structure for `t_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `message_id` varchar(64) NOT NULL COMMENT '消息id',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0-未读 1-已读',
  `receiver_user_id` varchar(64) NOT NULL COMMENT '接收者id',
  `sender_name` varchar(64) NOT NULL DEFAULT '' COMMENT '发送者姓名',
  `sender_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '发送消息者id',
  `message_type` char(1) NOT NULL COMMENT '消息类别 1-评论 2-系统消息 3-公告',
  `content_id` varchar(64) NOT NULL DEFAULT '' COMMENT '内容id',
  `content_name` varchar(64) NOT NULL DEFAULT '' COMMENT '内容名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `comment_content` varchar(300) NOT NULL DEFAULT '' COMMENT '评论内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息通知表';

-- ----------------------------
-- Records of t_message
-- ----------------------------

-- ----------------------------
-- Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_id` varchar(64) NOT NULL COMMENT '权限id',
  `permission_name` varchar(64) NOT NULL COMMENT '权限名',
  `url` varchar(100) NOT NULL COMMENT 'url路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pid` varchar(64) NOT NULL DEFAULT '0' COMMENT '父级id',
  `level` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '层级数',
  `sort` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `icon` varchar(64) NOT NULL DEFAULT '' COMMENT '图标',
  `type` char(1) NOT NULL COMMENT '类型 0-菜单 1-链接',
  `create_user` varchar(64) NOT NULL COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('39', 'p10038', '基本设置', '/user/set', '2018-03-18 18:13:14', '0', '1', '6', 'layui-icon-username', '1', '', '2019-02-17 12:15:15', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('40', 'p10039', '我的文章', '/user/article/mine', '2018-03-18 18:13:35', '0', '1', '1', 'layui-icon-read', '1', '', '2019-02-17 12:12:35', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('41', 'p10040', '我的消息', '/user/message', '2018-03-18 18:13:52', '0', '1', '2', 'layui-icon-dialogue', '1', '', '2019-02-17 12:16:37', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('42', 'p10041', '审核文章', '/user/check', '2018-03-18 18:14:09', '0', '1', '3', 'layui-icon-survey', '1', '', '2019-02-17 12:16:58', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('43', 'p10042', '发送消息', '/user/systemMessage', '2018-03-18 18:14:33', '0', '1', '4', 'layui-icon-release', '1', '', '2019-02-17 12:16:11', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('44', 'p10043', '权限管理', '  ', '2018-03-18 18:14:48', '0', '1', '15', 'layui-icon-app', '0', '', '2019-02-17 12:34:09', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('45', 'p10044', '权限列表', '/user/permission', '2018-03-18 18:15:08', 'p10043', '1', '51', 'layui-icon-spread-left', '1', '', '2019-02-17 12:14:17', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('46', 'p10045', '角色管理', '/user/role', '2018-03-18 18:15:51', 'p10043', '1', '52', 'layui-icon-face-smile', '1', '', '2019-02-17 12:14:45', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('48', 'p988454', '用户管理', '/user/manage', '2018-03-23 21:47:29', 'p10043', '1', '53', 'layui-icon-group', '1', '', '2019-02-17 12:49:16', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('49', 'p1001448', '文章编辑', '/user/article/doEdit', '2018-04-01 21:55:48', 'p10039', '2', '13', 'layui-icon-fonts-clear', '2', '20180107a049b606cacd', '2019-02-26 22:15:27', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('50', 'p1002918', '新增权限', '/user/permission/doAdd', '2018-04-02 22:24:41', 'p10044', '2', '513', 'layui-icon-app', '2', '20180107a049b606cacd', '2019-02-26 22:17:25', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('51', 'p1002949', '编辑权限', '/user/permission/doEdit', '2018-04-02 22:54:20', 'p10044', '2', '512', 'layui-icon-link', '2', '20180107a049b606cacd', '2019-02-26 22:17:16', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('52', 'p1002959', '删除权限', '/user/permission/doDelete', '2018-04-02 23:03:25', 'p10044', '2', '511', 'layui-icon-tips', '2', '20180107a049b606cacd', '2019-02-26 22:17:01', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('53', 'p1007366', '发表新文章', '/user/article/doSave', '2018-04-06 00:30:33', 'p10039', '2', '11', 'layui-icon-auz', '2', '20180107a049b606cacd', '2019-02-17 12:13:26', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('54', 'p1008656', '分配角色', '/user/role/allocateRole', '2018-04-06 21:59:38', 'p988454', '2', '532', 'layui-icon-share', '2', '20180406823da5754261', '2019-02-26 22:16:26', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('55', 'p1008658', '分配权限', '/user/role/allocatePermission', '2018-04-06 22:00:39', 'p10045', '2', '521', 'layui-icon-list', '2', '20180406823da5754261', '2019-02-26 22:17:46', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('56', 'p1008660', '编辑角色', '/user/role/doEdit', '2018-04-06 22:01:16', 'p10045', '2', '522', 'layui-icon-templeate-1', '2', '20180406823da5754261', '2019-02-26 22:18:18', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('57', 'p1008661', '删除角色', '/user/role/doDelete', '2018-04-06 22:01:33', 'p10045', '2', '523', 'layui-icon-delete', '2', '20180406823da5754261', '2019-02-26 22:18:35', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('58', 'p1008682', '删除文章', '/user/article/doDelete', '2018-04-06 22:21:55', 'p10039', '2', '12', 'layui-icon-close', '2', '20180406823da5754261', '2019-02-26 22:14:30', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('59', 'p1008684', '修改用户状态', '/user/manage/changeStatus', '2018-04-06 22:23:07', 'p988454', '2', '531', 'layui-icon-note', '2', '20180406823da5754261', '2019-02-26 22:15:54', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('60', 'p1107289', '置顶/加精', '/user/article/set', '2018-06-14 11:12:29', '0', '1', '1', 'layui-icon-rate-solid', '2', '20180406823da5754261', '2019-02-26 22:15:35', '20180406823da5754261');
INSERT INTO `t_permission` VALUES ('62', 'p1464486', '积分历史', '/user/scoreHistory', '2019-02-17 12:29:50', '0', '1', '1', 'layui-icon-dollar', '1', '20180406823da5754261', null, '');
INSERT INTO `t_permission` VALUES ('63', 'p1464491', '我的图片', '/user/myPicturePage', '2019-02-17 12:33:38', '0', '1', '1', 'layui-icon-picture', '1', '20180406823da5754261', null, '');

-- ----------------------------
-- Table structure for `t_picture`
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `picture_id` varchar(64) NOT NULL DEFAULT '' COMMENT '图片id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `picture_url` varchar(200) NOT NULL DEFAULT '' COMMENT '图片访问路径',
  `picture_path` varchar(200) NOT NULL DEFAULT '' COMMENT '图片保存路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `picture_id` (`picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片信息表';

-- ----------------------------
-- Records of t_picture
-- ----------------------------

-- ----------------------------
-- Table structure for `t_praise`
-- ----------------------------
DROP TABLE IF EXISTS `t_praise`;
CREATE TABLE `t_praise` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `praise_id` varchar(64) NOT NULL DEFAULT '' COMMENT '点赞id',
  `comment_id` varchar(64) NOT NULL DEFAULT '' COMMENT '评论id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of t_praise
-- ----------------------------
INSERT INTO `t_praise` VALUES ('1', 'z1463615', 'c1463618', '20180406823da5754261', '2019-02-16 21:58:32');
INSERT INTO `t_praise` VALUES ('2', 'z1463616', 'c1463616', '20180406823da5754261', '2019-02-16 21:58:44');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `super_admin_flag` char(1) NOT NULL DEFAULT '0' COMMENT '是否是管理员角色，0-否，1-是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('7', 'r10006', '超级管理员', '2018-03-19 22:52:20', '', '2018-04-01 20:42:57', '20180107a049b606cacd', '1');
INSERT INTO `t_role` VALUES ('8', 'r1001308', '普通用户', '2018-04-01 20:46:46', '20180107a049b606cacd', '2018-04-01 20:42:57', '20180107a049b606cacd', '0');

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色id',
  `permission_id` varchar(32) NOT NULL DEFAULT '' COMMENT '权限id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('75', 'r10006', 'p10038', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('76', 'r10006', 'p10039', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('77', 'r10006', 'p10040', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('78', 'r10006', 'p10041', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('79', 'r10006', 'p10042', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('80', 'r10006', 'p10043', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('81', 'r10006', 'p10044', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('82', 'r10006', 'p10045', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('83', 'r10006', 'p988454', '2018-03-23 22:05:02');
INSERT INTO `t_role_permission` VALUES ('87', 'r10006', 'p1001448', '2018-04-01 21:55:48');
INSERT INTO `t_role_permission` VALUES ('90', 'r10006', 'p1002918', '2018-04-02 22:24:41');
INSERT INTO `t_role_permission` VALUES ('91', 'r10006', 'p1002949', '2018-04-02 22:54:20');
INSERT INTO `t_role_permission` VALUES ('92', 'r10006', 'p1002959', '2018-04-02 23:03:25');
INSERT INTO `t_role_permission` VALUES ('93', 'r10006', 'p1007366', '2018-04-06 00:30:34');
INSERT INTO `t_role_permission` VALUES ('94', 'r10006', 'p1008656', '2018-04-06 21:59:38');
INSERT INTO `t_role_permission` VALUES ('95', 'r10006', 'p1008658', '2018-04-06 22:00:39');
INSERT INTO `t_role_permission` VALUES ('96', 'r10006', 'p1008660', '2018-04-06 22:01:16');
INSERT INTO `t_role_permission` VALUES ('97', 'r10006', 'p1008661', '2018-04-06 22:01:33');
INSERT INTO `t_role_permission` VALUES ('107', 'r10006', 'p1008682', '2018-04-06 22:21:55');
INSERT INTO `t_role_permission` VALUES ('108', 'r10006', 'p1008684', '2018-04-06 22:23:07');
INSERT INTO `t_role_permission` VALUES ('187', 'r10006', 'p1030189', '2018-04-21 20:47:07');
INSERT INTO `t_role_permission` VALUES ('188', 'r10006', 'p1030191', '2018-04-21 20:47:41');
INSERT INTO `t_role_permission` VALUES ('189', 'r10006', 'p1030197', '2018-04-21 20:52:18');
INSERT INTO `t_role_permission` VALUES ('190', 'r1001308', 'p10039', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('191', 'r1001308', 'p1007366', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('192', 'r1001308', 'p1008682', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('193', 'r1001308', 'p1001448', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('194', 'r1001308', 'p10040', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('195', 'r1001308', 'p10041', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('196', 'r1001308', 'p10038', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('197', 'r10006', 'p1107289', '2018-06-14 11:12:29');
INSERT INTO `t_role_permission` VALUES ('198', 'r1001308', 'p1140342', '2018-05-13 19:56:44');
INSERT INTO `t_role_permission` VALUES ('199', 'r10006', 'p1464486', '2019-02-17 12:29:50');
INSERT INTO `t_role_permission` VALUES ('200', 'r10006', 'p1464491', '2019-02-17 12:33:38');

-- ----------------------------
-- Table structure for `t_score_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_score_history`;
CREATE TABLE `t_score_history` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `type` char(1) NOT NULL COMMENT '积分类型，1-登陆，2-发表文章成功，3-回帖,4-签到.5-注册',
  `type_name` varchar(64) NOT NULL DEFAULT '' COMMENT '类型名',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `score_date` date NOT NULL COMMENT '积分获取日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idex_user_id_type_score_date` (`user_id`,`type`,`score_date`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='积分历史表';

-- ----------------------------
-- Records of t_score_history
-- ----------------------------
INSERT INTO `t_score_history` VALUES ('10', '20180406823da5754261', '1', '登陆', '5', '2019-02-16', '2019-02-16 21:27:02');
INSERT INTO `t_score_history` VALUES ('11', '20180406823da5754261', '2', '发表文章成功', '5', '2019-02-16', '2019-02-16 21:40:50');
INSERT INTO `t_score_history` VALUES ('12', '20180406823da5754261', '3', '回帖', '2', '2019-02-16', '2019-02-16 21:41:06');
INSERT INTO `t_score_history` VALUES ('13', '20180406823da5754261', '4', '签到', '5', '2019-02-16', '2019-02-16 21:43:29');
INSERT INTO `t_score_history` VALUES ('14', '20180406823da5754261', '3', '回帖', '2', '2019-02-16', '2019-02-16 21:57:55');
INSERT INTO `t_score_history` VALUES ('15', '20180406823da5754261', '3', '回帖', '2', '2019-02-16', '2019-02-16 21:58:07');
INSERT INTO `t_score_history` VALUES ('16', '20180406823da5754261', '3', '回帖', '2', '2019-02-16', '2019-02-16 21:58:25');
INSERT INTO `t_score_history` VALUES ('17', '2019021636efdaf047a2', '5', '注册', '20', '2019-02-16', '2019-02-16 23:30:00');
INSERT INTO `t_score_history` VALUES ('18', '2019021636efdaf047a2', '1', '登陆', '5', '2019-02-16', '2019-02-16 23:30:00');
INSERT INTO `t_score_history` VALUES ('19', '20190216f9b22528ae14', '5', '注册', '20', '2019-02-16', '2019-02-16 23:34:54');
INSERT INTO `t_score_history` VALUES ('20', '20190216f9b22528ae14', '1', '登陆', '5', '2019-02-16', '2019-02-16 23:34:54');
INSERT INTO `t_score_history` VALUES ('21', '20180406823da5754261', '1', '登陆', '5', '2019-02-17', '2019-02-17 12:08:30');
INSERT INTO `t_score_history` VALUES ('22', '20190217f4a6ff0d98ab', '5', '注册', '20', '2019-02-17', '2019-02-17 12:11:03');
INSERT INTO `t_score_history` VALUES ('23', '20190217f4a6ff0d98ab', '1', '登陆', '5', '2019-02-17', '2019-02-17 12:11:03');
INSERT INTO `t_score_history` VALUES ('24', '20180406823da5754261', '4', '签到', '5', '2019-02-17', '2019-02-17 14:21:57');
INSERT INTO `t_score_history` VALUES ('25', '20180406823da5754261', '3', '回帖', '2', '2019-02-17', '2019-02-17 14:23:07');
INSERT INTO `t_score_history` VALUES ('26', '20180406823da5754261', '1', '登陆', '5', '2019-02-26', '2019-02-26 22:00:38');
INSERT INTO `t_score_history` VALUES ('27', '20180406823da5754261', '4', '签到', '5', '2019-02-26', '2019-02-26 22:29:20');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别 0-未知，1-男，2-女',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登陆时间',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '0-禁用，1-正常',
  `telephone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_portrait` varchar(256) NOT NULL DEFAULT '' COMMENT '用户头像',
  `update_user` varchar(64) NOT NULL DEFAULT '',
  `admin_flag` char(1) NOT NULL DEFAULT '0' COMMENT '管理员标志，0-否，1-是',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`) USING BTREE,
  UNIQUE KEY `idx_telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '20180406823da5754261', '2', 'admin', 'admin', 'B8126D979040396255441D6133A8B3A28265BED4DF055A6525D52877', '2018-04-06 21:17:33', '2019-02-26 22:00:38', '1', '18911536627', '2019-02-17 22:03:01', 'http://www.panzhigao.vip/myimage/20190217134706005.jpg', '', '0', '北京');

-- ----------------------------
-- Table structure for `t_user_extension`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_extension`;
CREATE TABLE `t_user_extension` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `user_portrait` varchar(256) NOT NULL DEFAULT '' COMMENT '用户头像',
  `user_brief` varchar(500) NOT NULL DEFAULT '' COMMENT '用户简介',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `article_counts` int(11) NOT NULL DEFAULT '0' COMMENT '文章数',
  `comment_counts` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `continuous_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续登陆天数',
  `continuous_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='用户拓展表';

-- ----------------------------
-- Records of t_user_extension
-- ----------------------------
INSERT INTO `t_user_extension` VALUES ('10', '20180406823da5754261', 'admin', 'http://www.panzhigao.vip/myimage/20190217134706005.jpg', '今天下雨了', '2018-04-06 21:27:04', '2019-02-26 22:29:20', '1', '5', '45', '3', '3');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '20180406823da5754261', 'r10006', '2019-02-16 23:30:00','20180406823da5754261');
