/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : study2

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-04-10 15:43:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `status` int(1) NOT NULL COMMENT '0-审核未通过，1-草稿，2-审核中，3-发布成功',
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
  `stick` int(11) NOT NULL DEFAULT '0' COMMENT '置顶系数',
  `high_quality` int(1) NOT NULL DEFAULT '0' COMMENT '是否是精品贴，0-否，1-是',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章分类',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES ('1524252', '1523899', '', '3', '2019-03-31 00:35:00', null, '2019-03-31 00:35:00', '66666', '22222', '', '', '1', '9', '2', '0', '0', '1');
INSERT INTO `t_article` VALUES ('1534142', '1523899', 'admin', '3', '2019-04-06 21:04:24', '2019-04-09 17:12:54', '2019-04-06 21:05:48', '江小白LOGO被宣告无效，世间要再无江小白？！', '&lt;p&gt;说到白酒界最会搞营销的那非&lt;span&gt;江小白&lt;/span&gt;莫属，凭借戳人心窝的文案走红，然而人红是非多，江小白与江津酒厂长达3年的商标官司，最近又有了新进展。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/fcfff589f1f44e38a01a1386f7da6cb6\" img_width=\"900\" img_height=\"608\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;有新闻报道江小白LOGO被宣告无效？！难道世间要再无江小白？&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/d2702b897291480d964f6c87338fc93c\" img_width=\"64\" img_height=\"64\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e205b33c31794454a309cf685275ef46\" img_width=\"599\" img_height=\"394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;一时间网友都开始担心，这样一个备受年轻人喜爱的江小白就要离我们而去了？于是江小白便在官微上发表声明用以澄清此事。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/585ffe0afe70473bbce9a50877901adb\" img_width=\"900\" img_height=\"691\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;细读江小白的声明，也能看出江小白注册了这么多商标，偶尔有一个无效，并不影响江小白的正常销售。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/bee4bed91d3743a7a88e9cc95a68fbbd\" img_width=\"690\" img_height=\"809\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;其实被宣告无效的是江小白的&lt;span&gt;第10325554号商标&lt;/span&gt;&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/1a9ea25a59804b59a180d0b01026030a\" img_width=\"787\" img_height=\"741\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而江小白在发展的这些年注册了不少的商标，累计算下来有&lt;span&gt;171个&lt;/span&gt;；但江津酒厂从2012年申请至今才9个“江小白”商标，但两方大部分商标还处在审核中。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/cb6243e276e24b28a70182a9c5eb2d94\" img_width=\"440\" img_height=\"790\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;看完这些，想必各位江小白的老铁也可以放心了，原来这是虚惊一场。但事情最终会变成哪种局势，现在还很难下定论！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/d5f51050f68542c09ae6c924ef49989b\" img_width=\"900\" img_height=\"512\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;江小白一直以戮心的文案切中年轻人的情感痛点，可谓是数不胜数，正是这样一个创新的文案，吸引了不少人对江小白的关注。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/18be3886a8b74394add836c252212f94\" img_width=\"1024\" img_height=\"1545\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/8f203eec39f7428fb7b6712200b5c2f3\" img_width=\"945\" img_height=\"1299\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/65d499b569634dbe8b810b65c96b1397\" img_width=\"1008\" img_height=\"1553\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而最近江小白一直保持着一颗不安分的心，不断的尝试新玩法，与电影、音乐、艺术节等纷纷联合跨界搞营销。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/c95eb52c4d2043cdac69f90a66a98b56\" img_width=\"900\" img_height=\"1628\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/565b553936474e1ab0cbefc965d68d57\" img_width=\"690\" img_height=\"966\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/d3651bb19eb74b3eba26eb1883e21fba\" img_width=\"900\" img_height=\"1260\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/be8cbf68e4fa43fb97dbd56fbdbdff0c\" img_width=\"900\" img_height=\"1394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e6e925b13a504b7ab2278f548834e9d3\" img_width=\"900\" img_height=\"600\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;不仅营销搞的风声水起，米醋还发现江小白自己还推出了米、油产品，感觉世界已经阻挡不住江小白了！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/082783db8d934286b72eac35d6afaa1e\" img_width=\"1024\" img_height=\"708\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/0171c075fc50469fb5361e72c4f1f8d5\" img_width=\"690\" img_height=\"920\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/c293d021aad34080af008d9ec409eff0\" img_width=\"900\" img_height=\"622\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;&lt;span&gt;最后来说说，江小白商标案你怎么看？&lt;/span&gt;&lt;/p&gt;', '', '', '1', '11', '1', '2', '1', '1');
INSERT INTO `t_article` VALUES ('15241201112223333', '1523899', 'admin', '3', '2019-03-30 22:13:18', '2019-04-09 17:15:38', '2019-03-30 22:48:12', '666', '6666', '', '', '1', '70', '1', '4', '1', '1');
INSERT INTO `t_article` VALUES ('15394991112223333', '1523899', 'admin', '3', '2019-04-10 14:40:20', '2019-04-10 14:40:28', '2019-04-10 14:40:28', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '&lt;p&gt;&lt;span&gt;加上上周国办公布的社保降费方案，自2015年起已6次降低或阶段性降低社保费率，5年预计减轻企业社保缴费负担8000亿元。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;4月9日，人社部、财政部、税务总局、国家医保局等四部门有关负责人就《降低社会保险费率综合方案》答记者问中，透露了上述信息。&lt;/p&gt;&lt;p&gt;四部门同时透露，今年的这次降费，是2015年以来幅度最大的一次，且首次同时惠及企业和个人，尤其能减少小微企业及其员工、灵活就业人员的社保缴费负担。&lt;/p&gt;&lt;p&gt;为何说幅度最大？这次城镇职工基本养老保险单位缴费比例，高于16%的省份，可降至16%。&lt;/p&gt;&lt;p&gt;而目前，各省份（含新疆生产建设兵团）企业缴费比例不统一，高的省份20%，多数省份阶段性降至19%，还有个别省份14%左右。&lt;/p&gt;&lt;p&gt;&lt;span&gt;也就是说，单位缴费比例最多可降低4个百分点，不设条件，也不是阶段性政策，而是长期性制度安排。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;仅这一条，全年可减轻企业社保缴费负担1900亿元。加上继续阶段性降低失业保险和工伤保险费率，本次降低养老、失业、工伤保险费率，预计2019年全年可减轻社保缴费负担3000多亿元。&lt;/p&gt;&lt;p&gt;单位缴费，降低企业负担。而社保缴费基数的调整，则直接惠及职工。&lt;/p&gt;&lt;p&gt;《方案》明确，将城镇非私营单位和城镇私营单位就业人员平均工资加权计算的全口径城镇单位就业人员平均工资，作为核定职工缴费基数上下限的指标。&lt;/p&gt;&lt;p&gt;而原来的政策，将非私营单位在岗职工平均工资作为社保缴费基数，未纳入广大私营单位职工的工资，并不公平。&lt;/p&gt;&lt;p&gt;这次调整后，&lt;span&gt;工资水平较低的职工，缴费基数可相应降低，缴费负担减轻。部分企业，特别是部分小微企业或劳动密集型企业，不少职工按照缴费基数下限缴费，企业缴费负担也可进一步减轻，能更多受益。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RN8wJ4FB3gBiRH\" img_width=\"504\" img_height=\"190\" alt=\"5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;举个例子：某地以前将非私营单位在岗职工平均工资6000元，作为社保缴费基数。那么个人缴费基数的下限就是3600元。某职工每月工资3000元，就按照3600元的最低缴费基数算社保费。&lt;/p&gt;&lt;p&gt;调整后，按全口径城镇单位就业人员平均工资5000元，作为新的社保缴费基数。个人缴费基数下限相应降低到3000元，该职工就可按3000元计算缴费金额。&lt;/p&gt;&lt;p&gt;前后对比，月缴费基数减少600元，个人缴费比例8%，月缴费负担相应减轻48元。扣除社保缴费，工资未到个税起征点，那么此人5月1日的到手工资，将实打实地多拿到48元。&lt;/p&gt;&lt;p&gt;&lt;span&gt;若此人所在企业，以个人缴费基数之和确定单位缴费基数，则企业每月缴费基数也相应减少600元，缴费负担可进一步减轻。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;此外，个体工商户和灵活就业人员参加养老保险，可在全口径城镇单位就业人员平均工资的60%至300%范围内选择适当的缴费基数。&lt;/p&gt;&lt;p&gt;&lt;span&gt;不仅平均工资口径调整、标准降低，选择范围也变大，选择低基数的可以进一步减轻缴费负担，收入较高的人员也可以选择较高的缴费基数，来提高自己退休后的养老金水平。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;比如，按上例，如为灵活就业人员，月缴费基数可从6000元改为以3000元下限缴费，则月缴费基数减少3000元，按20%比例缴费，月缴费负担相应减轻600元。&lt;/p&gt;&lt;p&gt;中国宏观经济研究院副研究员关博告诉21世纪经济报道记者，费基调整是此轮社保降费的最大亮点。&lt;/p&gt;&lt;p&gt;“一方面通过全口径核定费基，实现了民营企业，特别是工资性收入低于在岗职工平均工资的小微企业职工结构性降费，减负效果叠加。同时，费基是养老保险制度筹资缴费的关键参数，在降低费率过程中统一费基计算办法，在制度整合方面迈出了重要一步。”关博说。&lt;/p&gt;', '', '', '0', '1', '1', '0', '0', '4');

