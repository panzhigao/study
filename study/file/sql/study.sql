/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-05-26 18:20:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_article`
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `status` int(1) NOT NULL COMMENT '0-审核未通过，1-草稿，2-审核中，3-发布成功，4-下线',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `title` varchar(128) NOT NULL COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `outline` varchar(300) NOT NULL DEFAULT '' COMMENT '文章概要',
  `image` varchar(100) NOT NULL DEFAULT '' COMMENT '缩略图',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) DEFAULT '0' COMMENT '阅读数',
  `type` int(1) DEFAULT '1' COMMENT '1-文章 2系统公告',
  `top` int(11) NOT NULL DEFAULT '0' COMMENT '置顶系数,0-未置顶，1-置顶',
  `high_quality` int(1) NOT NULL DEFAULT '0' COMMENT '是否是精品贴，0-否，1-是',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章分类',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of t_article
-- ----------------------------

-- ----------------------------
-- Table structure for `t_article_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category`;
CREATE TABLE `t_article_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(32) NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态，0-首页不展示，1-首页展示',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='文章分类表';

-- ----------------------------
-- Records of t_article_category
-- ----------------------------
INSERT INTO `t_article_category` VALUES ('1', '国际', '2', '0', '2019-04-07 11:05:29', '1523899', '2019-05-17 23:11:29', '1523899');
INSERT INTO `t_article_category` VALUES ('4', '财经', '100', '1', '2019-04-10 14:39:05', '1523899', '2019-04-10 14:39:08', '1523899');
INSERT INTO `t_article_category` VALUES ('5', '体育', '0', '1', '2019-04-10 14:39:29', '1523899', '2019-04-27 21:30:54', '1523899');
INSERT INTO `t_article_category` VALUES ('6', '科技', '1', '1', '2019-04-10 14:39:45', '1523899', '2019-04-10 14:39:49', '1523899');
INSERT INTO `t_article_category` VALUES ('7', '游戏', '1', '1', '2019-04-12 17:16:28', '1523899', '2019-04-16 19:06:58', '1523899');
INSERT INTO `t_article_category` VALUES ('8', '教育', '18', '1', '2019-04-12 17:24:31', '1523899', '2019-04-12 17:24:35', '1523899');
INSERT INTO `t_article_category` VALUES ('9', '图片', '1', '1', '2019-04-20 16:32:33', '1523899', '2019-04-20 16:32:37', '1523899');
INSERT INTO `t_article_category` VALUES ('10', '娱乐', '0', '1', '2019-04-27 21:26:45', '1523899', '2019-04-27 21:26:55', '1523899');
INSERT INTO `t_article_category` VALUES ('11', '视频', '12', '0', '2019-05-02 20:32:26', '1523899', '2019-05-14 00:01:46', '1523899');
INSERT INTO `t_article_category` VALUES ('12', '社会', '15', '0', '2019-05-04 22:44:20', '1523899', null, '0');
INSERT INTO `t_article_category` VALUES ('13', '历史', '1', '1', '2019-05-12 15:51:51', '1523899', '2019-05-12 17:28:15', '1523899');
INSERT INTO `t_article_category` VALUES ('14', '动漫', '14', '0', '2019-05-12 15:57:52', '1523899', null, '0');
INSERT INTO `t_article_category` VALUES ('15', '公告', '1', '0', '2019-05-18 22:57:31', '1523899', null, '0');