-- ----------------------------
-- Table structure for t_article_category
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category`;
CREATE TABLE `t_article_category` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(32) NOT NULL DEFAULT '' COMMENT '分类名称',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0-未启用，1-启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(32) NOT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '更新人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='文章分类表';

-- ----------------------------
-- Records of t_article_category
-- ----------------------------
INSERT INTO `t_article_category` VALUES ('1', '国际', '2', '1', '2019-04-07 11:05:29', '444', '2019-04-10 14:11:57', '1523899');
INSERT INTO `t_article_category` VALUES ('4', '财经', '1', '1', '2019-04-10 14:39:05', '1523899', '2019-04-10 14:39:08', '1523899');
INSERT INTO `t_article_category` VALUES ('5', '体育', '1', '1', '2019-04-10 14:39:29', '1523899', '2019-04-10 14:39:31', '1523899');
INSERT INTO `t_article_category` VALUES ('6', '科技', '1', '1', '2019-04-10 14:39:45', '1523899', '2019-04-10 14:39:49', '1523899');

-- ----------------------------
-- Table structure for t_article_check
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='文章审核表';

-- ----------------------------
-- Records of t_article_check
-- ----------------------------
INSERT INTO `t_article_check` VALUES ('38', '1523899', 'admin', '1524120', '666', '6666', '1', '0', '1523899', 'admin', '2019-03-30 22:48:12', '2019-03-30 22:13:19', '1', '', '1');
INSERT INTO `t_article_check` VALUES ('39', '1523899', 'admin', '1534142', '江小白LOGO被宣告无效，世间要再无江小白？！', '&lt;p&gt;说到白酒界最会搞营销的那非&lt;span&gt;江小白&lt;/span&gt;莫属，凭借戳人心窝的文案走红，然而人红是非多，江小白与江津酒厂长达3年的商标官司，最近又有了新进展。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/fcfff589f1f44e38a01a1386f7da6cb6\" img_width=\"900\" img_height=\"608\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;有新闻报道江小白LOGO被宣告无效？！难道世间要再无江小白？&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/d2702b897291480d964f6c87338fc93c\" img_width=\"64\" img_height=\"64\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e205b33c31794454a309cf685275ef46\" img_width=\"599\" img_height=\"394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;一时间网友都开始担心，这样一个备受年轻人喜爱的江小白就要离我们而去了？于是江小白便在官微上发表声明用以澄清此事。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/585ffe0afe70473bbce9a50877901adb\" img_width=\"900\" img_height=\"691\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;细读江小白的声明，也能看出江小白注册了这么多商标，偶尔有一个无效，并不影响江小白的正常销售。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/bee4bed91d3743a7a88e9cc95a68fbbd\" img_width=\"690\" img_height=\"809\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;其实被宣告无效的是江小白的&lt;span&gt;第10325554号商标&lt;/span&gt;&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/1a9ea25a59804b59a180d0b01026030a\" img_width=\"787\" img_height=\"741\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而江小白在发展的这些年注册了不少的商标，累计算下来有&lt;span&gt;171个&lt;/span&gt;；但江津酒厂从2012年申请至今才9个“江小白”商标，但两方大部分商标还处在审核中。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/cb6243e276e24b28a70182a9c5eb2d94\" img_width=\"440\" img_height=\"790\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;看完这些，想必各位江小白的老铁也可以放心了，原来这是虚惊一场。但事情最终会变成哪种局势，现在还很难下定论！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/d5f51050f68542c09ae6c924ef49989b\" img_width=\"900\" img_height=\"512\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;江小白一直以戮心的文案切中年轻人的情感痛点，可谓是数不胜数，正是这样一个创新的文案，吸引了不少人对江小白的关注。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/18be3886a8b74394add836c252212f94\" img_width=\"1024\" img_height=\"1545\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/8f203eec39f7428fb7b6712200b5c2f3\" img_width=\"945\" img_height=\"1299\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/65d499b569634dbe8b810b65c96b1397\" img_width=\"1008\" img_height=\"1553\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而最近江小白一直保持着一颗不安分的心，不断的尝试新玩法，与电影、音乐、艺术节等纷纷联合跨界搞营销。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/c95eb52c4d2043cdac69f90a66a98b56\" img_width=\"900\" img_height=\"1628\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/565b553936474e1ab0cbefc965d68d57\" img_width=\"690\" img_height=\"966\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/d3651bb19eb74b3eba26eb1883e21fba\" img_width=\"900\" img_height=\"1260\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/be8cbf68e4fa43fb97dbd56fbdbdff0c\" img_width=\"900\" img_height=\"1394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e6e925b13a504b7ab2278f548834e9d3\" img_width=\"900\" img_height=\"600\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;不仅营销搞的风声水起，米醋还发现江小白自己还推出了米、油产品，感觉世界已经阻挡不住江小白了！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/082783db8d934286b72eac35d6afaa1e\" img_width=\"1024\" img_height=\"708\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/0171c075fc50469fb5361e72c4f1f8d5\" img_width=\"690\" img_height=\"920\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/c293d021aad34080af008d9ec409eff0\" img_width=\"900\" img_height=\"622\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;&lt;span&gt;最后来说说，江小白商标案你怎么看？&lt;/span&gt;&lt;/p&gt;', '1', '1', '1523899', 'admin', '2019-04-06 21:05:48', '2019-04-06 21:05:21', '1', '', '4');
INSERT INTO `t_article_check` VALUES ('40', '1523899', 'admin', '1539499', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '&lt;p&gt;&lt;span&gt;加上上周国办公布的社保降费方案，自2015年起已6次降低或阶段性降低社保费率，5年预计减轻企业社保缴费负担8000亿元。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;4月9日，人社部、财政部、税务总局、国家医保局等四部门有关负责人就《降低社会保险费率综合方案》答记者问中，透露了上述信息。&lt;/p&gt;&lt;p&gt;四部门同时透露，今年的这次降费，是2015年以来幅度最大的一次，且首次同时惠及企业和个人，尤其能减少小微企业及其员工、灵活就业人员的社保缴费负担。&lt;/p&gt;&lt;p&gt;为何说幅度最大？这次城镇职工基本养老保险单位缴费比例，高于16%的省份，可降至16%。&lt;/p&gt;&lt;p&gt;而目前，各省份（含新疆生产建设兵团）企业缴费比例不统一，高的省份20%，多数省份阶段性降至19%，还有个别省份14%左右。&lt;/p&gt;&lt;p&gt;&lt;span&gt;也就是说，单位缴费比例最多可降低4个百分点，不设条件，也不是阶段性政策，而是长期性制度安排。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;仅这一条，全年可减轻企业社保缴费负担1900亿元。加上继续阶段性降低失业保险和工伤保险费率，本次降低养老、失业、工伤保险费率，预计2019年全年可减轻社保缴费负担3000多亿元。&lt;/p&gt;&lt;p&gt;单位缴费，降低企业负担。而社保缴费基数的调整，则直接惠及职工。&lt;/p&gt;&lt;p&gt;《方案》明确，将城镇非私营单位和城镇私营单位就业人员平均工资加权计算的全口径城镇单位就业人员平均工资，作为核定职工缴费基数上下限的指标。&lt;/p&gt;&lt;p&gt;而原来的政策，将非私营单位在岗职工平均工资作为社保缴费基数，未纳入广大私营单位职工的工资，并不公平。&lt;/p&gt;&lt;p&gt;这次调整后，&lt;span&gt;工资水平较低的职工，缴费基数可相应降低，缴费负担减轻。部分企业，特别是部分小微企业或劳动密集型企业，不少职工按照缴费基数下限缴费，企业缴费负担也可进一步减轻，能更多受益。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RN8wJ4FB3gBiRH\" img_width=\"504\" img_height=\"190\" alt=\"5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;举个例子：某地以前将非私营单位在岗职工平均工资6000元，作为社保缴费基数。那么个人缴费基数的下限就是3600元。某职工每月工资3000元，就按照3600元的最低缴费基数算社保费。&lt;/p&gt;&lt;p&gt;调整后，按全口径城镇单位就业人员平均工资5000元，作为新的社保缴费基数。个人缴费基数下限相应降低到3000元，该职工就可按3000元计算缴费金额。&lt;/p&gt;&lt;p&gt;前后对比，月缴费基数减少600元，个人缴费比例8%，月缴费负担相应减轻48元。扣除社保缴费，工资未到个税起征点，那么此人5月1日的到手工资，将实打实地多拿到48元。&lt;/p&gt;&lt;p&gt;&lt;span&gt;若此人所在企业，以个人缴费基数之和确定单位缴费基数，则企业每月缴费基数也相应减少600元，缴费负担可进一步减轻。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;此外，个体工商户和灵活就业人员参加养老保险，可在全口径城镇单位就业人员平均工资的60%至300%范围内选择适当的缴费基数。&lt;/p&gt;&lt;p&gt;&lt;span&gt;不仅平均工资口径调整、标准降低，选择范围也变大，选择低基数的可以进一步减轻缴费负担，收入较高的人员也可以选择较高的缴费基数，来提高自己退休后的养老金水平。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;比如，按上例，如为灵活就业人员，月缴费基数可从6000元改为以3000元下限缴费，则月缴费基数减少3000元，按20%比例缴费，月缴费负担相应减轻600元。&lt;/p&gt;&lt;p&gt;中国宏观经济研究院副研究员关博告诉21世纪经济报道记者，费基调整是此轮社保降费的最大亮点。&lt;/p&gt;&lt;p&gt;“一方面通过全口径核定费基，实现了民营企业，特别是工资性收入低于在岗职工平均工资的小微企业职工结构性降费，减负效果叠加。同时，费基是养老保险制度筹资缴费的关键参数，在降低费率过程中统一费基计算办法，在制度整合方面迈出了重要一步。”关博说。&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-10 14:40:28', '2019-04-10 14:40:20', '1', '', '5');

-- ----------------------------
-- Table structure for t_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_collection`;
CREATE TABLE `t_collection` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(64) NOT NULL DEFAULT '0' COMMENT '用户id',
  `article_id` bigint(64) NOT NULL DEFAULT '0' COMMENT '文章id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_article_id` (`user_id`,`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of t_collection
-- ----------------------------
INSERT INTO `t_collection` VALUES ('2', '1523899', '1524120', '2019-03-31 00:48:54', '666');

-- ----------------------------
-- Table structure for t_comment
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
INSERT INTO `t_comment` VALUES ('1525037', '1524965', '1524120', '6666', '', '2019-03-31 13:24:38', '1');
INSERT INTO `t_comment` VALUES ('1525052', '1524965', '1524252', '8888', '', '2019-03-31 13:37:52', '1');
INSERT INTO `t_comment` VALUES ('1538170', '1523899', '1534142', '888', '', '2019-04-09 16:33:56', '0');

-- ----------------------------
-- Table structure for t_link
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='链接表';

-- ----------------------------
-- Records of t_link
-- ----------------------------
INSERT INTO `t_link` VALUES ('14', '百度', 'http://www.baidu.com', '1', '1', '2019-03-23 22:29:43', '1523899', '2019-03-23 22:30:41', '1523899');
INSERT INTO `t_link` VALUES ('15', '腾讯', 'http://www.qq.com', '2', '0', '2019-03-23 22:30:04', '1523899', '2019-03-30 22:11:10', '1523899');
INSERT INTO `t_link` VALUES ('16', '新浪', 'http://www.sina.com', '3', '1', '2019-03-23 22:30:36', '1523899', '2019-03-23 22:30:46', '1523899');