-- ----------------------------
-- Table structure for `t_article_check`
-- ----------------------------
DROP TABLE IF EXISTS `t_article_check`;
CREATE TABLE `t_article_check` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章id',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章内容',
  `complete_flag` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核完成，0-否，1-是',
  `check_type` int(1) NOT NULL COMMENT '审核类型，0-创建，1-修改',
  `check_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '审核人id',
  `check_username` varchar(64) NOT NULL DEFAULT '' COMMENT '审核人名',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `approve_flag` int(1) DEFAULT NULL COMMENT '是否通过，0-否，1-是',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '审核备注',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章分类',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COMMENT='文章审核表';

-- ----------------------------
-- Records of t_article_check
-- ----------------------------

-- ----------------------------
-- Table structure for `t_collection`
-- ----------------------------
DROP TABLE IF EXISTS `t_collection`;
CREATE TABLE `t_collection` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_article_id` (`user_id`,`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of t_collection
-- ----------------------------

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论者id',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章id',
  `comment_content` varchar(300) NOT NULL COMMENT '评论内容',
  `reply_to_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '接收评论者的userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `praise_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论信息';

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_exception_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_exception_log`;
CREATE TABLE `t_exception_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `ip` int(11) NOT NULL DEFAULT '0' COMMENT '远程访问主机IP',
  `class_name` varchar(255) NOT NULL DEFAULT '' COMMENT '类名',
  `method_name` varchar(128) NOT NULL DEFAULT '' COMMENT '方法名',
  `exception_type` varchar(255) NOT NULL DEFAULT '' COMMENT '异常类型',
  `exception_msg` text NOT NULL COMMENT '异常信息',
  `is_view` int(2) NOT NULL DEFAULT '0' COMMENT '是否查看，0：未查看、1：已查看',
  `create_time` datetime NOT NULL COMMENT '异常发生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='异常信息日志表';

-- ----------------------------
-- Records of t_exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_link`
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `link_name` varchar(128) NOT NULL DEFAULT '' COMMENT '链接名称',
  `link_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '链接地址',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态,0-未启用，1-已启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='链接表';

-- ----------------------------
-- Records of t_link
-- ----------------------------
INSERT INTO `t_link` VALUES ('14', '百度', 'http://www.baidu.com', '1', '1', '2019-03-23 22:29:43', '1523899', '2019-03-23 22:30:41', '1523899');
INSERT INTO `t_link` VALUES ('15', '腾讯', 'http://www.qq.com', '2', '1', '2019-03-23 22:30:04', '1523899', '2019-04-20 16:17:45', '1523899');
INSERT INTO `t_link` VALUES ('16', '新浪', 'http://www.sina.com', '3', '1', '2019-03-23 22:30:36', '1523899', '2019-04-20 16:25:23', '1523899');
INSERT INTO `t_link` VALUES ('17', '今日头条', 'https://www.toutiao.com/', '7', '1', '2019-04-20 16:28:47', '1523899', '2019-05-09 17:23:33', '1523899');

-- ----------------------------
-- Table structure for `t_login_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_login_history`;
CREATE TABLE `t_login_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间，即登录时间',
  `ip` int(10) unsigned zerofill NOT NULL COMMENT '登录时的ip',
  `user_agent` varchar(2048) NOT NULL DEFAULT '' COMMENT '用户代理',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_username` (`user_id`,`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_login_history
-- ----------------------------

-- ----------------------------
-- Table structure for `t_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0-未读 1-已读',
  `receiver_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '接收者id',
  `sender_name` varchar(64) NOT NULL DEFAULT '' COMMENT '发送者姓名',
  `sender_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '发送消息者id',
  `message_type` int(1) NOT NULL COMMENT '消息类别 1-评论 2-系统消息 3-公告',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '内容id',
  `comment_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论id',
  `content_name` varchar(64) NOT NULL DEFAULT '' COMMENT '内容名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `comment_content` varchar(300) NOT NULL DEFAULT '' COMMENT '评论内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8 COMMENT='消息通知表';

-- ----------------------------
-- Records of t_message
-- ----------------------------

-- ----------------------------
-- Table structure for `t_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_log`;
CREATE TABLE `t_operate_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `content` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作内容',
  `operate_type` int(11) NOT NULL DEFAULT '0' COMMENT '操作类型',
  `ip` int(11) NOT NULL DEFAULT '0' COMMENT 'ip',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=434 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of t_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(64) NOT NULL DEFAULT '' COMMENT '权限名',
  `permission_point` varchar(64) NOT NULL DEFAULT '' COMMENT '权限点',
  `url` varchar(100) NOT NULL COMMENT 'url路径',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id',
  `level` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '层级数',
  `sort` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `icon` varchar(64) NOT NULL DEFAULT '' COMMENT '图标',
  `type` int(1) NOT NULL COMMENT '类型 0-菜单 1-链接 2-按钮',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '权限管理', 'permission:manage', '  ', '0', '1', '100', 'layui-icon-app', '0', '2019-03-30 19:52:14', '1523899', '2019-05-19 17:46:00', '1523899');
INSERT INTO `t_permission` VALUES ('2', '权限列表', 'permission:load', '/user/permission', '1', '2', '50', 'layui-icon-spread-left', '1', '2019-03-30 19:53:13', '1523899', '2019-05-19 17:46:11', '1523899');
INSERT INTO `t_permission` VALUES ('3', '新增权限', 'permission:doAdd', '/user/permission/doAdd', '2', '3', '100', 'layui-icon-add-1', '2', '2019-03-30 20:56:02', '1523899', '2019-05-19 18:00:37', '1523899');
INSERT INTO `t_permission` VALUES ('4', '编辑权限', 'permission:doEdit', '/user/permission/doEdit', '2', '2', '100', 'layui-icon-note', '2', '2019-03-30 21:18:54', '1523899', '2019-05-19 17:46:49', '1523899');
INSERT INTO `t_permission` VALUES ('5', '权限删除', 'permission:doDelete', '/user/permission/doDelete', '2', '2', '100', 'layui-icon-delete', '2', '2019-03-30 21:31:42', '1523899', '2019-05-19 17:46:38', '1523899');
INSERT INTO `t_permission` VALUES ('6', '基本设置', 'user:set', '/user/set', '0', '1', '2', 'layui-icon-rate-half', '1', '2019-03-30 21:35:11', '1523899', '2019-05-19 17:37:58', '1523899');
INSERT INTO `t_permission` VALUES ('7', '我的文章', 'article:mine', '/user/article/mine', '0', '1', '3', 'layui-icon-template-1', '1', '2019-03-30 21:38:25', '1523899', '2019-05-19 17:38:30', '1523899');
INSERT INTO `t_permission` VALUES ('8', '我的消息', 'message:load', '/user/message', '0', '1', '4', 'layui-icon-login-wechat', '1', '2019-03-30 21:52:39', '1523899', '2019-05-19 17:39:49', '1523899');
INSERT INTO `t_permission` VALUES ('9', '系统公告', 'systemNotice:load', '/user/systemNotice', '0', '1', '5', 'layui-icon-star-fill', '1', '2019-03-30 21:53:41', '1523899', '2019-05-19 17:41:27', '1523899');
INSERT INTO `t_permission` VALUES ('10', '审核文章', 'articleCheck:load', '/user/articleCheck', '0', '1', '6', 'layui-icon-face-surprised', '1', '2019-03-30 21:54:08', '1523899', '2019-05-19 17:41:52', '1523899');
INSERT INTO `t_permission` VALUES ('11', '文章编辑', 'article:doEdit', '/user/article/doEdit', '7', '2', '100', 'layui-icon-edit', '2', '2019-03-30 21:54:59', '1523899', '2019-05-19 18:04:13', '1523899');
INSERT INTO `t_permission` VALUES ('12', '发表新文章', 'article:doSave', '/user/article/doSave', '7', '2', '100', 'layui-icon-add-1', '2', '2019-03-30 21:55:44', '1523899', '2019-05-19 18:00:23', '1523899');
INSERT INTO `t_permission` VALUES ('13', '角色管理', 'role:load', '/user/role', '1', '2', '100', 'layui-icon-group', '1', '2019-03-30 21:57:25', '1523899', '2019-05-19 17:47:11', '1523899');
INSERT INTO `t_permission` VALUES ('14', '分配角色', 'role:allocateRole', '/user/role/allocateRole', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:57:53', '1523899', '2019-05-19 17:53:22', '1523899');
INSERT INTO `t_permission` VALUES ('15', '分配权限', 'role:allocatePermission', '/user/role/allocatePermission', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:13', '1523899', '2019-05-19 17:47:45', '1523899');
INSERT INTO `t_permission` VALUES ('16', '编辑角色', 'role:doEdit', '/user/role/doEdit', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:37', '1523899', '2019-05-19 17:53:34', '1523899');
INSERT INTO `t_permission` VALUES ('17', '删除角色', 'role:doDelete', '/user/role/doDelete', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:57', '1523899', '2019-05-19 17:53:50', '1523899');
INSERT INTO `t_permission` VALUES ('18', '删除文章', 'article:doDelete', '/user/article/doDelete', '7', '2', '100', 'layui-icon-delete', '2', '2019-03-30 21:59:23', '1523899', '2019-05-19 17:39:12', '1523899');
INSERT INTO `t_permission` VALUES ('19', '文章置顶/加精', 'article:set', '/user/article/set', '0', '1', '200', 'layui-icon-rate', '2', '2019-03-30 22:00:40', '1523899', '2019-05-19 17:54:10', '1523899');
INSERT INTO `t_permission` VALUES ('20', '积分历史', 'scoreHistory:load', '/user/scoreHistory', '0', '1', '9', 'layui-icon-star-fill', '1', '2019-03-30 22:01:07', '1523899', '2019-05-19 17:42:00', '1523899');
INSERT INTO `t_permission` VALUES ('21', '我的图片', 'myPicturePage:load', '/user/myPicturePage', '0', '1', '10', 'layui-icon-picture', '1', '2019-03-30 22:01:29', '1523899', '2019-05-19 17:42:58', '1523899');
INSERT INTO `t_permission` VALUES ('22', '系统管理', 'system:manage', '  ', '0', '1', '300', 'layui-icon-file', '0', '2019-03-30 22:01:51', '1523899', '2019-05-19 17:56:00', '1523899');
INSERT INTO `t_permission` VALUES ('23', '操作日志', 'operateLog:load', '/user/operateLog', '22', '2', '301', 'layui-icon-star-fill', '1', '2019-03-30 22:02:20', '1523899', '2019-05-19 17:55:07', '1523899');
INSERT INTO `t_permission` VALUES ('24', '系统配置', 'systemConfig:load', '/user/systemConfig', '22', '2', '100', 'layui-icon-fire', '1', '2019-03-30 22:02:40', '1523899', '2019-05-19 17:58:43', '1523899');
INSERT INTO `t_permission` VALUES ('25', '链接管理', 'link:load', '/user/link/index', '22', '2', '300', 'layui-icon-next', '1', '2019-03-30 22:03:26', '1523899', '2019-05-19 18:01:02', '1523899');
INSERT INTO `t_permission` VALUES ('26', '新增链接', 'link:doAdd', '/user/link/doAdd', '25', '2', '100', 'layui-icon-add-1', '2', '2019-03-30 22:03:47', '1523899', '2019-05-19 18:02:10', '1523899');
INSERT INTO `t_permission` VALUES ('27', '编辑链接', 'link:doEdit', '/user/link/doEdit', '25', '2', '100', 'layui-icon-edit', '2', '2019-03-30 22:04:07', '1523899', '2019-05-19 18:01:54', '1523899');
INSERT INTO `t_permission` VALUES ('28', '删除链接', 'link:doDelete', '/user/link/doDelete', '25', '2', '100', 'layui-icon-delete', '2', '2019-03-30 22:04:26', '1523899', '2019-05-19 18:01:13', '1523899');
INSERT INTO `t_permission` VALUES ('29', '修改链接状态', 'link:changeStatus', '/user/link/changeStatus', '25', '2', '303', 'layui-icon-edit', '2', '2019-03-30 22:05:12', '1523899', '2019-05-19 18:03:45', '1523899');
INSERT INTO `t_permission` VALUES ('30', '用户管理', 'user:manage', '/user/manage', '0', '1', '11', 'layui-icon-username', '1', '2019-03-30 22:06:38', '1523899', '2019-05-19 17:43:12', '1523899');
INSERT INTO `t_permission` VALUES ('31', '修改用户状态', 'user:manage:changeStatus', '/user/manage/changeStatus', '30', '2', '401', 'layui-icon-star-fill', '2', '2019-03-30 22:07:17', '1523899', '2019-05-19 17:44:01', '1523899');
INSERT INTO `t_permission` VALUES ('32', '新增角色', 'role:doAdd', '/user/role/doAdd', '13', '2', '100', 'layui-icon-ok', '2', '2019-03-31 00:58:16', '1523899', '2019-05-19 17:59:25', '1523899');
INSERT INTO `t_permission` VALUES ('33', '分配角色', 'user:role:allocateRole', '/user/role/allocateRole', '30', '2', '100', 'layui-icon-star-fill', '2', '2019-03-31 12:31:44', '1523899', '2019-05-19 17:43:26', '1523899');
INSERT INTO `t_permission` VALUES ('34', '文章分类', 'articleCategory:load', '/user/articleCategory', '22', '2', '500', 'layui-icon-menu-fill', '1', '2019-04-07 10:47:02', '1523899', '2019-05-19 17:54:59', '1523899');
INSERT INTO `t_permission` VALUES ('35', '新增文章分类', 'articleCategory:doAdd', '/user/articleCategory/doAdd', '34', '2', '100', 'layui-icon-add-1', '2', '2019-04-09 15:12:26', '1523899', '2019-05-19 18:02:20', '1523899');
INSERT INTO `t_permission` VALUES ('36', '编辑文章分类', 'articleCategory:doEdit', '/user/articleCategory/doEdit', '34', '2', '100', 'layui-icon-edit', '2', '2019-04-09 15:12:50', '1523899', '2019-05-19 18:02:46', '1523899');
INSERT INTO `t_permission` VALUES ('37', '删除文章分类', 'articleCategory:doDelete', '/user/articleCategory/doDelete', '34', '2', '100', 'layui-icon-delete', '2', '2019-04-09 15:13:10', '1523899', '2019-05-19 18:03:06', '1523899');
INSERT INTO `t_permission` VALUES ('38', '修改分类状态', 'articleCategory:changeStatus', '/user/articleCategory/changeStatus', '34', '2', '100', 'layui-icon-edit', '2', '2019-04-09 16:12:57', '1523899', '2019-05-19 18:03:35', '1523899');
INSERT INTO `t_permission` VALUES ('41', '异常日志', 'exceptionLog:load', '/user/exceptionLog', '22', '2', '100', 'layui-icon-fonts-del', '1', '2019-04-16 16:44:40', '1523899', '2019-05-19 17:58:15', '1523899');
INSERT INTO `t_permission` VALUES ('42', '新增系统配置', 'systemConfig:doAdd', '/user/systemConfig/doAdd', '24', '2', '100', 'layui-icon-ok', '2', '2019-04-20 14:32:06', '1523899', '2019-05-19 17:59:03', '1523899');
INSERT INTO `t_permission` VALUES ('43', '编辑系统配置', 'systemConfig:doEdit', '/user/systemConfig/doEdit', '24', '2', '100', 'layui-icon-star-fill', '2', '2019-04-20 14:32:34', '1523899', '2019-05-19 17:58:53', '1523899');
INSERT INTO `t_permission` VALUES ('44', '删除系统配置', 'systemConfig:doDelete', '/user/systemConfig/doDelete', '24', '2', '100', 'layui-icon-delete', '2', '2019-04-20 14:33:04', '1523899', '2019-05-19 18:03:54', '1523899');
INSERT INTO `t_permission` VALUES ('45', '文章下线', 'article:offline', '/user/article/offline', '7', '2', '100', 'layui-icon-face-cry', '2', '2019-05-02 21:56:46', '1523899', '2019-05-19 18:04:31', '1523899');
INSERT INTO `t_permission` VALUES ('46', '文章es数据同步', 'article:syncEs', '/user/article/syncEs', '24', '2', '100', 'layui-icon-star-fill', '1', '2019-05-03 22:13:21', '1523899', '2019-05-19 17:54:00', '1523899');
INSERT INTO `t_permission` VALUES ('47', '同步es数据', 'user:data:syncEs', '/user/data/syncEs', '30', '2', '100', 'layui-icon-star-fill', '2', '2019-05-11 00:49:35', '1523899', '2019-05-19 17:43:43', '1523899');
INSERT INTO `t_permission` VALUES ('48', '库存管理', 'repertory:load', '/user/repertory/index', '0', '1', '100', 'layui-icon-diamond', '1', '2019-05-19 12:32:22', '1523899', '2019-05-19 17:44:49', '1523899');
INSERT INTO `t_permission` VALUES ('49', '新增库存', 'repertory:doAdd', '/user/repertory/doAdd', '48', '2', '100', 'layui-icon-star-fill', '2', '2019-05-19 13:04:14', '1523899', '2019-05-19 19:41:39', '1523899');
INSERT INTO `t_permission` VALUES ('50', '删除库存', 'repertory:doDelete', '/user/repertory/doDelete', '48', '2', '100', 'layui-icon-delete', '2', '2019-05-19 20:14:13', '1523899', null, '0');

-- ----------------------------
-- Table structure for `t_picture`
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `picture_url` varchar(200) NOT NULL DEFAULT '' COMMENT '图片访问路径',
  `picture_path` varchar(200) NOT NULL DEFAULT '' COMMENT '图片保存路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8 COMMENT='图片信息表';

-- ----------------------------
-- Records of t_picture
-- ----------------------------

-- ----------------------------
-- Table structure for `t_praise`
-- ----------------------------
DROP TABLE IF EXISTS `t_praise`;
CREATE TABLE `t_praise` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章id',
  `comment_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1593493 DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of t_praise
-- ----------------------------

-- ----------------------------
-- Table structure for `t_repertory`
-- ----------------------------
DROP TABLE IF EXISTS `t_repertory`;
CREATE TABLE `t_repertory` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `specification` varchar(64) NOT NULL DEFAULT '' COMMENT '规格型号',
  `unit` varchar(32) NOT NULL DEFAULT '' COMMENT '单位',
  `quantity` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `unit_price` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `tax` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '税额',
  `total_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT ' 创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='库存表';

-- ----------------------------
-- Records of t_repertory
-- ----------------------------
INSERT INTO `t_repertory` VALUES ('2', '100*100', '台', '5', '20.44', '0.00', '0.00', '0.00', '0', '2019-05-19 12:34:47');
INSERT INTO `t_repertory` VALUES ('4', '100*200*800', '台', '50', '100.00', '5000.00', '800.00', '4200.00', '1523899', '2019-05-19 20:26:43');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
  `super_admin_flag` int(1) NOT NULL DEFAULT '0' COMMENT '是否是管理员角色，0-否，1-是',
  `remark` varchar(128) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '2019-03-30 19:56:04', '1523899', '2019-04-15 18:36:37', '1523899', '1', '权限最大');
INSERT INTO `t_role` VALUES ('2', '注册用户', '2019-03-31 12:20:02', '1523899', '2019-05-19 17:40:35', '1523899', '0', '普通注册用户的权限，有浏览的权限');

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(10) NOT NULL DEFAULT '0' COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1', '2019-03-30 19:57:34', '1523899');
INSERT INTO `t_role_permission` VALUES ('2', '1', '2', '2019-03-30 19:57:57', '1523899');
INSERT INTO `t_role_permission` VALUES ('3', '1', '3', '2019-03-30 21:00:34', '1523899');
INSERT INTO `t_role_permission` VALUES ('4', '1', '4', '2019-03-30 21:18:54', '1523899');
INSERT INTO `t_role_permission` VALUES ('5', '1', '5', '2019-03-30 21:31:42', '1523899');
INSERT INTO `t_role_permission` VALUES ('6', '1', '6', '2019-03-30 21:35:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('8', '1', '7', '2019-03-30 21:38:25', '1523899');
INSERT INTO `t_role_permission` VALUES ('9', '1', '8', '2019-03-30 21:52:39', '1523899');
INSERT INTO `t_role_permission` VALUES ('10', '1', '9', '2019-03-30 21:53:41', '1523899');
INSERT INTO `t_role_permission` VALUES ('11', '1', '10', '2019-03-30 21:54:08', '1523899');
INSERT INTO `t_role_permission` VALUES ('12', '1', '11', '2019-03-30 21:54:59', '1523899');
INSERT INTO `t_role_permission` VALUES ('13', '1', '12', '2019-03-30 21:55:44', '1523899');
INSERT INTO `t_role_permission` VALUES ('14', '1', '13', '2019-03-30 21:57:25', '1523899');
INSERT INTO `t_role_permission` VALUES ('15', '1', '14', '2019-03-30 21:57:53', '1523899');
INSERT INTO `t_role_permission` VALUES ('16', '1', '15', '2019-03-30 21:58:13', '1523899');
INSERT INTO `t_role_permission` VALUES ('17', '1', '16', '2019-03-30 21:58:37', '1523899');
INSERT INTO `t_role_permission` VALUES ('18', '1', '17', '2019-03-30 21:58:57', '1523899');
INSERT INTO `t_role_permission` VALUES ('19', '1', '18', '2019-03-30 21:59:23', '1523899');
INSERT INTO `t_role_permission` VALUES ('20', '1', '19', '2019-03-30 22:00:40', '1523899');
INSERT INTO `t_role_permission` VALUES ('21', '1', '20', '2019-03-30 22:01:07', '1523899');
INSERT INTO `t_role_permission` VALUES ('22', '1', '21', '2019-03-30 22:01:29', '1523899');
INSERT INTO `t_role_permission` VALUES ('23', '1', '22', '2019-03-30 22:01:51', '1523899');
INSERT INTO `t_role_permission` VALUES ('24', '1', '23', '2019-03-30 22:02:20', '1523899');
INSERT INTO `t_role_permission` VALUES ('25', '1', '24', '2019-03-30 22:02:40', '1523899');
INSERT INTO `t_role_permission` VALUES ('26', '1', '25', '2019-03-30 22:03:26', '1523899');
INSERT INTO `t_role_permission` VALUES ('27', '1', '26', '2019-03-30 22:03:47', '1523899');
INSERT INTO `t_role_permission` VALUES ('28', '1', '27', '2019-03-30 22:04:07', '1523899');
INSERT INTO `t_role_permission` VALUES ('29', '1', '28', '2019-03-30 22:04:26', '1523899');
INSERT INTO `t_role_permission` VALUES ('30', '1', '29', '2019-03-30 22:05:12', '1523899');
INSERT INTO `t_role_permission` VALUES ('31', '1', '30', '2019-03-30 22:06:38', '1523899');
INSERT INTO `t_role_permission` VALUES ('32', '1', '31', '2019-03-30 22:07:17', '1523899');
INSERT INTO `t_role_permission` VALUES ('33', '1', '32', '2019-03-31 00:58:16', '1523899');
INSERT INTO `t_role_permission` VALUES ('65', '1', '33', '2019-03-31 12:31:44', '1523899');
INSERT INTO `t_role_permission` VALUES ('66', '2', '6', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('67', '2', '7', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('68', '2', '11', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('69', '2', '12', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('70', '2', '18', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('71', '2', '8', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('72', '2', '20', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('73', '2', '21', '2019-03-31 13:44:11', '1523899');
INSERT INTO `t_role_permission` VALUES ('74', '1', '34', '2019-04-07 10:47:02', '1523899');
INSERT INTO `t_role_permission` VALUES ('75', '1', '35', '2019-04-09 15:12:26', '1523899');
INSERT INTO `t_role_permission` VALUES ('76', '1', '36', '2019-04-09 15:12:50', '1523899');
INSERT INTO `t_role_permission` VALUES ('77', '1', '37', '2019-04-09 15:13:10', '1523899');
INSERT INTO `t_role_permission` VALUES ('78', '1', '38', '2019-04-09 16:12:57', '1523899');
INSERT INTO `t_role_permission` VALUES ('80', '1', '40', '2019-04-16 16:39:20', '1523899');
INSERT INTO `t_role_permission` VALUES ('81', '1', '41', '2019-04-16 16:44:40', '1523899');
INSERT INTO `t_role_permission` VALUES ('82', '1', '42', '2019-04-20 14:32:06', '1523899');
INSERT INTO `t_role_permission` VALUES ('83', '1', '43', '2019-04-20 14:32:34', '1523899');
INSERT INTO `t_role_permission` VALUES ('84', '1', '44', '2019-04-20 14:33:04', '1523899');
INSERT INTO `t_role_permission` VALUES ('85', '1', '45', '2019-05-02 21:56:46', '1523899');
INSERT INTO `t_role_permission` VALUES ('86', '1', '46', '2019-05-03 22:13:21', '1523899');
INSERT INTO `t_role_permission` VALUES ('87', '1', '47', '2019-05-11 00:49:35', '1523899');
INSERT INTO `t_role_permission` VALUES ('88', '1', '48', '2019-05-19 12:32:22', '1523899');
INSERT INTO `t_role_permission` VALUES ('89', '1', '49', '2019-05-19 13:04:14', '1523899');
INSERT INTO `t_role_permission` VALUES ('90', '1', '50', '2019-05-19 20:14:13', '1523899');

-- ----------------------------
-- Table structure for `t_score_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_score_history`;
CREATE TABLE `t_score_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `type` int(1) NOT NULL COMMENT '积分类型，1-登陆，2-发表文章成功，3-回帖,4-签到.5-注册',
  `type_name` varchar(64) NOT NULL DEFAULT '' COMMENT '类型名',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `score_date` date NOT NULL COMMENT '积分获取日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `total_score` int(11) NOT NULL DEFAULT '0' COMMENT '积分总计',
  PRIMARY KEY (`id`),
  KEY `idex_user_id_type_score_date` (`user_id`,`type`,`score_date`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=488 DEFAULT CHARSET=utf8 COMMENT='积分历史表';

-- ----------------------------
-- Records of t_score_history
-- ----------------------------
INSERT INTO `t_score_history` VALUES ('245', '1523898', '5', '注册', '20', '2019-03-30', '2019-03-30 18:41:28', '20');
INSERT INTO `t_score_history` VALUES ('247', '1523899', '1', '登陆', '5', '2019-03-30', '2019-03-30 20:51:45', '5');
INSERT INTO `t_score_history` VALUES ('248', '1523899', '4', '签到', '5', '2019-03-30', '2019-03-30 22:12:06', '10');
INSERT INTO `t_score_history` VALUES ('249', '1523899', '2', '发表文章成功', '5', '2019-03-30', '2019-03-30 22:48:12', '15');
INSERT INTO `t_score_history` VALUES ('250', '1523899', '4', '签到', '5', '2019-03-31', '2019-03-31 00:36:40', '20');
INSERT INTO `t_score_history` VALUES ('251', '1523899', '1', '登陆', '5', '2019-03-31', '2019-03-31 02:41:55', '25');
INSERT INTO `t_score_history` VALUES ('253', '1524964', '5', '注册', '20', '2019-03-31', '2019-03-31 12:16:08', '20');
INSERT INTO `t_score_history` VALUES ('254', '1524965', '1', '登陆', '5', '2019-03-31', '2019-03-31 12:16:08', '5');
INSERT INTO `t_score_history` VALUES ('255', '1524965', '4', '签到', '5', '2019-03-31', '2019-03-31 13:11:18', '10');
INSERT INTO `t_score_history` VALUES ('256', '1524965', '3', '回帖', '3', '2019-03-31', '2019-03-31 13:24:38', '12');
INSERT INTO `t_score_history` VALUES ('257', '1524965', '3', '回帖', '3', '2019-03-31', '2019-03-31 13:37:52', '14');
INSERT INTO `t_score_history` VALUES ('258', '1525055', '5', '注册', '20', '2019-03-31', '2019-03-31 13:38:25', '20');
INSERT INTO `t_score_history` VALUES ('259', '1525055', '1', '登陆', '5', '2019-03-31', '2019-03-31 13:38:25', '25');
INSERT INTO `t_score_history` VALUES ('260', '1525055', '4', '签到', '5', '2019-03-31', '2019-03-31 13:38:36', '30');
INSERT INTO `t_score_history` VALUES ('261', '1523899', '1', '登陆', '5', '2019-04-06', '2019-04-06 21:03:39', '30');
INSERT INTO `t_score_history` VALUES ('262', '1523899', '2', '发表文章成功', '5', '2019-04-06', '2019-04-06 21:05:48', '35');
INSERT INTO `t_score_history` VALUES ('263', '1523899', '4', '签到', '5', '2019-04-06', '2019-04-06 21:25:03', '40');
INSERT INTO `t_score_history` VALUES ('264', '1523899', '1', '登陆', '5', '2019-04-07', '2019-04-07 10:33:51', '45');
INSERT INTO `t_score_history` VALUES ('265', '1523899', '3', '回帖', '3', '2019-04-09', '2019-04-09 16:33:56', '47');
INSERT INTO `t_score_history` VALUES ('266', '1523899', '1', '登陆', '5', '2019-04-09', '2019-04-09 17:10:29', '52');
INSERT INTO `t_score_history` VALUES ('267', '1523899', '1', '登陆', '5', '2019-04-10', '2019-04-10 13:58:51', '57');
INSERT INTO `t_score_history` VALUES ('268', '1523899', '2', '发表文章成功', '5', '2019-04-10', '2019-04-10 14:40:28', '62');
INSERT INTO `t_score_history` VALUES ('269', '1523899', '1', '登陆', '5', '2019-04-11', '2019-04-11 10:04:42', '67');
INSERT INTO `t_score_history` VALUES ('270', '1523899', '2', '发表文章成功', '5', '2019-04-11', '2019-04-11 10:14:14', '72');
INSERT INTO `t_score_history` VALUES ('271', '1523899', '1', '登陆', '5', '2019-04-12', '2019-04-12 17:14:43', '77');
INSERT INTO `t_score_history` VALUES ('272', '1523899', '4', '签到', '5', '2019-04-12', '2019-04-12 17:47:19', '82');
INSERT INTO `t_score_history` VALUES ('273', '1523899', '1', '登陆', '5', '2019-04-15', '2019-04-15 17:14:32', '87');
INSERT INTO `t_score_history` VALUES ('274', '1523899', '4', '签到', '5', '2019-04-15', '2019-04-15 18:34:27', '92');
INSERT INTO `t_score_history` VALUES ('275', '1523899', '1', '登陆', '5', '2019-04-16', '2019-04-16 16:35:51', '97');
INSERT INTO `t_score_history` VALUES ('276', '1523899', '4', '签到', '5', '2019-04-16', '2019-04-16 19:04:18', '107');
INSERT INTO `t_score_history` VALUES ('277', '1523899', '1', '登陆', '5', '2019-04-17', '2019-04-17 18:10:50', '112');
INSERT INTO `t_score_history` VALUES ('278', '1523899', '1', '登陆', '5', '2019-04-18', '2019-04-18 13:52:51', '117');
INSERT INTO `t_score_history` VALUES ('279', '1523899', '1', '登陆', '5', '2019-04-20', '2019-04-20 14:15:24', '122');
INSERT INTO `t_score_history` VALUES ('280', '1523899', '4', '签到', '5', '2019-04-20', '2019-04-20 16:29:38', '127');
INSERT INTO `t_score_history` VALUES ('281', '1524965', '1', '登陆', '5', '2019-04-20', '2019-04-20 16:29:54', '19');
INSERT INTO `t_score_history` VALUES ('282', '1524965', '2', '发表文章成功', '5', '2019-04-20', '2019-04-20 16:32:15', '24');
INSERT INTO `t_score_history` VALUES ('283', '1524965', '2', '发表文章成功', '5', '2019-04-20', '2019-04-20 16:33:48', '29');
INSERT INTO `t_score_history` VALUES ('284', '1523899', '2', '发表文章成功', '5', '2019-04-20', '2019-04-20 22:43:11', '132');
INSERT INTO `t_score_history` VALUES ('285', '1523899', '3', '回帖', '3', '2019-04-20', '2019-04-20 23:18:16', '134');
INSERT INTO `t_score_history` VALUES ('286', '1524965', '1', '登陆', '5', '2019-04-21', '2019-04-21 00:02:01', '34');
INSERT INTO `t_score_history` VALUES ('287', '1524965', '4', '签到', '5', '2019-04-21', '2019-04-21 00:08:21', '39');
INSERT INTO `t_score_history` VALUES ('288', '1523899', '1', '登陆', '5', '2019-04-21', '2019-04-21 00:09:28', '139');
INSERT INTO `t_score_history` VALUES ('289', '1523899', '1', '登陆', '5', '2019-04-26', '2019-04-26 22:38:58', '144');
INSERT INTO `t_score_history` VALUES ('290', '1523899', '4', '签到', '5', '2019-04-26', '2019-04-26 23:45:04', '149');
INSERT INTO `t_score_history` VALUES ('291', '1523899', '3', '回帖', '3', '2019-04-26', '2019-04-26 23:47:44', '151');
INSERT INTO `t_score_history` VALUES ('292', '1523899', '3', '回帖', '3', '2019-04-26', '2019-04-26 23:50:52', '153');
INSERT INTO `t_score_history` VALUES ('293', '1523899', '1', '登陆', '5', '2019-04-27', '2019-04-27 20:14:28', '158');
INSERT INTO `t_score_history` VALUES ('294', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:15:59', '163');
INSERT INTO `t_score_history` VALUES ('295', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:19:34', '168');
INSERT INTO `t_score_history` VALUES ('296', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:31:54', '173');
INSERT INTO `t_score_history` VALUES ('297', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:35:27', '178');
INSERT INTO `t_score_history` VALUES ('298', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:46:56', '183');
INSERT INTO `t_score_history` VALUES ('299', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:50:27', '188');
INSERT INTO `t_score_history` VALUES ('300', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 20:53:45', '193');
INSERT INTO `t_score_history` VALUES ('301', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 21:05:12', '198');
INSERT INTO `t_score_history` VALUES ('302', '1523899', '4', '签到', '5', '2019-04-27', '2019-04-27 21:33:25', '208');
INSERT INTO `t_score_history` VALUES ('303', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 21:39:29', '213');
INSERT INTO `t_score_history` VALUES ('304', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 21:43:59', '218');
INSERT INTO `t_score_history` VALUES ('305', '1523899', '2', '发表文章成功', '5', '2019-04-27', '2019-04-27 21:56:41', '223');
INSERT INTO `t_score_history` VALUES ('306', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 21:57:51', '225');
INSERT INTO `t_score_history` VALUES ('307', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 21:59:32', '227');
INSERT INTO `t_score_history` VALUES ('308', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 22:07:20', '229');
INSERT INTO `t_score_history` VALUES ('309', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 22:15:45', '231');
INSERT INTO `t_score_history` VALUES ('310', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 22:16:05', '233');
INSERT INTO `t_score_history` VALUES ('311', '1523899', '3', '回帖', '3', '2019-04-27', '2019-04-27 22:16:49', '235');
INSERT INTO `t_score_history` VALUES ('312', '1523899', '1', '登陆', '5', '2019-05-01', '2019-05-01 23:30:32', '240');
INSERT INTO `t_score_history` VALUES ('313', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 00:00:16', '245');
INSERT INTO `t_score_history` VALUES ('314', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 00:04:22', '250');
INSERT INTO `t_score_history` VALUES ('315', '1523899', '3', '回帖', '3', '2019-05-02', '2019-05-02 00:12:35', '252');
INSERT INTO `t_score_history` VALUES ('316', '1524965', '1', '登陆', '5', '2019-05-02', '2019-05-02 00:13:52', '44');
INSERT INTO `t_score_history` VALUES ('317', '1524965', '3', '回帖', '3', '2019-05-02', '2019-05-02 00:14:42', '46');
INSERT INTO `t_score_history` VALUES ('318', '1524965', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 00:26:23', '51');
INSERT INTO `t_score_history` VALUES ('319', '1523899', '3', '回帖', '3', '2019-05-02', '2019-05-02 00:28:39', '254');
INSERT INTO `t_score_history` VALUES ('320', '1523899', '4', '签到', '5', '2019-05-02', '2019-05-02 00:30:38', '259');
INSERT INTO `t_score_history` VALUES ('321', '1523899', '1', '登陆', '5', '2019-05-02', '2019-05-02 18:11:01', '264');
INSERT INTO `t_score_history` VALUES ('322', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 18:13:04', '269');
INSERT INTO `t_score_history` VALUES ('323', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 20:38:55', '274');
INSERT INTO `t_score_history` VALUES ('324', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:15:50', '279');
INSERT INTO `t_score_history` VALUES ('325', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:19:33', '284');
INSERT INTO `t_score_history` VALUES ('326', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:30:08', '289');
INSERT INTO `t_score_history` VALUES ('327', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:31:11', '294');
INSERT INTO `t_score_history` VALUES ('328', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:41:11', '299');
INSERT INTO `t_score_history` VALUES ('329', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:49:16', '304');
INSERT INTO `t_score_history` VALUES ('330', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:50:58', '309');
INSERT INTO `t_score_history` VALUES ('331', '1523899', '2', '发表文章成功', '5', '2019-05-02', '2019-05-02 23:55:27', '314');
INSERT INTO `t_score_history` VALUES ('332', '1523899', '2', '发表文章成功', '5', '2019-05-03', '2019-05-03 00:11:28', '319');
INSERT INTO `t_score_history` VALUES ('333', '1523899', '4', '签到', '5', '2019-05-03', '2019-05-03 00:17:43', '329');
INSERT INTO `t_score_history` VALUES ('334', '1523899', '1', '登陆', '5', '2019-05-03', '2019-05-03 17:19:08', '334');
INSERT INTO `t_score_history` VALUES ('335', '1523899', '2', '发表文章成功', '5', '2019-05-03', '2019-05-03 21:01:06', '339');
INSERT INTO `t_score_history` VALUES ('336', '1523899', '3', '回帖', '3', '2019-05-03', '2019-05-03 21:27:41', '341');
INSERT INTO `t_score_history` VALUES ('337', '1523899', '2', '发表文章成功', '5', '2019-05-03', '2019-05-03 22:32:51', '346');
INSERT INTO `t_score_history` VALUES ('338', '1523899', '3', '回帖', '3', '2019-05-03', '2019-05-03 22:36:46', '348');
INSERT INTO `t_score_history` VALUES ('339', '1573184', '5', '注册', '20', '2019-05-03', '2019-05-03 22:42:58', '20');
INSERT INTO `t_score_history` VALUES ('340', '1573184', '1', '登陆', '5', '2019-05-03', '2019-05-03 22:42:58', '25');
INSERT INTO `t_score_history` VALUES ('341', '1573184', '2', '发表文章成功', '5', '2019-05-03', '2019-05-03 22:44:51', '30');
INSERT INTO `t_score_history` VALUES ('342', '1573184', '3', '回帖', '3', '2019-05-03', '2019-05-03 22:46:27', '32');
INSERT INTO `t_score_history` VALUES ('343', '1574355', '5', '注册', '20', '2019-05-04', '2019-05-04 18:09:36', '20');
INSERT INTO `t_score_history` VALUES ('344', '1574355', '1', '登陆', '5', '2019-05-04', '2019-05-04 18:09:36', '25');
INSERT INTO `t_score_history` VALUES ('345', '1523899', '1', '登陆', '5', '2019-05-04', '2019-05-04 18:18:53', '353');
INSERT INTO `t_score_history` VALUES ('346', '1574355', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 18:19:32', '30');
INSERT INTO `t_score_history` VALUES ('347', '1574355', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 18:19:36', '35');
INSERT INTO `t_score_history` VALUES ('348', '1574355', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 18:19:38', '40');
INSERT INTO `t_score_history` VALUES ('349', '1574355', '4', '签到', '5', '2019-05-04', '2019-05-04 18:19:46', '45');
INSERT INTO `t_score_history` VALUES ('350', '1523899', '4', '签到', '5', '2019-05-04', '2019-05-04 22:32:26', '363');
INSERT INTO `t_score_history` VALUES ('351', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:34:05', '368');
INSERT INTO `t_score_history` VALUES ('352', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:37:14', '373');
INSERT INTO `t_score_history` VALUES ('353', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:02', '378');
INSERT INTO `t_score_history` VALUES ('354', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:04', '383');
INSERT INTO `t_score_history` VALUES ('355', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:14', '388');
INSERT INTO `t_score_history` VALUES ('356', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:15', '393');
INSERT INTO `t_score_history` VALUES ('357', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:17', '398');
INSERT INTO `t_score_history` VALUES ('358', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:19', '403');
INSERT INTO `t_score_history` VALUES ('359', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:21', '408');
INSERT INTO `t_score_history` VALUES ('360', '1523899', '2', '发表文章成功', '5', '2019-05-04', '2019-05-04 22:47:23', '413');
INSERT INTO `t_score_history` VALUES ('361', '1523899', '1', '登陆', '5', '2019-05-05', '2019-05-05 18:46:38', '418');
INSERT INTO `t_score_history` VALUES ('362', '1523899', '4', '签到', '5', '2019-05-05', '2019-05-05 18:49:06', '428');
INSERT INTO `t_score_history` VALUES ('363', '1523899', '1', '登陆', '5', '2019-05-09', '2019-05-09 17:22:35', '433');
INSERT INTO `t_score_history` VALUES ('364', '1523899', '1', '登陆', '5', '2019-05-11', '2019-05-11 00:39:20', '438');
INSERT INTO `t_score_history` VALUES ('365', '1523899', '4', '签到', '5', '2019-05-11', '2019-05-11 00:58:47', '443');
INSERT INTO `t_score_history` VALUES ('366', '1584177', '5', '注册', '20', '2019-05-11', '2019-05-11 13:39:46', '20');
INSERT INTO `t_score_history` VALUES ('367', '1584177', '1', '登陆', '5', '2019-05-11', '2019-05-11 13:39:46', '25');
INSERT INTO `t_score_history` VALUES ('368', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 17:49:17', '448');
INSERT INTO `t_score_history` VALUES ('369', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 18:10:40', '453');
INSERT INTO `t_score_history` VALUES ('370', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:01:32', '458');
INSERT INTO `t_score_history` VALUES ('371', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:02:39', '463');
INSERT INTO `t_score_history` VALUES ('372', '1584522', '5', '注册', '20', '2019-05-11', '2019-05-11 19:20:57', '20');
INSERT INTO `t_score_history` VALUES ('373', '1584522', '1', '登陆', '5', '2019-05-11', '2019-05-11 19:20:57', '25');
INSERT INTO `t_score_history` VALUES ('374', '1584522', '4', '签到', '5', '2019-05-11', '2019-05-11 19:22:17', '30');
INSERT INTO `t_score_history` VALUES ('375', '1584522', '3', '回帖', '3', '2019-05-11', '2019-05-11 19:22:39', '32');
INSERT INTO `t_score_history` VALUES ('376', '1584522', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:35:48', '37');
INSERT INTO `t_score_history` VALUES ('377', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:43', '468');
INSERT INTO `t_score_history` VALUES ('378', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:45', '473');
INSERT INTO `t_score_history` VALUES ('379', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:47', '478');
INSERT INTO `t_score_history` VALUES ('380', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:49', '483');
INSERT INTO `t_score_history` VALUES ('381', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:51', '488');
INSERT INTO `t_score_history` VALUES ('382', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 19:44:53', '493');
INSERT INTO `t_score_history` VALUES ('383', '1523899', '3', '回帖', '3', '2019-05-11', '2019-05-11 19:45:40', '495');
INSERT INTO `t_score_history` VALUES ('384', '1523899', '3', '回帖', '3', '2019-05-11', '2019-05-11 19:45:51', '497');
INSERT INTO `t_score_history` VALUES ('385', '1584712', '5', '注册', '20', '2019-05-11', '2019-05-11 22:21:36', '20');
INSERT INTO `t_score_history` VALUES ('386', '1584712', '1', '登陆', '5', '2019-05-11', '2019-05-11 22:21:36', '25');
INSERT INTO `t_score_history` VALUES ('387', '1584728', '5', '注册', '20', '2019-05-11', '2019-05-11 22:32:24', '20');
INSERT INTO `t_score_history` VALUES ('388', '1584728', '1', '登陆', '5', '2019-05-11', '2019-05-11 22:32:24', '25');
INSERT INTO `t_score_history` VALUES ('389', '1584742', '5', '注册', '20', '2019-05-11', '2019-05-11 22:41:49', '20');
INSERT INTO `t_score_history` VALUES ('390', '1584742', '1', '登陆', '5', '2019-05-11', '2019-05-11 22:41:49', '25');
INSERT INTO `t_score_history` VALUES ('391', '1584742', '4', '签到', '5', '2019-05-11', '2019-05-11 22:44:36', '30');
INSERT INTO `t_score_history` VALUES ('392', '1523899', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:19', '502');
INSERT INTO `t_score_history` VALUES ('393', '1584728', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:23', '30');
INSERT INTO `t_score_history` VALUES ('394', '1584728', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:25', '35');
INSERT INTO `t_score_history` VALUES ('395', '1584728', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:28', '40');
INSERT INTO `t_score_history` VALUES ('396', '1584742', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:29', '35');
INSERT INTO `t_score_history` VALUES ('397', '1584712', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:31', '30');
INSERT INTO `t_score_history` VALUES ('398', '1584742', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:33', '40');
INSERT INTO `t_score_history` VALUES ('399', '1584728', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:36', '45');
INSERT INTO `t_score_history` VALUES ('400', '1584712', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:37', '35');
INSERT INTO `t_score_history` VALUES ('401', '1584712', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:40', '40');
INSERT INTO `t_score_history` VALUES ('402', '1584712', '2', '发表文章成功', '5', '2019-05-11', '2019-05-11 22:55:42', '45');
INSERT INTO `t_score_history` VALUES ('403', '1523899', '3', '回帖', '3', '2019-05-11', '2019-05-11 23:00:39', '504');
INSERT INTO `t_score_history` VALUES ('404', '1523899', '1', '登陆', '5', '2019-05-12', '2019-05-12 15:38:49', '509');
INSERT INTO `t_score_history` VALUES ('405', '1523899', '4', '签到', '5', '2019-05-12', '2019-05-12 15:40:37', '519');
INSERT INTO `t_score_history` VALUES ('406', '1585770', '5', '注册', '20', '2019-05-12', '2019-05-12 15:44:05', '20');
INSERT INTO `t_score_history` VALUES ('407', '1585770', '1', '登陆', '5', '2019-05-12', '2019-05-12 15:44:05', '25');
INSERT INTO `t_score_history` VALUES ('408', '1585770', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:01:57', '30');
INSERT INTO `t_score_history` VALUES ('409', '1585770', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:02:00', '35');
INSERT INTO `t_score_history` VALUES ('410', '1585770', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:02:02', '40');
INSERT INTO `t_score_history` VALUES ('411', '1585770', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:02:03', '45');
INSERT INTO `t_score_history` VALUES ('412', '1523899', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:02:05', '524');
INSERT INTO `t_score_history` VALUES ('413', '1585770', '4', '签到', '5', '2019-05-12', '2019-05-12 16:02:37', '50');
INSERT INTO `t_score_history` VALUES ('414', '1585770', '3', '回帖', '3', '2019-05-12', '2019-05-12 16:10:15', '52');
INSERT INTO `t_score_history` VALUES ('415', '1585770', '3', '回帖', '3', '2019-05-12', '2019-05-12 16:17:46', '54');
INSERT INTO `t_score_history` VALUES ('416', '1585815', '5', '注册', '20', '2019-05-12', '2019-05-12 16:18:27', '20');
INSERT INTO `t_score_history` VALUES ('417', '1585815', '1', '登陆', '5', '2019-05-12', '2019-05-12 16:18:27', '25');
INSERT INTO `t_score_history` VALUES ('418', '1585815', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:23:42', '30');
INSERT INTO `t_score_history` VALUES ('419', '1585815', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:25:06', '35');
INSERT INTO `t_score_history` VALUES ('420', '1585770', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 16:25:07', '59');
INSERT INTO `t_score_history` VALUES ('421', '1523899', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:03:55', '529');
INSERT INTO `t_score_history` VALUES ('422', '1523899', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:04:28', '531');
INSERT INTO `t_score_history` VALUES ('423', '1523899', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:05:22', '533');
INSERT INTO `t_score_history` VALUES ('424', '1525055', '1', '登陆', '5', '2019-05-12', '2019-05-12 17:08:16', '35');
INSERT INTO `t_score_history` VALUES ('425', '1525055', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:15:31', '40');
INSERT INTO `t_score_history` VALUES ('426', '1585881', '5', '注册', '20', '2019-05-12', '2019-05-12 17:17:03', '20');
INSERT INTO `t_score_history` VALUES ('427', '1585881', '1', '登陆', '5', '2019-05-12', '2019-05-12 17:17:03', '25');
INSERT INTO `t_score_history` VALUES ('428', '1585888', '5', '注册', '20', '2019-05-12', '2019-05-12 17:20:23', '20');
INSERT INTO `t_score_history` VALUES ('429', '1585888', '1', '登陆', '5', '2019-05-12', '2019-05-12 17:20:23', '25');
INSERT INTO `t_score_history` VALUES ('430', '1585891', '5', '注册', '20', '2019-05-12', '2019-05-12 17:22:06', '20');
INSERT INTO `t_score_history` VALUES ('431', '1585891', '1', '登陆', '5', '2019-05-12', '2019-05-12 17:22:06', '25');
INSERT INTO `t_score_history` VALUES ('432', '1585888', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:24:24', '30');
INSERT INTO `t_score_history` VALUES ('433', '1585891', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:24:27', '30');
INSERT INTO `t_score_history` VALUES ('434', '1585881', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:24:33', '30');
INSERT INTO `t_score_history` VALUES ('435', '1585881', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:24:37', '35');
INSERT INTO `t_score_history` VALUES ('436', '1525055', '2', '发表文章成功', '5', '2019-05-12', '2019-05-12 17:24:39', '45');
INSERT INTO `t_score_history` VALUES ('437', '1523899', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:26:23', '535');
INSERT INTO `t_score_history` VALUES ('438', '1525055', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:26:52', '47');
INSERT INTO `t_score_history` VALUES ('439', '1585881', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:27:22', '37');
INSERT INTO `t_score_history` VALUES ('440', '1585891', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:27:37', '32');
INSERT INTO `t_score_history` VALUES ('441', '1585888', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:28:28', '32');
INSERT INTO `t_score_history` VALUES ('442', '1585888', '4', '签到', '5', '2019-05-12', '2019-05-12 17:29:37', '37');
INSERT INTO `t_score_history` VALUES ('443', '1523899', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:30:03', '537');
INSERT INTO `t_score_history` VALUES ('444', '1585888', '3', '回帖', '3', '2019-05-12', '2019-05-12 17:30:14', '39');
INSERT INTO `t_score_history` VALUES ('445', '1523899', '1', '登陆', '5', '2019-05-13', '2019-05-13 23:19:23', '542');
INSERT INTO `t_score_history` VALUES ('446', '1523899', '3', '回帖', '3', '2019-05-13', '2019-05-13 23:21:44', '544');
INSERT INTO `t_score_history` VALUES ('447', '1523899', '4', '签到', '5', '2019-05-13', '2019-05-13 23:45:22', '559');
INSERT INTO `t_score_history` VALUES ('448', '1523899', '2', '发表文章成功', '5', '2019-05-13', '2019-05-13 23:48:12', '564');
INSERT INTO `t_score_history` VALUES ('449', '1523899', '2', '发表文章成功', '5', '2019-05-13', '2019-05-13 23:48:14', '569');
INSERT INTO `t_score_history` VALUES ('450', '1523899', '1', '登陆', '5', '2019-05-17', '2019-05-17 21:59:58', '574');
INSERT INTO `t_score_history` VALUES ('451', '1523899', '4', '签到', '5', '2019-05-17', '2019-05-17 22:00:10', '579');
INSERT INTO `t_score_history` VALUES ('452', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:19:54', '584');
INSERT INTO `t_score_history` VALUES ('453', '1523899', '3', '回帖', '3', '2019-05-17', '2019-05-17 22:21:17', '586');
INSERT INTO `t_score_history` VALUES ('454', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:35:33', '591');
INSERT INTO `t_score_history` VALUES ('455', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:35:35', '596');
INSERT INTO `t_score_history` VALUES ('456', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:35:37', '601');
INSERT INTO `t_score_history` VALUES ('457', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:42:32', '606');
INSERT INTO `t_score_history` VALUES ('458', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:43:30', '611');
INSERT INTO `t_score_history` VALUES ('459', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:46:57', '616');
INSERT INTO `t_score_history` VALUES ('460', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:46:59', '621');
INSERT INTO `t_score_history` VALUES ('461', '1523899', '3', '回帖', '3', '2019-05-17', '2019-05-17 22:48:35', '623');
INSERT INTO `t_score_history` VALUES ('462', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:57:04', '628');
INSERT INTO `t_score_history` VALUES ('463', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 22:57:06', '633');
INSERT INTO `t_score_history` VALUES ('464', '1523899', '3', '回帖', '3', '2019-05-17', '2019-05-17 22:57:23', '635');
INSERT INTO `t_score_history` VALUES ('465', '1573184', '1', '登陆', '5', '2019-05-17', '2019-05-17 23:14:21', '37');
INSERT INTO `t_score_history` VALUES ('466', '1573184', '3', '回帖', '3', '2019-05-17', '2019-05-17 23:14:47', '39');
INSERT INTO `t_score_history` VALUES ('467', '1573184', '3', '回帖', '3', '2019-05-17', '2019-05-17 23:15:08', '41');
INSERT INTO `t_score_history` VALUES ('468', '1573184', '3', '回帖', '3', '2019-05-17', '2019-05-17 23:15:37', '43');
INSERT INTO `t_score_history` VALUES ('469', '1585881', '1', '登陆', '5', '2019-05-17', '2019-05-17 23:18:44', '42');
INSERT INTO `t_score_history` VALUES ('470', '1585881', '4', '签到', '5', '2019-05-17', '2019-05-17 23:19:26', '47');
INSERT INTO `t_score_history` VALUES ('471', '1585881', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 23:23:01', '52');
INSERT INTO `t_score_history` VALUES ('472', '1585881', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 23:23:03', '57');
INSERT INTO `t_score_history` VALUES ('473', '1573184', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 23:23:17', '48');
INSERT INTO `t_score_history` VALUES ('474', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 23:23:20', '640');
INSERT INTO `t_score_history` VALUES ('475', '1523899', '2', '发表文章成功', '5', '2019-05-17', '2019-05-17 23:23:39', '645');
INSERT INTO `t_score_history` VALUES ('476', '1523899', '1', '登陆', '5', '2019-05-18', '2019-05-18 21:17:06', '650');
INSERT INTO `t_score_history` VALUES ('477', '1523899', '4', '签到', '5', '2019-05-18', '2019-05-18 21:17:13', '665');
INSERT INTO `t_score_history` VALUES ('478', '1523899', '2', '发表文章成功', '5', '2019-05-18', '2019-05-18 21:21:56', '670');
INSERT INTO `t_score_history` VALUES ('479', '1523899', '2', '发表文章成功', '5', '2019-05-18', '2019-05-18 21:29:41', '675');
INSERT INTO `t_score_history` VALUES ('480', '1523899', '2', '发表文章成功', '5', '2019-05-18', '2019-05-18 21:29:43', '680');
INSERT INTO `t_score_history` VALUES ('481', '1523899', '1', '登陆', '5', '2019-05-19', '2019-05-19 12:28:51', '685');
INSERT INTO `t_score_history` VALUES ('482', '1523899', '4', '签到', '15', '2019-05-19', '2019-05-19 21:16:09', '700');
INSERT INTO `t_score_history` VALUES ('483', '1523899', '1', '登陆', '5', '2019-05-21', '2019-05-21 23:02:34', '705');
INSERT INTO `t_score_history` VALUES ('484', '1523899', '3', '回帖', '2', '2019-05-21', '2019-05-21 23:05:07', '707');
INSERT INTO `t_score_history` VALUES ('485', '1523899', '4', '签到', '5', '2019-05-21', '2019-05-21 23:06:33', '712');
INSERT INTO `t_score_history` VALUES ('486', '1523899', '1', '登陆', '5', '2019-05-26', '2019-05-26 15:45:27', '717');
INSERT INTO `t_score_history` VALUES ('487', '1523899', '4', '签到', '5', '2019-05-26', '2019-05-26 17:39:48', '722');

-- ----------------------------
-- Table structure for `t_system_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_config`;
CREATE TABLE `t_system_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `param_name` varchar(128) NOT NULL DEFAULT '' COMMENT '变量名',
  `param_value` varchar(2048) NOT NULL DEFAULT '' COMMENT '变量值',
  `remark` varchar(128) NOT NULL DEFAULT '' COMMENT '变量备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT '变量类型，0-值，1-开关',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_name_unique` (`param_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of t_system_config
-- ----------------------------
INSERT INTO `t_system_config` VALUES ('1', 'web_name', '好好学习社区-good', '网站名称', '2019-04-17 18:16:57', '1523899', '2019-05-09 17:24:37', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('3', 'web_title', '好好学习社区', '网站标题', '2019-04-17 18:23:19', '1523899', '2019-05-12 17:04:55', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('4', 'keywords', '好好学习社区', '网站关键字', '2019-04-17 18:23:37', '1523899', '2019-04-27 21:17:29', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('5', 'description', '好好学习社区，致力于为web学习交流', 'description', '2019-04-17 18:23:56', '1523899', '2019-04-18 13:53:22', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('6', 'image_upload_dir', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\', '图片存储路径', '2019-04-17 18:29:02', '1523899', '2019-04-20 14:16:24', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('7', 'record_info', '2019 © &lt;a href=\"/\"  target=\"_blank\"&gt;panzhigao.vip 出品&lt;/a&gt;京ICP备18031226号', '网站版权信息', '2019-04-17 18:30:39', '1523899', '2019-04-27 21:20:20', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('8', 'web_code', 'body {}', '网站公共代码', '2019-04-17 18:31:03', '1523899', '2019-04-27 21:20:30', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('9', 'burying_point_code', 'https://s22.cnzz.com/z_stat.php?id=1274156186&web_id=1274156186', '埋点代码', '2019-04-17 18:31:24', '1523899', '2019-04-17 18:31:39', '1523899', '0');
INSERT INTO `t_system_config` VALUES ('14', 'mail_enabled', '0', '系统异常发生时,发送邮件到开发人员', '2019-04-20 15:42:42', '1523899', '2019-05-12 17:03:23', '1523899', '1');
INSERT INTO `t_system_config` VALUES ('15', 'email_receviers', '16253672@qq.com', '系统异常邮件接收人邮箱地址', '2019-04-20 15:53:39', '1523899', '2019-04-20 16:04:22', '1523899', '0');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别 0-未知，1-男，2-女',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_login_time` datetime NOT NULL COMMENT '最近登陆时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '0-禁用，1-正常',
  `telephone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `user_portrait` varchar(256) NOT NULL DEFAULT '' COMMENT '用户头像',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人id',
  `admin_flag` int(1) NOT NULL DEFAULT '0' COMMENT '管理员标志，0-否，1-是',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  `user_brief` varchar(1024) NOT NULL DEFAULT '' COMMENT '用户简介',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1523899', '1', 'admin', '大哥大', '6FA456619C9CD1E640134D615945CBA05D5F26DC32003EF3E59DA2A5', '2019-03-30 18:41:28', '2019-05-26 18:11:05', '1', '18911536627', '2019-05-11 17:16:28', 'http://www.panzhigao.vip/myimage/20190511171628096.jpg', '0', '1', '北京', '好好学习天天向上');
INSERT INTO `t_user` VALUES ('1524965', '1', 'gangtiexia', '钢铁侠', 'D2D5F45EA8D6F5D54EDE7A768BF1A3625F93B61F428B71539F7D9B7A', '2019-03-31 12:16:08', '2019-05-02 00:13:52', '1', '', '2019-05-02 00:14:22', 'http://www.panzhigao.vip/myimage/20190502001422523.jpg', '0', '0', '', '');
INSERT INTO `t_user` VALUES ('1525055', '1', 'lvdengxia', '绿灯侠', '0701BC65DCF322E5A6304ED34D683AEE3F80DD262C26C8043BCB14B9', '2019-03-31 13:38:25', '2019-05-12 17:08:16', '1', '', '2019-05-12 17:10:19', 'http://www.panzhigao.vip/myimage/20190512171019678.jpg', '0', '0', '美国', '');
INSERT INTO `t_user` VALUES ('1584522', '1', 'meidui', '美国队长', 'BE39BAF79005F5D84328AA4E484EA1861165E96C40D62543C1D0B099', '2019-05-11 19:20:57', '2019-05-11 19:20:57', '1', '', '2019-05-11 19:21:48', 'http://www.panzhigao.vip/myimage/20190511192148846.jpg', '0', '0', '纽约', '我是美队，大家支持我');
INSERT INTO `t_user` VALUES ('1584712', '0', 'wenzhang', '文章', 'FAB558B4184D2AB9531D67DA8C48F0269883EF9BF1C002238A61CEBF', '2019-05-11 22:21:36', '2019-05-11 22:21:36', '1', '', '2019-05-11 22:22:07', 'http://www.panzhigao.vip/myimage/20190511222207301.jpg', '0', '0', '北京', '大家好，我是文章');
INSERT INTO `t_user` VALUES ('1584728', '1', 'zhizhuxia', '蜘蛛侠', '95C491DB6D6909159446D3634D90710F6DD48DB32BD0EDBD97CCDF71', '2019-05-11 22:32:24', '2019-05-11 22:32:24', '1', '', '2019-05-11 22:32:51', 'http://www.panzhigao.vip/myimage/20190511223251153.jpg', '0', '0', '纽约', '我是蜘蛛侠');
INSERT INTO `t_user` VALUES ('1584742', '1', 'leishen', '雷神', 'C2D212606A7B2408BC12635F9DB6137470351582E197F0CAD93C3FC1', '2019-05-11 22:41:49', '2019-05-11 22:41:49', '1', '', '2019-05-11 22:44:19', 'http://www.panzhigao.vip/myimage/20190511224245596.jpg', '0', '0', '阿斯加德', '北欧神话里挥舞着大铁锤、掌控着风暴和闪电的天神，还能用铁锤打开时空之门。暴脾气的他因为自大鲁莽的行为重新点燃了一场古老战争的战火，之后被贬到凡间被迫与人类一起生活。在地球上的日子，“雷神”逐渐学会了如何做一个真正的英雄。');
INSERT INTO `t_user` VALUES ('1585770', '1', 'liubei', '刘备', 'BB0AFF2D023865671C187A48C610FD0CD8B28D01C20AC9F8DB69AE1A', '2019-05-12 15:44:05', '2019-05-12 16:09:48', '1', '', '2019-05-12 15:45:13', 'http://www.panzhigao.vip/myimage/20190512154513468.jpg', '0', '0', '重庆', '以德服人');
INSERT INTO `t_user` VALUES ('1585815', '1', 'guanyu', '关羽', 'E897F6C09B5F88BAE09C97F83A9270CE60B17C6B9CFD803DF8D2F81C', '2019-05-12 16:18:27', '2019-05-12 16:18:27', '1', '', '2019-05-12 16:19:24', 'http://www.panzhigao.vip/myimage/20190512161924113.jpg', '0', '0', '荆州', '观尔乃插标卖首');
INSERT INTO `t_user` VALUES ('1585881', '1', 'caocao', '曹操', 'BEC744D2AC825C0EEB786902EDBAE790A14BB51622451AD5DF8EF626', '2019-05-12 17:17:03', '2019-05-17 23:18:44', '1', '', '2019-05-12 17:17:57', 'http://www.panzhigao.vip/myimage/20190512171721805.jpg', '0', '0', '魏国', '宁教我负天下人，不教天下人负我');
INSERT INTO `t_user` VALUES ('1585888', '1', 'chenhe', '陈赫', 'FFBEBEA90DE7FEDFE91BC28B3768C3D32A5E9B960E53DD3FF8C93324', '2019-05-12 17:20:23', '2019-05-12 17:20:23', '1', '', '2019-05-12 17:20:46', 'http://www.panzhigao.vip/myimage/20190512172046676.jpg', '0', '0', '', '好男人就是我');

-- ----------------------------
-- Table structure for `t_user_extension`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_extension`;
CREATE TABLE `t_user_extension` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `user_portrait` varchar(256) NOT NULL DEFAULT '' COMMENT '用户头像',
  `user_brief` varchar(500) NOT NULL DEFAULT '' COMMENT '用户简介',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `article_counts` int(11) NOT NULL DEFAULT '0' COMMENT '文章数',
  `comment_counts` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `total_score` int(11) NOT NULL DEFAULT '0' COMMENT '总积分数',
  `continuous_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续登陆天数',
  `continuous_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  `total_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共登陆天数',
  `total_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1585892 DEFAULT CHARSET=utf8 COMMENT='用户拓展表';

-- ----------------------------
-- Records of t_user_extension
-- ----------------------------
INSERT INTO `t_user_extension` VALUES ('1523899', '大哥大', 'http://www.panzhigao.vip/myimage/20190511171628096.jpg', '好好学习天天向上', '2019-03-30 18:41:28', '2019-05-26 17:39:48', '71', '26', '722', '30', '21', '30', '21');
INSERT INTO `t_user_extension` VALUES ('1524965', '钢铁侠', 'http://www.panzhigao.vip/myimage/20190502001422523.jpg', '', '2019-03-31 12:16:08', '2019-05-02 00:26:23', '3', '3', '66', '3', '2', '3', '2');
INSERT INTO `t_user_extension` VALUES ('1525055', '绿灯侠', 'http://www.panzhigao.vip/myimage/20190512171019678.jpg', '', '2019-03-31 13:38:25', '2019-05-12 17:26:52', '2', '1', '47', '2', '1', '2', '1');
INSERT INTO `t_user_extension` VALUES ('1584522', '美国队长', 'http://www.panzhigao.vip/myimage/20190511192148846.jpg', '我是美队，大家支持我', '2019-05-11 19:20:57', '2019-05-11 19:35:48', '1', '1', '37', '1', '1', '1', '1');
INSERT INTO `t_user_extension` VALUES ('1584712', '文章', 'http://www.panzhigao.vip/myimage/20190511222207301.jpg', '大家好，我是文章', '2019-05-11 22:21:36', '2019-05-11 22:55:42', '4', '0', '45', '1', '0', '1', '0');
INSERT INTO `t_user_extension` VALUES ('1584728', '蜘蛛侠', 'http://www.panzhigao.vip/myimage/20190511223251153.jpg', '我是蜘蛛侠', '2019-05-11 22:32:24', '2019-05-11 22:55:36', '4', '0', '45', '1', '0', '1', '0');
INSERT INTO `t_user_extension` VALUES ('1584742', '雷神', 'http://www.panzhigao.vip/myimage/20190511224245596.jpg', '北欧神话里挥舞着大铁锤、掌控着风暴和闪电的天神，还能用铁锤打开时空之门。暴脾气的他因为自大鲁莽的行为重新点燃了一场古老战争的战火，之后被贬到凡间被迫与人类一起生活。在地球上的日子，“雷神”逐渐学会了如何做一个真正的英雄。', '2019-05-11 22:41:49', '2019-05-11 22:55:33', '2', '0', '40', '1', '1', '1', '1');
INSERT INTO `t_user_extension` VALUES ('1585770', '刘备', 'http://www.panzhigao.vip/myimage/20190512154513468.jpg', '以德服人', '2019-05-12 15:44:05', '2019-05-12 16:25:07', '5', '2', '59', '1', '1', '1', '1');
INSERT INTO `t_user_extension` VALUES ('1585815', '关羽', 'http://www.panzhigao.vip/myimage/20190512161924113.jpg', '观尔乃插标卖首', '2019-05-12 16:18:27', '2019-05-12 16:25:06', '2', '0', '35', '1', '0', '1', '0');
INSERT INTO `t_user_extension` VALUES ('1585881', '曹操', 'http://www.panzhigao.vip/myimage/20190512171721805.jpg', '宁教我负天下人，不教天下人负我', '2019-05-12 17:17:03', '2019-05-17 23:23:03', '4', '1', '57', '2', '1', '2', '1');
INSERT INTO `t_user_extension` VALUES ('1585888', '陈赫', 'http://www.panzhigao.vip/myimage/20190512172046676.jpg', '好男人就是我', '2019-05-12 17:20:23', '2019-05-12 17:30:14', '1', '2', '39', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1523899', '1', '2019-03-30 18:41:28', '1523898');
INSERT INTO `t_user_role` VALUES ('2', '1524965', '2', '2019-03-31 12:16:08', '1524964');
INSERT INTO `t_user_role` VALUES ('3', '1525055', '2', '2019-03-31 13:38:25', '1525055');
INSERT INTO `t_user_role` VALUES ('4', '1573184', '2', '2019-05-03 22:42:58', '1573184');
INSERT INTO `t_user_role` VALUES ('5', '1574355', '2', '2019-05-04 18:09:36', '1574355');
INSERT INTO `t_user_role` VALUES ('6', '1584177', '2', '2019-05-11 13:39:46', '1584177');
INSERT INTO `t_user_role` VALUES ('7', '1584522', '2', '2019-05-11 19:20:57', '1584522');
INSERT INTO `t_user_role` VALUES ('8', '1584712', '2', '2019-05-11 22:21:36', '1584712');
INSERT INTO `t_user_role` VALUES ('9', '1584728', '2', '2019-05-11 22:32:24', '1584728');
INSERT INTO `t_user_role` VALUES ('10', '1584742', '2', '2019-05-11 22:41:49', '1584742');
INSERT INTO `t_user_role` VALUES ('11', '1585770', '2', '2019-05-12 15:44:05', '1585770');
INSERT INTO `t_user_role` VALUES ('12', '1585815', '2', '2019-05-12 16:18:27', '1585815');
INSERT INTO `t_user_role` VALUES ('13', '1585881', '2', '2019-05-12 17:17:03', '1585881');
INSERT INTO `t_user_role` VALUES ('14', '1585888', '2', '2019-05-12 17:20:23', '1585888');
INSERT INTO `t_user_role` VALUES ('15', '1585891', '2', '2019-05-12 17:22:06', '1585891');