-- ----------------------------
-- Table structure for t_login_history
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
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_login_history
-- ----------------------------
INSERT INTO `t_login_history` VALUES ('178', '1523899', 'admin', '2019-03-30 20:50:40', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('179', '1523899', 'admin', '2019-03-30 20:51:45', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('180', '1523899', 'admin', '2019-03-30 20:56:56', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('181', '1523899', 'admin', '2019-03-30 20:59:54', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('182', '1523899', 'admin', '2019-03-30 21:00:56', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('183', '1523899', 'admin', '2019-03-30 22:44:53', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('184', '1523899', 'admin', '2019-03-30 22:45:17', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('185', '1523899', 'admin', '2019-03-31 02:41:55', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('186', '1523899', 'admin', '2019-03-31 02:47:58', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('187', '1523899', 'admin', '2019-03-31 11:14:03', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('188', '1523899', 'admin', '2019-03-31 11:15:03', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('189', '1524965', 'gangtiexia', '2019-03-31 12:16:08', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('190', '1523899', 'admin', '2019-03-31 12:19:38', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('191', '1524965', 'gangtiexia', '2019-03-31 13:10:48', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('192', '1525055', 'lvdengxia', '2019-03-31 13:38:25', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('193', '1523899', 'admin', '2019-03-31 13:43:44', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('194', '1523899', 'admin', '2019-03-31 14:32:12', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('195', '1523899', 'admin', '2019-04-06 21:03:39', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('196', '1523899', 'admin', '2019-04-07 10:33:51', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('197', '1523899', 'admin', '2019-04-07 12:08:45', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('198', '1523899', 'admin', '2019-04-09 17:10:29', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; WOW64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('199', '1523899', 'admin', '2019-04-09 17:10:30', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; WOW64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('200', '1523899', 'admin', '2019-04-09 19:06:57', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; WOW64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('201', '1523899', 'admin', '2019-04-10 13:58:51', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; WOW64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('202', '1523899', 'admin', '2019-04-10 14:45:15', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; WOW64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36');

-- ----------------------------
-- Table structure for t_message
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='消息通知表';

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES ('32', '1', '1523899', 'admin', '1523899', '3', '1524120', '0', '666', '2019-03-30 22:48:12', '');
INSERT INTO `t_message` VALUES ('33', '1', '1523899', '钢铁侠', '1524965', '1', '1524120', '0', '666', '2019-03-31 13:24:38', '6666');
INSERT INTO `t_message` VALUES ('34', '1', '1523899', '钢铁侠', '1524965', '1', '1524252', '0', '66666', '2019-03-31 13:37:52', '8888');
INSERT INTO `t_message` VALUES ('35', '1', '1523899', 'admin', '1523899', '3', '1534142', '0', '江小白LOGO被宣告无效，世间要再无江小白？！', '2019-04-06 21:05:48', '');
INSERT INTO `t_message` VALUES ('36', '0', '1523899', 'admin', '1523899', '3', '1539499', '0', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '2019-04-10 14:40:28', '');

-- ----------------------------
-- Table structure for t_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_log`;
CREATE TABLE `t_operate_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `content` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作内容',
  `operate_type` int(11) NOT NULL DEFAULT '0' COMMENT '操作类型',
  `ip` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of t_operate_log
-- ----------------------------
INSERT INTO `t_operate_log` VALUES ('81', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=编辑权限, url=/user/permission/doEdit, pid=2, marker=null, level=2, sort=100, icon=layui-icon-note, type=2)', '101', '2130706433', '2019-03-30 21:18:54');
INSERT INTO `t_operate_log` VALUES ('84', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=权限删除, url=/user/permission/doDelete, pid=2, marker=null, level=2, sort=100, icon=layui-icon-senior, type=2)', '101', '2130706433', '2019-03-30 21:31:42');
INSERT INTO `t_operate_log` VALUES ('85', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=基本设置, url=/user/set, pid=0, marker=null, level=1, sort=100, icon=layui-icon-rate-half, type=1)', '101', '2130706433', '2019-03-30 21:35:11');
INSERT INTO `t_operate_log` VALUES ('87', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=我的文章, url=/user/article/mine, pid=0, marker=null, level=1, sort=3, icon=layui-icon-template-1, type=1)', '101', '2130706433', '2019-03-30 21:38:25');
INSERT INTO `t_operate_log` VALUES ('88', '1523899', 'admin', 'admin(admin)编辑权限：权限id：1，编辑内容：权限排序：100-->0；', '102', '2130706433', '2019-03-30 21:45:33');
INSERT INTO `t_operate_log` VALUES ('89', '1523899', 'admin', 'admin(admin)编辑权限：权限id：3，编辑内容：权限排序：100-->0；', '102', '2130706433', '2019-03-30 21:46:19');
INSERT INTO `t_operate_log` VALUES ('90', '1523899', 'admin', 'admin(admin)编辑权限：权限id：2，编辑内容：权限排序：50-->0；', '102', '2130706433', '2019-03-30 21:50:55');
INSERT INTO `t_operate_log` VALUES ('91', '1523899', 'admin', 'admin(admin)编辑权限：权限id：6，编辑内容：权限排序：2-->100；', '102', '2130706433', '2019-03-30 21:51:18');
INSERT INTO `t_operate_log` VALUES ('92', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=我的消息, url=/user/message, pid=0, marker=null, level=1, sort=4, icon=layui-icon-login-wechat, type=1)', '101', '2130706433', '2019-03-30 21:52:39');
INSERT INTO `t_operate_log` VALUES ('93', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=系统公告, url=/user/systemNotice, pid=0, marker=null, level=1, sort=5, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 21:53:42');
INSERT INTO `t_operate_log` VALUES ('94', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=审核文章, url=/user/articleCheck, pid=0, marker=null, level=1, sort=6, icon=layui-icon-face-surprised, type=1)', '101', '2130706433', '2019-03-30 21:54:09');
INSERT INTO `t_operate_log` VALUES ('95', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=文章编辑, url=/user/article/doEdit, pid=7, marker=null, level=2, sort=100, icon=layui-icon-rate-half, type=1)', '101', '2130706433', '2019-03-30 21:54:59');
INSERT INTO `t_operate_log` VALUES ('96', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=发表新文章, url=/user/article/doSave, pid=7, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:55:44');
INSERT INTO `t_operate_log` VALUES ('97', '1523899', 'admin', 'admin(admin)编辑权限：权限id：11，编辑内容：权限类型：2-->1；', '102', '2130706433', '2019-03-30 21:55:57');
INSERT INTO `t_operate_log` VALUES ('98', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=角色管理, url=/user/role, pid=1, marker=null, level=2, sort=100, icon=layui-icon-vercode, type=1)', '101', '2130706433', '2019-03-30 21:57:25');
INSERT INTO `t_operate_log` VALUES ('99', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=分配角色, url=/user/role/allocateRole, pid=13, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:57:53');
INSERT INTO `t_operate_log` VALUES ('100', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=分配权限, url=/user/role/allocatePermission, pid=13, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:58:13');
INSERT INTO `t_operate_log` VALUES ('101', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=编辑角色, url=/user/role/doEdit, pid=13, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:58:37');
INSERT INTO `t_operate_log` VALUES ('102', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=删除角色, url=/user/role/doDelete, pid=13, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:58:57');
INSERT INTO `t_operate_log` VALUES ('103', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=删除文章, url=/user/article/doDelete, pid=7, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 21:59:23');
INSERT INTO `t_operate_log` VALUES ('104', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=文章置顶/加精, url=/user/article/set, pid=0, marker=null, level=1, sort=200, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 22:00:40');
INSERT INTO `t_operate_log` VALUES ('105', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=积分历史, url=/user/scoreHistory, pid=0, marker=null, level=1, sort=9, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:01:07');
INSERT INTO `t_operate_log` VALUES ('106', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=我的图片, url=/user/myPicturePage, pid=0, marker=null, level=1, sort=10, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:01:29');
INSERT INTO `t_operate_log` VALUES ('107', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=系统管理, url=, pid=0, marker=null, level=1, sort=300, icon=layui-icon-star-fill, type=0)', '101', '2130706433', '2019-03-30 22:01:51');
INSERT INTO `t_operate_log` VALUES ('108', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=操作日志, url=/user/operateLog, pid=22, marker=null, level=2, sort=301, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:02:20');
INSERT INTO `t_operate_log` VALUES ('109', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=系统配置, url=/user/systemConfig, pid=22, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:02:40');
INSERT INTO `t_operate_log` VALUES ('110', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=链接管理, url=/user/link/index, pid=22, marker=null, level=2, sort=300, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:03:26');
INSERT INTO `t_operate_log` VALUES ('111', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=新增链接, url=/user/link/doAdd, pid=25, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:03:47');
INSERT INTO `t_operate_log` VALUES ('112', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=编辑链接, url=/user/link/doEdit, pid=25, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:04:07');
INSERT INTO `t_operate_log` VALUES ('113', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=删除链接, url=/user/link/doDelete, pid=25, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 22:04:26');
INSERT INTO `t_operate_log` VALUES ('114', '1523899', 'admin', 'admin(admin)编辑权限：权限id：27，编辑内容：权限类型：2-->1；', '102', '2130706433', '2019-03-30 22:04:39');
INSERT INTO `t_operate_log` VALUES ('115', '1523899', 'admin', 'admin(admin)编辑权限：权限id：26，编辑内容：权限类型：2-->1；', '102', '2130706433', '2019-03-30 22:04:47');
INSERT INTO `t_operate_log` VALUES ('116', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=修改链接状态, url=/user/link/changeStatus, pid=25, marker=null, level=2, sort=303, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-30 22:05:13');
INSERT INTO `t_operate_log` VALUES ('117', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=用户管理, url=/user/manage, pid=0, marker=null, level=1, sort=400, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:06:38');
INSERT INTO `t_operate_log` VALUES ('118', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=修改用户状态, url=/user/manage/changeStatus, pid=30, marker=null, level=2, sort=401, icon=layui-icon-star-fill, type=1)', '101', '2130706433', '2019-03-30 22:07:17');
INSERT INTO `t_operate_log` VALUES ('119', '1523899', 'admin', 'admin(admin)编辑权限：权限id：31，编辑内容：权限类型：2-->1；', '102', '2130706433', '2019-03-30 22:08:22');
INSERT INTO `t_operate_log` VALUES ('120', '1523899', 'admin', 'admin(admin)编辑权限：权限id：30，编辑内容：权限图标：layui-icon-username-->layui-icon-star-fill；', '102', '2130706433', '2019-03-30 22:09:58');
INSERT INTO `t_operate_log` VALUES ('121', '1523899', 'admin', 'admin(admin)链接上线：下线链接，链接id=15', '504', '2130706433', '2019-03-30 22:11:10');
INSERT INTO `t_operate_log` VALUES ('122', '1523899', 'admin', 'admin(admin)编辑系统配置：图片上传路径：/home/tomcat/myimage/-->C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\；', '401', '2130706433', '2019-03-31 00:33:52');
INSERT INTO `t_operate_log` VALUES ('123', '1523899', 'admin', 'admin(admin)编辑权限：权限id：25，编辑内容：权限图标：layui-icon-next-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:37:22');
INSERT INTO `t_operate_log` VALUES ('124', '1523899', 'admin', 'admin(admin)编辑权限：权限id：13，编辑内容：权限图标：layui-icon-group-->layui-icon-vercode；', '102', '2130706433', '2019-03-31 00:37:55');
INSERT INTO `t_operate_log` VALUES ('125', '1523899', 'admin', 'admin(admin)编辑权限：权限id：21，编辑内容：权限图标：layui-icon-picture-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:38:22');
INSERT INTO `t_operate_log` VALUES ('126', '1523899', 'admin', 'admin(admin)编辑权限：权限id：19，编辑内容：权限图标：layui-icon-rate-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:38:33');
INSERT INTO `t_operate_log` VALUES ('127', '1523899', 'admin', 'admin(admin)编辑权限：权限id：22，编辑内容：权限路径：  -->；权限图标：layui-icon-file-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:38:55');
INSERT INTO `t_operate_log` VALUES ('128', '1523899', 'admin', 'admin(admin)编辑权限：权限id：18，编辑内容：权限图标：layui-icon-delete-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:39:24');
INSERT INTO `t_operate_log` VALUES ('129', '1523899', 'admin', 'admin(admin)编辑权限：权限id：5，编辑内容：权限图标：layui-icon-delete-->layui-icon-senior；', '102', '2130706433', '2019-03-31 00:39:38');
INSERT INTO `t_operate_log` VALUES ('130', '1523899', 'admin', 'admin(admin)编辑权限：权限id：28，编辑内容：权限图标：layui-icon-delete-->layui-icon-star-fill；', '102', '2130706433', '2019-03-31 00:39:55');
INSERT INTO `t_operate_log` VALUES ('131', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=新增角色, url=/user/role/doAdd, pid=13, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-31 00:58:16');
INSERT INTO `t_operate_log` VALUES ('132', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=测试一, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-03-31 00:58:29');
INSERT INTO `t_operate_log` VALUES ('133', '1523899', 'admin', 'admin(admin)编辑角色：2,测试一-->测试一-0', '202', '2130706433', '2019-03-31 01:08:25');
INSERT INTO `t_operate_log` VALUES ('134', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=测试一-0, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-03-31 01:08:50');
INSERT INTO `t_operate_log` VALUES ('135', '1523899', 'admin', 'admin(admin)编辑角色：1,超级管理员-->超级管理员2', '202', '2130706433', '2019-03-31 01:09:08');
INSERT INTO `t_operate_log` VALUES ('136', '1523899', 'admin', 'admin(admin)编辑角色：1,超级管理员2-->超级管理员', '202', '2130706433', '2019-03-31 01:09:14');
INSERT INTO `t_operate_log` VALUES ('137', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=注册用户, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-03-31 12:20:02');
INSERT INTO `t_operate_log` VALUES ('138', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=分配角色, url=/user/role/allocateRole, pid=30, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-03-31 12:31:44');
INSERT INTO `t_operate_log` VALUES ('139', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：{}-->body {transform: scale&#40;1.5) translate&#40;200px, 100px); }；', '401', '2130706433', '2019-04-06 21:07:33');
INSERT INTO `t_operate_log` VALUES ('140', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {transform: scale&#40;1.5) translate&#40;200px, 100px); }-->body {transform: scale&#40;0.8) translate&#40;200px, 100px); }；', '401', '2130706433', '2019-04-06 21:07:51');
INSERT INTO `t_operate_log` VALUES ('141', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {transform: scale&#40;0.8) translate&#40;200px, 100px); }-->body {\ntransform: scale&#40;0.8) ;\n }；', '401', '2130706433', '2019-04-06 21:08:33');
INSERT INTO `t_operate_log` VALUES ('142', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {\ntransform: scale&#40;0.8) ;\n }-->body {\ntransform: scale&#40;0.9) ;\n }；', '401', '2130706433', '2019-04-06 21:08:45');
INSERT INTO `t_operate_log` VALUES ('143', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {\ntransform: scale&#40;0.9) ;\n }-->{}；', '401', '2130706433', '2019-04-06 21:08:56');
INSERT INTO `t_operate_log` VALUES ('144', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：{}-->body {\n   transform: scale&#40;1.1);\n   transform-origin: 0 0;\n}；', '401', '2130706433', '2019-04-06 21:17:52');
INSERT INTO `t_operate_log` VALUES ('145', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {\n   transform: scale&#40;1.1);\n   transform-origin: 0 0;\n}-->body {\n   transform: scale&#40;0.9);\n   transform-origin: 0 0;\n}；', '401', '2130706433', '2019-04-06 21:18:04');
INSERT INTO `t_operate_log` VALUES ('146', '1523899', 'admin', 'admin(admin)编辑系统配置：网站公共代码：body {\n   transform: scale&#40;0.9);\n   transform-origin: 0 0;\n}-->body {\n\n}；', '401', '2130706433', '2019-04-06 21:18:45');
INSERT INTO `t_operate_log` VALUES ('147', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=文章分类管理, url=/user/articleCategory/index, pid=22, marker=null, level=2, sort=100, icon=layui-icon-menu-fill, type=1)', '101', '2130706433', '2019-04-07 10:47:02');
INSERT INTO `t_operate_log` VALUES ('148', '1523899', 'admin', 'admin(admin)编辑权限：权限id：34，编辑内容：权限排序：500-->100；', '102', '2130706433', '2019-04-07 10:54:16');
INSERT INTO `t_operate_log` VALUES ('149', '1523899', 'admin', 'admin(admin)编辑权限：权限id：24，编辑内容：权限图标：layui-icon-fire-->layui-icon-star-fill；', '102', '2130706433', '2019-04-07 10:54:47');
INSERT INTO `t_operate_log` VALUES ('150', '1523899', 'admin', 'admin(admin)编辑权限：权限id：34，编辑内容：权限名称：文章分类-->文章分类管理；', '102', '2130706433', '2019-04-07 10:57:48');
INSERT INTO `t_operate_log` VALUES ('151', '1523899', 'admin', 'admin(admin)编辑权限：权限id：34，编辑内容：权限路径：/user/articleCategory-->/user/articleCategory/index；', '102', '2130706433', '2019-04-07 12:11:38');
INSERT INTO `t_operate_log` VALUES ('152', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=新增文章分类, url=/user/articleCategory/doAdd, pid=34, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-04-09 15:12:26');
INSERT INTO `t_operate_log` VALUES ('153', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=编辑文章分类, url=/user/articleCategory/doEdit, pid=34, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-04-09 15:12:50');
INSERT INTO `t_operate_log` VALUES ('154', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=删除文章分类, url=/user/articleCategory/doDelete, pid=34, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-04-09 15:13:10');
INSERT INTO `t_operate_log` VALUES ('155', '1523899', 'admin', 'admin(admin)文章分类编辑：排序：0-->2；', '602', '2130706433', '2019-04-09 15:13:16');
INSERT INTO `t_operate_log` VALUES ('156', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称999', '601', '2130706433', '2019-04-09 15:21:55');
INSERT INTO `t_operate_log` VALUES ('157', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称000', '601', '2130706433', '2019-04-09 15:29:44');
INSERT INTO `t_operate_log` VALUES ('158', '1523899', 'admin', 'admin(admin)文章分类删除：ArticleCategory(id=3, categoryName=000, sort=1, status=0)', '603', '2130706433', '2019-04-09 15:29:47');
INSERT INTO `t_operate_log` VALUES ('159', '1523899', 'admin', 'admin(admin)新增权限：Permission(permissionName=修改分类状态, url=/user/articleCategory/changeStatus, pid=34, marker=null, level=2, sort=100, icon=layui-icon-star-fill, type=2)', '101', '2130706433', '2019-04-09 16:12:57');
INSERT INTO `t_operate_log` VALUES ('160', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类国际', '603', '2130706433', '2019-04-09 16:15:00');
INSERT INTO `t_operate_log` VALUES ('161', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类999', '603', '2130706433', '2019-04-10 13:59:30');
INSERT INTO `t_operate_log` VALUES ('162', '1523899', 'admin', 'admin(admin)文章分类下线：下线文章分类：国际', '603', '2130706433', '2019-04-10 14:02:43');
INSERT INTO `t_operate_log` VALUES ('163', '1523899', 'admin', 'admin(admin)文章分类下线：下线文章分类：999', '603', '2130706433', '2019-04-10 14:03:35');
INSERT INTO `t_operate_log` VALUES ('164', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类国际', '603', '2130706433', '2019-04-10 14:11:57');
INSERT INTO `t_operate_log` VALUES ('165', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类999', '603', '2130706433', '2019-04-10 14:11:59');
INSERT INTO `t_operate_log` VALUES ('166', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称财经', '601', '2130706433', '2019-04-10 14:39:05');
INSERT INTO `t_operate_log` VALUES ('167', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类财经', '603', '2130706433', '2019-04-10 14:39:08');
INSERT INTO `t_operate_log` VALUES ('168', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称体育', '601', '2130706433', '2019-04-10 14:39:29');
INSERT INTO `t_operate_log` VALUES ('169', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类体育', '603', '2130706433', '2019-04-10 14:39:31');
INSERT INTO `t_operate_log` VALUES ('170', '1523899', 'admin', 'admin(admin)文章分类删除：ArticleCategory(id=2, categoryName=999, sort=1, status=1)', '603', '2130706433', '2019-04-10 14:39:38');
INSERT INTO `t_operate_log` VALUES ('171', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称科技', '601', '2130706433', '2019-04-10 14:39:45');
INSERT INTO `t_operate_log` VALUES ('172', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类科技', '603', '2130706433', '2019-04-10 14:39:49');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(64) NOT NULL COMMENT '权限名',
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '权限管理', '  ', '0', '1', '100', 'layui-icon-app', '0', '2019-03-30 19:52:14', '1523899', '2019-03-30 21:45:33', '1523899');
INSERT INTO `t_permission` VALUES ('2', '权限列表', '/user/permission', '1', '2', '50', 'layui-icon-spread-left', '1', '2019-03-30 19:53:13', '1523899', '2019-03-30 21:50:55', '1523899');
INSERT INTO `t_permission` VALUES ('3', '新增权限', '/user/permission/doAdd', '2', '3', '100', 'layui-icon-app', '2', '2019-03-30 20:56:02', '1523899', '2019-03-30 21:46:19', '1523899');
INSERT INTO `t_permission` VALUES ('4', '编辑权限', '/user/permission/doEdit', '2', '2', '100', 'layui-icon-note', '2', '2019-03-30 21:18:54', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('5', '权限删除', '/user/permission/doDelete', '2', '2', '100', 'layui-icon-delete', '2', '2019-03-30 21:31:42', '1523899', '2019-03-31 00:39:38', '1523899');
INSERT INTO `t_permission` VALUES ('6', '基本设置', '/user/set', '0', '1', '2', 'layui-icon-rate-half', '1', '2019-03-30 21:35:11', '1523899', '2019-03-30 21:51:18', '1523899');
INSERT INTO `t_permission` VALUES ('7', '我的文章', '/user/article/mine', '0', '1', '3', 'layui-icon-template-1', '1', '2019-03-30 21:38:25', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('8', '我的消息', '/user/message', '0', '1', '4', 'layui-icon-login-wechat', '1', '2019-03-30 21:52:39', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('9', '系统公告', '/user/systemNotice', '0', '1', '5', 'layui-icon-star-fill', '1', '2019-03-30 21:53:41', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('10', '审核文章', '/user/articleCheck', '0', '1', '6', 'layui-icon-face-surprised', '1', '2019-03-30 21:54:08', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('11', '文章编辑', '/user/article/doEdit', '7', '2', '100', 'layui-icon-rate-half', '2', '2019-03-30 21:54:59', '1523899', '2019-03-30 21:55:57', '1523899');
INSERT INTO `t_permission` VALUES ('12', '发表新文章', '/user/article/doSave', '7', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:55:44', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('13', '角色管理', '/user/role', '1', '2', '100', 'layui-icon-group', '1', '2019-03-30 21:57:25', '1523899', '2019-03-31 00:37:55', '1523899');
INSERT INTO `t_permission` VALUES ('14', '分配角色', '/user/role/allocateRole', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:57:53', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('15', '分配权限', '/user/role/allocatePermission', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:13', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('16', '编辑角色', '/user/role/doEdit', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:37', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('17', '删除角色', '/user/role/doDelete', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 21:58:57', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('18', '删除文章', '/user/article/doDelete', '7', '2', '100', 'layui-icon-delete', '2', '2019-03-30 21:59:23', '1523899', '2019-03-31 00:39:24', '1523899');
INSERT INTO `t_permission` VALUES ('19', '文章置顶/加精', '/user/article/set', '0', '1', '200', 'layui-icon-rate', '2', '2019-03-30 22:00:40', '1523899', '2019-03-31 00:38:33', '1523899');
INSERT INTO `t_permission` VALUES ('20', '积分历史', '/user/scoreHistory', '0', '1', '9', 'layui-icon-star-fill', '1', '2019-03-30 22:01:07', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('21', '我的图片', '/user/myPicturePage', '0', '1', '10', 'layui-icon-picture', '1', '2019-03-30 22:01:29', '1523899', '2019-03-31 00:38:22', '1523899');
INSERT INTO `t_permission` VALUES ('22', '系统管理', '  ', '0', '1', '300', 'layui-icon-file', '0', '2019-03-30 22:01:51', '1523899', '2019-03-31 00:38:55', '1523899');
INSERT INTO `t_permission` VALUES ('23', '操作日志', '/user/operateLog', '22', '2', '301', 'layui-icon-star-fill', '1', '2019-03-30 22:02:20', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('24', '系统配置', '/user/systemConfig', '22', '2', '100', 'layui-icon-fire', '1', '2019-03-30 22:02:40', '1523899', '2019-04-07 10:54:47', '1523899');
INSERT INTO `t_permission` VALUES ('25', '链接管理', '/user/link/index', '22', '2', '300', 'layui-icon-next', '1', '2019-03-30 22:03:26', '1523899', '2019-03-31 00:37:22', '1523899');
INSERT INTO `t_permission` VALUES ('26', '新增链接', '/user/link/doAdd', '25', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 22:03:47', '1523899', '2019-03-30 22:04:47', '1523899');
INSERT INTO `t_permission` VALUES ('27', '编辑链接', '/user/link/doEdit', '25', '2', '100', 'layui-icon-star-fill', '2', '2019-03-30 22:04:07', '1523899', '2019-03-30 22:04:39', '1523899');
INSERT INTO `t_permission` VALUES ('28', '删除链接', '/user/link/doDelete', '25', '2', '100', 'layui-icon-delete', '2', '2019-03-30 22:04:26', '1523899', '2019-03-31 00:39:55', '1523899');
INSERT INTO `t_permission` VALUES ('29', '修改链接状态', '/user/link/changeStatus', '25', '2', '303', 'layui-icon-star-fill', '2', '2019-03-30 22:05:12', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('30', '用户管理', '/user/manage', '0', '1', '400', 'layui-icon-username', '1', '2019-03-30 22:06:38', '1523899', '2019-03-30 22:09:58', '1523899');
INSERT INTO `t_permission` VALUES ('31', '修改用户状态', '/user/manage/changeStatus', '30', '2', '401', 'layui-icon-star-fill', '2', '2019-03-30 22:07:17', '1523899', '2019-03-30 22:08:22', '1523899');
INSERT INTO `t_permission` VALUES ('32', '新增角色', '/user/role/doAdd', '13', '2', '100', 'layui-icon-star-fill', '2', '2019-03-31 00:58:16', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('33', '分配角色', '/user/role/allocateRole', '30', '2', '100', 'layui-icon-star-fill', '2', '2019-03-31 12:31:44', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('34', '文章分类', '/user/articleCategory', '22', '2', '500', 'layui-icon-menu-fill', '1', '2019-04-07 10:47:02', '1523899', '2019-04-07 12:11:38', '1523899');
INSERT INTO `t_permission` VALUES ('35', '新增文章分类', '/user/articleCategory/doAdd', '34', '2', '100', 'layui-icon-star-fill', '2', '2019-04-09 15:12:26', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('36', '编辑文章分类', '/user/articleCategory/doEdit', '34', '2', '100', 'layui-icon-star-fill', '2', '2019-04-09 15:12:50', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('37', '删除文章分类', '/user/articleCategory/doDelete', '34', '2', '100', 'layui-icon-star-fill', '2', '2019-04-09 15:13:10', '1523899', null, '0');
INSERT INTO `t_permission` VALUES ('38', '修改分类状态', '/user/articleCategory/changeStatus', '34', '2', '100', 'layui-icon-star-fill', '2', '2019-04-09 16:12:57', '1523899', null, '0');

-- ----------------------------
-- Table structure for t_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `picture_url` varchar(200) NOT NULL DEFAULT '' COMMENT '图片访问路径',
  `picture_path` varchar(200) NOT NULL DEFAULT '' COMMENT '图片保存路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='图片信息表';

-- ----------------------------
-- Records of t_picture
-- ----------------------------
INSERT INTO `t_picture` VALUES ('19', '2019-03-31 14:17:57', '1524965', 'http://www.panzhigao.vip/myimage/20190331141757933.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190331141757933.jpg');
INSERT INTO `t_picture` VALUES ('20', '2019-03-31 14:18:13', '1524965', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190331141813320.jpg');

-- ----------------------------
-- Table structure for t_praise
-- ----------------------------
DROP TABLE IF EXISTS `t_praise`;
CREATE TABLE `t_praise` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章id',
  `comment_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1525054 DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of t_praise
-- ----------------------------
INSERT INTO `t_praise` VALUES ('1525050', '1524120', '1525037', '1524965', '2019-03-31 13:37:12');
INSERT INTO `t_praise` VALUES ('1525053', '1524252', '1525052', '1524965', '2019-03-31 13:37:54');

-- ----------------------------
-- Table structure for t_role
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '2019-03-30 19:56:04', '1523899', '2019-03-31 01:09:14', '1523899', '1');
INSERT INTO `t_role` VALUES ('2', '注册用户', '2019-03-31 12:20:02', '1523899', null, '0', '0');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(10) NOT NULL DEFAULT '0' COMMENT '角色id',
  `permission_id` bigint(10) NOT NULL DEFAULT '0' COMMENT '权限id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

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

-- ----------------------------
-- Table structure for t_score_history
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
  PRIMARY KEY (`id`),
  KEY `idex_user_id_type_score_date` (`user_id`,`type`,`score_date`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8 COMMENT='积分历史表';

-- ----------------------------
-- Records of t_score_history
-- ----------------------------
INSERT INTO `t_score_history` VALUES ('245', '1523898', '5', '注册', '20', '2019-03-30', '2019-03-30 18:41:28');
INSERT INTO `t_score_history` VALUES ('247', '1523899', '1', '登陆', '5', '2019-03-30', '2019-03-30 20:51:45');
INSERT INTO `t_score_history` VALUES ('248', '1523899', '4', '签到', '5', '2019-03-30', '2019-03-30 22:12:06');
INSERT INTO `t_score_history` VALUES ('249', '1523899', '2', '发表文章成功', '5', '2019-03-30', '2019-03-30 22:48:12');
INSERT INTO `t_score_history` VALUES ('250', '1523899', '4', '签到', '5', '2019-03-31', '2019-03-31 00:36:40');
INSERT INTO `t_score_history` VALUES ('251', '1523899', '1', '登陆', '5', '2019-03-31', '2019-03-31 02:41:55');
INSERT INTO `t_score_history` VALUES ('253', '1524964', '5', '注册', '20', '2019-03-31', '2019-03-31 12:16:08');
INSERT INTO `t_score_history` VALUES ('254', '1524965', '1', '登陆', '5', '2019-03-31', '2019-03-31 12:16:08');
INSERT INTO `t_score_history` VALUES ('255', '1524965', '4', '签到', '5', '2019-03-31', '2019-03-31 13:11:18');
INSERT INTO `t_score_history` VALUES ('256', '1524965', '3', '回帖', '2', '2019-03-31', '2019-03-31 13:24:38');
INSERT INTO `t_score_history` VALUES ('257', '1524965', '3', '回帖', '2', '2019-03-31', '2019-03-31 13:37:52');
INSERT INTO `t_score_history` VALUES ('258', '1525055', '5', '注册', '20', '2019-03-31', '2019-03-31 13:38:25');
INSERT INTO `t_score_history` VALUES ('259', '1525055', '1', '登陆', '5', '2019-03-31', '2019-03-31 13:38:25');
INSERT INTO `t_score_history` VALUES ('260', '1525055', '4', '签到', '5', '2019-03-31', '2019-03-31 13:38:36');
INSERT INTO `t_score_history` VALUES ('261', '1523899', '1', '登陆', '5', '2019-04-06', '2019-04-06 21:03:39');
INSERT INTO `t_score_history` VALUES ('262', '1523899', '2', '发表文章成功', '5', '2019-04-06', '2019-04-06 21:05:48');
INSERT INTO `t_score_history` VALUES ('263', '1523899', '4', '签到', '5', '2019-04-06', '2019-04-06 21:25:03');
INSERT INTO `t_score_history` VALUES ('264', '1523899', '1', '登陆', '5', '2019-04-07', '2019-04-07 10:33:51');
INSERT INTO `t_score_history` VALUES ('265', '1523899', '3', '回帖', '2', '2019-04-09', '2019-04-09 16:33:56');
INSERT INTO `t_score_history` VALUES ('266', '1523899', '1', '登陆', '5', '2019-04-09', '2019-04-09 17:10:29');
INSERT INTO `t_score_history` VALUES ('267', '1523899', '1', '登陆', '5', '2019-04-10', '2019-04-10 13:58:51');
INSERT INTO `t_score_history` VALUES ('268', '1523899', '2', '发表文章成功', '5', '2019-04-10', '2019-04-10 14:40:28');

-- ----------------------------
-- Table structure for t_system_config
-- ----------------------------
DROP TABLE IF EXISTS `t_system_config`;
CREATE TABLE `t_system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '网站标题',
  `web_name` varchar(128) NOT NULL DEFAULT '' COMMENT '网站名称',
  `web_title` varchar(128) NOT NULL DEFAULT '' COMMENT '网站标题',
  `keywords` varchar(512) NOT NULL DEFAULT '' COMMENT '网站关键字',
  `description` varchar(512) NOT NULL DEFAULT '' COMMENT '网站描述',
  `image_upload_dir` varchar(512) NOT NULL DEFAULT '' COMMENT '图片上传路径',
  `record_info` varchar(128) NOT NULL DEFAULT '' COMMENT '备案信息',
  `web_code` varchar(2048) NOT NULL DEFAULT '' COMMENT '网站公共代码',
  `burying_point_code` varchar(2048) NOT NULL DEFAULT '' COMMENT '网站埋点代码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of t_system_config
-- ----------------------------
INSERT INTO `t_system_config` VALUES ('1', '好好学习', '好好学习社区', '好好学习社区', '好好学习社区，致力于为web学习交流', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\', '2019 © &lt;a href=\"/\" target=\"_blank\"&gt;panzhigao.vip 出品&lt;/a&gt;京ICP备18031226号', 'body {\n\n}', 'https://s22.cnzz.com/z_stat.php?id=1274156186&web_id=1274156186', '2019-03-16 12:31:50', '1523899', '2019-04-06 21:18:45', '1523899');

-- ----------------------------
-- Table structure for t_user
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
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人id',
  `admin_flag` int(1) NOT NULL DEFAULT '0' COMMENT '管理员标志，0-否，1-是',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1523899', '1', 'admin', 'admin', '6FA456619C9CD1E640134D615945CBA05D5F26DC32003EF3E59DA2A5', '2019-03-30 18:41:28', '2019-04-10 14:45:15', '1', '18911536627', '2019-03-31 14:36:21', 'http://www.panzhigao.vip/myimage/20190331143621060.jpg', '0', '1', '北京');
INSERT INTO `t_user` VALUES ('1524965', '1', 'gangtiexia', '钢铁侠', 'D2D5F45EA8D6F5D54EDE7A768BF1A3625F93B61F428B71539F7D9B7A', '2019-03-31 12:16:08', '2019-03-31 13:10:48', '1', '', '2019-03-31 14:18:13', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', '0', '0', '');
INSERT INTO `t_user` VALUES ('1525055', '0', 'lvdengxia', '绿灯侠', '0701BC65DCF322E5A6304ED34D683AEE3F80DD262C26C8043BCB14B9', '2019-03-31 13:38:25', '2019-03-31 13:38:25', '1', '', '2019-03-31 13:38:25', '/static/images/default_portrait.jpg', '0', '0', '');

-- ----------------------------
-- Table structure for t_user_extension
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
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `continuous_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续登陆天数',
  `continuous_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  `total_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共登陆天数',
  `total_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1525056 DEFAULT CHARSET=utf8 COMMENT='用户拓展表';

-- ----------------------------
-- Records of t_user_extension
-- ----------------------------
INSERT INTO `t_user_extension` VALUES ('1523899', 'admin', 'http://www.panzhigao.vip/myimage/20190331143621060.jpg', '哈哈', '2019-03-30 18:41:28', '2019-04-10 14:40:28', '3', '1', '82', '6', '3', '6', '3');
INSERT INTO `t_user_extension` VALUES ('1524965', '钢铁侠', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', '', '2019-03-31 12:16:08', '2019-03-31 14:18:13', '0', '2', '29', '0', '1', '0', '1');
INSERT INTO `t_user_extension` VALUES ('1525055', '绿灯侠', '/static/images/default_portrait.jpg', '', '2019-03-31 13:38:25', '2019-03-31 13:38:36', '0', '0', '30', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(10) NOT NULL DEFAULT '0' COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1523899', '1', '2019-03-30 18:41:28', '1523898');
INSERT INTO `t_user_role` VALUES ('2', '1524965', '2', '2019-03-31 12:16:08', '1524964');
INSERT INTO `t_user_role` VALUES ('3', '1525055', '2', '2019-03-31 13:38:25', '1525055');
