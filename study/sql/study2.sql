/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : study2

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-04-13 23:35:09
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
INSERT INTO `t_article` VALUES ('1524252', '1523899', '', '3', '2019-03-31 00:35:00', null, '2019-03-31 00:35:00', '66666', '22222', '', '', '1', '10', '2', '0', '0', '1');
INSERT INTO `t_article` VALUES ('1534142', '1523899', 'admin', '3', '2019-04-06 21:04:24', '2019-04-09 17:12:54', '2019-04-06 21:05:48', '江小白LOGO被宣告无效，世间要再无江小白？！', '&lt;p&gt;说到白酒界最会搞营销的那非&lt;span&gt;江小白&lt;/span&gt;莫属，凭借戳人心窝的文案走红，然而人红是非多，江小白与江津酒厂长达3年的商标官司，最近又有了新进展。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/fcfff589f1f44e38a01a1386f7da6cb6\" img_width=\"900\" img_height=\"608\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;有新闻报道江小白LOGO被宣告无效？！难道世间要再无江小白？&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/d2702b897291480d964f6c87338fc93c\" img_width=\"64\" img_height=\"64\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e205b33c31794454a309cf685275ef46\" img_width=\"599\" img_height=\"394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;一时间网友都开始担心，这样一个备受年轻人喜爱的江小白就要离我们而去了？于是江小白便在官微上发表声明用以澄清此事。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/585ffe0afe70473bbce9a50877901adb\" img_width=\"900\" img_height=\"691\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;细读江小白的声明，也能看出江小白注册了这么多商标，偶尔有一个无效，并不影响江小白的正常销售。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/bee4bed91d3743a7a88e9cc95a68fbbd\" img_width=\"690\" img_height=\"809\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;其实被宣告无效的是江小白的&lt;span&gt;第10325554号商标&lt;/span&gt;&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/1a9ea25a59804b59a180d0b01026030a\" img_width=\"787\" img_height=\"741\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而江小白在发展的这些年注册了不少的商标，累计算下来有&lt;span&gt;171个&lt;/span&gt;；但江津酒厂从2012年申请至今才9个“江小白”商标，但两方大部分商标还处在审核中。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/cb6243e276e24b28a70182a9c5eb2d94\" img_width=\"440\" img_height=\"790\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;看完这些，想必各位江小白的老铁也可以放心了，原来这是虚惊一场。但事情最终会变成哪种局势，现在还很难下定论！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/d5f51050f68542c09ae6c924ef49989b\" img_width=\"900\" img_height=\"512\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;江小白一直以戮心的文案切中年轻人的情感痛点，可谓是数不胜数，正是这样一个创新的文案，吸引了不少人对江小白的关注。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/18be3886a8b74394add836c252212f94\" img_width=\"1024\" img_height=\"1545\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/8f203eec39f7428fb7b6712200b5c2f3\" img_width=\"945\" img_height=\"1299\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/65d499b569634dbe8b810b65c96b1397\" img_width=\"1008\" img_height=\"1553\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而最近江小白一直保持着一颗不安分的心，不断的尝试新玩法，与电影、音乐、艺术节等纷纷联合跨界搞营销。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/c95eb52c4d2043cdac69f90a66a98b56\" img_width=\"900\" img_height=\"1628\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/565b553936474e1ab0cbefc965d68d57\" img_width=\"690\" img_height=\"966\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/d3651bb19eb74b3eba26eb1883e21fba\" img_width=\"900\" img_height=\"1260\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/be8cbf68e4fa43fb97dbd56fbdbdff0c\" img_width=\"900\" img_height=\"1394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e6e925b13a504b7ab2278f548834e9d3\" img_width=\"900\" img_height=\"600\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;不仅营销搞的风声水起，米醋还发现江小白自己还推出了米、油产品，感觉世界已经阻挡不住江小白了！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/082783db8d934286b72eac35d6afaa1e\" img_width=\"1024\" img_height=\"708\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/0171c075fc50469fb5361e72c4f1f8d5\" img_width=\"690\" img_height=\"920\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/c293d021aad34080af008d9ec409eff0\" img_width=\"900\" img_height=\"622\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;&lt;span&gt;最后来说说，江小白商标案你怎么看？&lt;/span&gt;&lt;/p&gt;', '', '', '1', '11', '1', '2', '1', '1');
INSERT INTO `t_article` VALUES ('1542947', '1523899', 'admin', '3', '2019-04-13 00:10:46', '2019-04-13 00:29:15', '2019-04-13 00:28:23', '刘强东谈996：我现在还能做到8116+8', '&lt;p&gt;近段时间来，有关京东集团组织架构调整的余波仍未平息，有关互联网公司“996”工作制的利弊又引发了广泛争议。4月12日，一则疑似京东集团董事局主席兼首席执行官刘强东的朋友圈流出，内容为刘强东对京东集团本次的末位淘汰做了阐释。随即红星新闻记者从京东集团处证实了该朋友圈声明的真实性。&lt;/p&gt;&lt;p&gt;刘强东在声明开篇讲述了京东创业时期，自己作为客服24小时处理用户投诉的故事，并表示&lt;span&gt;京东已经四五年没有实施末位淘汰，导致人员急剧膨胀，发号施令和混日子的人越来越多，干活的人反倒越来越少，为了对18万员工背后的家庭负责，自己没有选择余地（末位淘汰）&lt;/span&gt;。对近期引起广泛争议的“996”刘强东也表示，自己到现在还能做到8116+8（周一到周六，早八点到晚11点，周日八小时，每月休假两天），但京东永远不会强制员工995或996，但是每个京东人都必须具备拼搏精神。&lt;/p&gt;&lt;p&gt;以下是刘强东声明全文：&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNQgTm89E52Op4\" img_width=\"640\" img_height=\"1385\" alt=\"刘强东谈996：我现在还能做到8116+8\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;', '', '', '0', '7', '1', '5', '1', '6');
INSERT INTO `t_article` VALUES ('1544268', '1544263', 'wangyuchun', '3', '2019-04-13 22:09:19', '2019-04-13 22:09:47', '2019-04-13 22:09:47', '推女郎合集', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220855043.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220909629.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220916218.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '', '', '3', '6', '1', '0', '0', '8');
INSERT INTO `t_article` VALUES ('1544321', '1544263', 'wangyuchun', '3', '2019-04-13 22:59:08', '2019-04-13 22:59:31', '2019-04-13 22:59:31', '王语纯的相册', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225836356.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225845281.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225854170.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225904915.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '', '', '0', '6', '1', '0', '0', '8');
INSERT INTO `t_article` VALUES ('1544326', '1544324', 'zhaoweiyi', '3', '2019-04-13 23:02:15', '2019-04-13 23:02:37', '2019-04-13 23:02:27', '我的图片', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230147523.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230153982.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230158498.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230206559.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '', '', '3', '5', '1', '6', '0', '8');
INSERT INTO `t_article` VALUES ('1544343', '1544324', 'zhaoweiyi', '3', '2019-04-13 23:15:49', '2019-04-13 23:16:04', '2019-04-13 23:16:04', '贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......', '&lt;p&gt;昨天，网友@天涯历知幸在微博上发表了一篇文章，让我们感到，这个世界上，总有人会偷偷守护你内心柔软，让你放胆探索世界……&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RNV4odc4lKwJTV\" img_width=\"1080\" img_height=\"638\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;学校有菜有肉爱心午餐，不好意思去吃，宁愿啃馒头就免费的汤，花一样的年纪，饿得面黄肌瘦……&lt;/p&gt;&lt;p&gt;学校公开的贫困生补助，或者商家慈善资助，不好意思去申请，怕站在台上接受所有人怜悯的目光……&lt;/p&gt;&lt;p&gt;对于这样的孩子，学校很着急，老师很着急，孩子来读书，不能让孩子吃不饱啊！&lt;/p&gt;&lt;p&gt;&lt;span&gt;于是，很多学校开始想办法&lt;/span&gt;&lt;/p&gt;&lt;p&gt;南京理工大学，研究孩子饭卡，如果一个孩子，一个月在食堂吃60顿饭，平均一顿都不到7块钱，就给孩子饭卡打钱，保证孩子每顿饭能吃饱。&lt;/p&gt;&lt;p&gt;中国科学技术大学，研究一卡通消费，如果孩子每个月在食堂吃饭超过60顿，但是消费还不到240元的，他们就一次性打160到孩子卡内。&lt;/p&gt;&lt;p&gt;郑州大学，同样是研究孩子的一卡通消费，通过这个，学校挖掘出很多不愿意拿补助的孩子，靠主食就免费菜汤拼命念书，郑州大学决定，对于每个月消费不到120的孩子，学校往他卡上打钱。&lt;/p&gt;&lt;p&gt;电子科技大学，有一套专门的系统，只负责去找“隐形贫困”的孩子。这套系统，记录了学生各类在校消费，比如说，吃饭花多少钱，买水果花多少钱，超市零食花多少，日用品花多少，每个学期坐几趟学校班车等等等等，非常详细。再结合学生勤工俭学，奖学金情况，家庭经济等等进行综合分析，最后计算机会给出一份名单，告诉学校应该补助谁。&lt;/p&gt;&lt;p&gt;有类似举动的学校还有很多很多，每个学校的方法也许都不一样，但是有一点是共通的，&lt;span&gt;整个过程，不用孩子申请，钱是偷偷打到孩子卡里，只有学校和孩子知道。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;我在你身后，你无忧无虑地前进吧&lt;/span&gt;&lt;/p&gt;&lt;p&gt;有人担心，会不会有孩子知道规则后，钻空子去骗这个补贴，南京理工大学的老师，是这么说的：如果一个孩子，为了拿这个补助，连着三个月，每顿都吃几块钱，那孩子是真缺这点钱，我们愿意把这钱给孩子。&lt;/p&gt;&lt;p&gt;&lt;span&gt;这些学校，这些老师，给予孩子的，不仅给物质上的资助，他们尽最大的可能，维护着这些孩子的自尊。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;相信若干年后，这些孩子走上社会，回忆起大学时光，想起的不是饥饿和窘迫，而是食堂里香喷喷的大鸡腿，是食堂阿姨满满一勺子的红烧排骨，偶尔午夜梦回，嘴角也会带着满足的微笑吧。&lt;/p&gt;&lt;p&gt;你放胆探索世界，我守护你内心柔软。&lt;/p&gt;&lt;p&gt;所谓为人师表，大约就是这样吧。&lt;/p&gt;&lt;p&gt;人间事，多美好。&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJ5IlaPItz4xSu\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;网友点赞：&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;“真好啊，所以才叫母校吧！&lt;/span&gt;&lt;span&gt;”&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4odr5U0PW9X\" img_width=\"1080\" img_height=\"392\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RNV4oe3Bnhh9fl\" img_width=\"1080\" img_height=\"1051\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4oeEE5v7zIL\" img_width=\"1080\" img_height=\"307\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4oeQCEDNPHy\" img_width=\"1080\" img_height=\"342\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;很多网友们也分享了自己的故事——&lt;/p&gt;&lt;p&gt;&lt;span&gt;帮我吃掉吧，不然就浪费了。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@点L心：学校发水果都会多发一点，我们班一个贫困生，我怕直接给他他不愿意，都是跟孩子们说多的水果我要了，然后放学的时候让他帮我提着，等到孩子们都走了，我就跟他说，我家今天买水果了，能不能帮我把这些水果吃了，不然就浪费了。&lt;/span&gt;&lt;/p&gt;&lt;br&gt;&lt;p&gt;&lt;span&gt;@撄宁行走在这人间世：本科室友在食堂买饭的时候，买一个豆沙包一个馒头，把豆沙包当成菜。后来我每次周末回家就会拿一堆水果来，放在宿舍窗台上，催着大家帮我吃，说我妈总是逼我吃我不爱吃的水果，非说这个有营养，吃不完就坏掉了。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJCOg5aF7tUyk6\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;这是我穿过最贵的鞋，108块呢！&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@小羽小事：还记得表妹考上高中，暑假来玩，我看她凉鞋都把脚磨出血来了，拉着她去商场，她不肯，说是夏天新买的。我硬是付了钱。后来妈妈跟我说，不小心看到她的日记，写到“今天到舅舅家，姐姐给我买了一双真皮的凉鞋，这是我穿过的最贵的鞋了，要108块呢！”我泪目。她大学毕业，第一笔工资惦记着给我买礼物。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJGd2mY93rwYGd\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;让她整合作业，我就能给她买文具了。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@叉烧喵-：原来班里有个家庭条件不好但是非常要强的孩子，便借口让她每天整合各科的作业，用便利贴贴在教室里提醒其他同学，这样我就可以经常给她买本子买笔了……啊～人间事，真美好。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RM76ffLHYpAQ46\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;孩子们放心吧，其实周围的同学很善良。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@阿夹才不是怪物：也希望这些孩子不用那么敏感，周围的同学其实都很善良。我大学时，因为父亲生病家里很困难，能申请的补助都会申请，打米饭免费汤吃咸菜，还在食堂擦桌子打工。我自己从没觉得自卑，也不会瞒着同学，中午吃饭我说我回寝室吃，要打工了告诉他们不用等我。同学从来没有对我有什么不同。所以孩子们放心吧！&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RLQfy8VCcVYkvq\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;在留言中，很多网友都欣慰地说：“还好我的学校申请助学金不用演讲！”&lt;/p&gt;&lt;p&gt;很多同学由于自尊心，宁可生活更节省一点，也不愿意走上讲台去叙述自己的困境。有时候，甚至本该属于他们的资助，也没有真正作用在他们身上。&lt;/p&gt;&lt;p&gt;相比之前的贫困生补助，需要贫困生主动提出申请，回家开出各种证明材料，学校收到之后进行评审，最后在学校里进行公示；&lt;span&gt;大数据资助贫困生，既不需要学生主动申请，也不用再提供任何证明，甚至在收到补贴前都没有学生知情，这样的资助方式当然要更为人性，也更有效率。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;很多人不愿意在同学面前展示自己的贫穷，所以干脆不去申请贫困生补助；与此同时，开证明这种确认贫困与否的办法，不仅很麻烦，而且谁也无法知道证明材料的真伪。此外，高校为了甄别贫困学生，搞过竞选贫困生、评议贫困生等很多招数，但无不遭遇侵犯学生隐私、伤害学生自尊的质疑。&lt;/p&gt;&lt;p&gt;而上述学校通过采集学生日常的生活消费数据，以及勤工俭学、社交特征、行为轨迹等方方面面的情况，大数据勾勒学生的相对贫困情况，自然更为准确。只要你在生活数据上是个贫困生，那么你就是一个可以得到帮助的贫困生。&lt;span&gt;这样的助困方法，提高的是助困效率，保护的是贫困生脆弱的自尊。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;利用大数据资助贫困生，是高校利用高科技提供更好服务的一大创新，终于让人感觉到我们的高校，没有与这个鼓励创新的时代脱节。&lt;/p&gt;&lt;p&gt;孩子们，放心吧，你的柔软心灵，总有人在默默守护。&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RI3AzvmEkSvqhZ\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;妈妈也许忘记了很多，但一定不会忘记爱你！&lt;/p&gt;', '', '', '0', '0', '1', '0', '0', '9');
INSERT INTO `t_article` VALUES ('1544349', '1544324', 'zhaoweiyi', '0', '2019-04-13 23:20:23', '2019-04-13 23:20:23', null, 'wwwwww', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232020989.jpeg\" alt=\"undefined\"&gt;', '', '', '0', '0', '1', '0', '0', '8');
INSERT INTO `t_article` VALUES ('1544352', '1544324', 'zhaoweiyi', '3', '2019-04-13 23:22:18', '2019-04-13 23:28:57', '2019-04-13 23:28:57', '青春少女', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232216562.jpeg\" alt=\"undefined\"&gt;', '', '', '0', '1', '1', '0', '0', '9');
INSERT INTO `t_article` VALUES ('1544364', '1544358', 'linmengmeng', '3', '2019-04-13 23:31:50', '2019-04-13 23:32:19', '2019-04-13 23:32:19', '33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！', '&lt;p&gt;北京时间4月13日晚，CBA季后赛半决赛G3打响，广东男篮客场111-106击败深圳男篮，总比分3-0领先，夺取赛点。与上一场一样，广东男篮赢得并不轻松，在最多领先20分的情况下，末节被对手追到1分。关键时刻，易建联成为广东男篮的关键先生。本场比赛，易建联出场35分钟52秒，21投14中，其中三分球2投1中，砍下33分14篮板3抢断4盖帽，打出了本赛季季后赛最强势一战！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/0e63a53709f24f08ae1fa18cb5b9ed57\" img_width=\"898\" img_height=\"614\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;上一场比赛，广东男篮遭遇深圳男篮顽强狙击，易建联也终于出汗了，打了35分钟，17中10，砍下23分14篮板2盖帽，并且在决胜阶段完成2次关键防守和1次关键进攻，帮助球队奠定胜局。此役李慕豪缺阵，深圳男篮内线大受影响，易建联打得就更加轻松了。半场战罢，易建联就11中8，高效砍下19分，扣篮、中投、三分、勾手、空接暴扣全都有。沈梓捷是深圳男篮阵中唯一能和易建联对位的球员，但他也是被打爆。第一节进行到8分42秒，易建联一对一单打沈梓捷，直接突破转身扣篮打成2+1！深圳其他球员对位易建联的时候，根本不够看。更令深圳男篮绝望的是，易建联在防守端同样统治力十足，半场送出4次盖帽，在内线可谓一夫当关万夫莫开。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/e53a7c9b50654aa4b0f0f6efd8ddcab5\" img_width=\"945\" img_height=\"619\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;第三节比赛，随着分差越来越大，易建联也顺势开启了划水模式。然而，就在易建联在场下休息这段时间，深圳男篮掀起反扑，将20分分差缩小到10分。易建联重新上场后，广东男篮稍稍稳住局势，但也未能拉开比赛，领先11分进入末节。关键的第四节，在球队进攻受挫的情况下，易建联用个人能力为球队延续进攻火力。篮下强吃沈梓捷、低位吸引防守助攻任骏飞上空篮、造沈梓捷犯规2罚全中、转身跳投打中；在他一系列操作下，广东男篮顶住了深圳男篮的反扑，领先9分，掌握比赛主动权。而当深圳男篮再次掀起反扑浪潮，并且在终场前4分39秒追到只差1分的时候，易建联再次成为广东男篮的关键先生。终场前4分21秒，马尚三分不中，易建联抢下进攻篮板，完成双手暴扣，这球不仅稳住局势，而且非常提升士气。防守回合，易建联又完成抢断，帮助球队打出反击，并且接到马尚传球再次上演扣篮，将分差扩大到5分。在易建联带动下，广东男篮起势，一举收割比赛。关键的第四节，易建联砍下10分7篮板2抢断！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/4ae6404645de4c04a27b13ff58596dd1\" img_width=\"889\" img_height=\"591\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;最近2场比赛，深圳男篮给广东男篮制造了很大的麻烦，而易建联也终于开启季后赛模式，合砍56分27篮板4抢断6盖帽，并且奉献了9次扣篮！事实证明，当易建联体能得到保证的时候，他在第四节是非常恐怖的，他才是广东男篮的核武器。对于深圳男篮来说，尽管总比分0-3落后，但能逼出全力模式的易建联，他们已经打得足够出色！&lt;/p&gt;', '', '', '0', '0', '1', '0', '0', '5');
INSERT INTO `t_article` VALUES ('15241201112223333', '1523899', 'admin', '3', '2019-03-30 22:13:18', '2019-04-09 17:15:38', '2019-03-30 22:48:12', '666', '6666', '', '', '1', '71', '1', '4', '1', '1');
INSERT INTO `t_article` VALUES ('15394991112223333', '1523899', 'admin', '3', '2019-04-10 14:40:20', '2019-04-10 14:40:28', '2019-04-10 14:40:28', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '&lt;p&gt;&lt;span&gt;加上上周国办公布的社保降费方案，自2015年起已6次降低或阶段性降低社保费率，5年预计减轻企业社保缴费负担8000亿元。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;4月9日，人社部、财政部、税务总局、国家医保局等四部门有关负责人就《降低社会保险费率综合方案》答记者问中，透露了上述信息。&lt;/p&gt;&lt;p&gt;四部门同时透露，今年的这次降费，是2015年以来幅度最大的一次，且首次同时惠及企业和个人，尤其能减少小微企业及其员工、灵活就业人员的社保缴费负担。&lt;/p&gt;&lt;p&gt;为何说幅度最大？这次城镇职工基本养老保险单位缴费比例，高于16%的省份，可降至16%。&lt;/p&gt;&lt;p&gt;而目前，各省份（含新疆生产建设兵团）企业缴费比例不统一，高的省份20%，多数省份阶段性降至19%，还有个别省份14%左右。&lt;/p&gt;&lt;p&gt;&lt;span&gt;也就是说，单位缴费比例最多可降低4个百分点，不设条件，也不是阶段性政策，而是长期性制度安排。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;仅这一条，全年可减轻企业社保缴费负担1900亿元。加上继续阶段性降低失业保险和工伤保险费率，本次降低养老、失业、工伤保险费率，预计2019年全年可减轻社保缴费负担3000多亿元。&lt;/p&gt;&lt;p&gt;单位缴费，降低企业负担。而社保缴费基数的调整，则直接惠及职工。&lt;/p&gt;&lt;p&gt;《方案》明确，将城镇非私营单位和城镇私营单位就业人员平均工资加权计算的全口径城镇单位就业人员平均工资，作为核定职工缴费基数上下限的指标。&lt;/p&gt;&lt;p&gt;而原来的政策，将非私营单位在岗职工平均工资作为社保缴费基数，未纳入广大私营单位职工的工资，并不公平。&lt;/p&gt;&lt;p&gt;这次调整后，&lt;span&gt;工资水平较低的职工，缴费基数可相应降低，缴费负担减轻。部分企业，特别是部分小微企业或劳动密集型企业，不少职工按照缴费基数下限缴费，企业缴费负担也可进一步减轻，能更多受益。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RN8wJ4FB3gBiRH\" img_width=\"504\" img_height=\"190\" alt=\"5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;举个例子：某地以前将非私营单位在岗职工平均工资6000元，作为社保缴费基数。那么个人缴费基数的下限就是3600元。某职工每月工资3000元，就按照3600元的最低缴费基数算社保费。&lt;/p&gt;&lt;p&gt;调整后，按全口径城镇单位就业人员平均工资5000元，作为新的社保缴费基数。个人缴费基数下限相应降低到3000元，该职工就可按3000元计算缴费金额。&lt;/p&gt;&lt;p&gt;前后对比，月缴费基数减少600元，个人缴费比例8%，月缴费负担相应减轻48元。扣除社保缴费，工资未到个税起征点，那么此人5月1日的到手工资，将实打实地多拿到48元。&lt;/p&gt;&lt;p&gt;&lt;span&gt;若此人所在企业，以个人缴费基数之和确定单位缴费基数，则企业每月缴费基数也相应减少600元，缴费负担可进一步减轻。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;此外，个体工商户和灵活就业人员参加养老保险，可在全口径城镇单位就业人员平均工资的60%至300%范围内选择适当的缴费基数。&lt;/p&gt;&lt;p&gt;&lt;span&gt;不仅平均工资口径调整、标准降低，选择范围也变大，选择低基数的可以进一步减轻缴费负担，收入较高的人员也可以选择较高的缴费基数，来提高自己退休后的养老金水平。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;比如，按上例，如为灵活就业人员，月缴费基数可从6000元改为以3000元下限缴费，则月缴费基数减少3000元，按20%比例缴费，月缴费负担相应减轻600元。&lt;/p&gt;&lt;p&gt;中国宏观经济研究院副研究员关博告诉21世纪经济报道记者，费基调整是此轮社保降费的最大亮点。&lt;/p&gt;&lt;p&gt;“一方面通过全口径核定费基，实现了民营企业，特别是工资性收入低于在岗职工平均工资的小微企业职工结构性降费，减负效果叠加。同时，费基是养老保险制度筹资缴费的关键参数，在降低费率过程中统一费基计算办法，在制度整合方面迈出了重要一步。”关博说。&lt;/p&gt;', '', '', '0', '3', '1', '0', '0', '4');

-- ----------------------------
-- Table structure for `t_article_category`
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='文章分类表';

-- ----------------------------
-- Records of t_article_category
-- ----------------------------
INSERT INTO `t_article_category` VALUES ('1', '国际', '2', '1', '2019-04-07 11:05:29', '444', '2019-04-10 14:11:57', '1523899');
INSERT INTO `t_article_category` VALUES ('4', '财经', '1', '1', '2019-04-10 14:39:05', '1523899', '2019-04-10 14:39:08', '1523899');
INSERT INTO `t_article_category` VALUES ('5', '体育', '1', '1', '2019-04-10 14:39:29', '1523899', '2019-04-10 14:39:31', '1523899');
INSERT INTO `t_article_category` VALUES ('6', '科技', '1', '1', '2019-04-10 14:39:45', '1523899', '2019-04-10 14:39:49', '1523899');
INSERT INTO `t_article_category` VALUES ('7', '漫画', '9', '1', '2019-04-13 21:59:36', '1523899', '2019-04-13 21:59:56', '1523899');
INSERT INTO `t_article_category` VALUES ('8', '图片', '1', '1', '2019-04-13 22:08:16', '1523899', '2019-04-13 22:54:15', '1523899');
INSERT INTO `t_article_category` VALUES ('9', '社会', '1', '1', '2019-04-13 23:15:15', '1523899', '2019-04-13 23:15:18', '1523899');

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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='文章审核表';

-- ----------------------------
-- Records of t_article_check
-- ----------------------------
INSERT INTO `t_article_check` VALUES ('38', '1523899', 'admin', '1524120', '666', '6666', '1', '0', '1523899', 'admin', '2019-03-30 22:48:12', '2019-03-30 22:13:19', '1', '', '1');
INSERT INTO `t_article_check` VALUES ('39', '1523899', 'admin', '1534142', '江小白LOGO被宣告无效，世间要再无江小白？！', '&lt;p&gt;说到白酒界最会搞营销的那非&lt;span&gt;江小白&lt;/span&gt;莫属，凭借戳人心窝的文案走红，然而人红是非多，江小白与江津酒厂长达3年的商标官司，最近又有了新进展。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/fcfff589f1f44e38a01a1386f7da6cb6\" img_width=\"900\" img_height=\"608\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;有新闻报道江小白LOGO被宣告无效？！难道世间要再无江小白？&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/d2702b897291480d964f6c87338fc93c\" img_width=\"64\" img_height=\"64\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e205b33c31794454a309cf685275ef46\" img_width=\"599\" img_height=\"394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;一时间网友都开始担心，这样一个备受年轻人喜爱的江小白就要离我们而去了？于是江小白便在官微上发表声明用以澄清此事。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/585ffe0afe70473bbce9a50877901adb\" img_width=\"900\" img_height=\"691\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;细读江小白的声明，也能看出江小白注册了这么多商标，偶尔有一个无效，并不影响江小白的正常销售。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/bee4bed91d3743a7a88e9cc95a68fbbd\" img_width=\"690\" img_height=\"809\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;其实被宣告无效的是江小白的&lt;span&gt;第10325554号商标&lt;/span&gt;&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/1a9ea25a59804b59a180d0b01026030a\" img_width=\"787\" img_height=\"741\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而江小白在发展的这些年注册了不少的商标，累计算下来有&lt;span&gt;171个&lt;/span&gt;；但江津酒厂从2012年申请至今才9个“江小白”商标，但两方大部分商标还处在审核中。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/cb6243e276e24b28a70182a9c5eb2d94\" img_width=\"440\" img_height=\"790\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;看完这些，想必各位江小白的老铁也可以放心了，原来这是虚惊一场。但事情最终会变成哪种局势，现在还很难下定论！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/d5f51050f68542c09ae6c924ef49989b\" img_width=\"900\" img_height=\"512\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;江小白一直以戮心的文案切中年轻人的情感痛点，可谓是数不胜数，正是这样一个创新的文案，吸引了不少人对江小白的关注。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/18be3886a8b74394add836c252212f94\" img_width=\"1024\" img_height=\"1545\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/8f203eec39f7428fb7b6712200b5c2f3\" img_width=\"945\" img_height=\"1299\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/65d499b569634dbe8b810b65c96b1397\" img_width=\"1008\" img_height=\"1553\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;然而最近江小白一直保持着一颗不安分的心，不断的尝试新玩法，与电影、音乐、艺术节等纷纷联合跨界搞营销。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/c95eb52c4d2043cdac69f90a66a98b56\" img_width=\"900\" img_height=\"1628\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/565b553936474e1ab0cbefc965d68d57\" img_width=\"690\" img_height=\"966\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/d3651bb19eb74b3eba26eb1883e21fba\" img_width=\"900\" img_height=\"1260\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/be8cbf68e4fa43fb97dbd56fbdbdff0c\" img_width=\"900\" img_height=\"1394\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/e6e925b13a504b7ab2278f548834e9d3\" img_width=\"900\" img_height=\"600\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;不仅营销搞的风声水起，米醋还发现江小白自己还推出了米、油产品，感觉世界已经阻挡不住江小白了！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/082783db8d934286b72eac35d6afaa1e\" img_width=\"1024\" img_height=\"708\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p9.pstatp.com/large/pgc-image/0171c075fc50469fb5361e72c4f1f8d5\" img_width=\"690\" img_height=\"920\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/c293d021aad34080af008d9ec409eff0\" img_width=\"900\" img_height=\"622\" alt=\"江小白LOGO被宣告无效，世间要再无江小白？！\" inline=\"0\"&gt;&lt;/div&gt;&lt;p&gt;&lt;span&gt;最后来说说，江小白商标案你怎么看？&lt;/span&gt;&lt;/p&gt;', '1', '1', '1523899', 'admin', '2019-04-06 21:05:48', '2019-04-06 21:05:21', '1', '', '4');
INSERT INTO `t_article_check` VALUES ('40', '1523899', 'admin', '1539499', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '&lt;p&gt;&lt;span&gt;加上上周国办公布的社保降费方案，自2015年起已6次降低或阶段性降低社保费率，5年预计减轻企业社保缴费负担8000亿元。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;4月9日，人社部、财政部、税务总局、国家医保局等四部门有关负责人就《降低社会保险费率综合方案》答记者问中，透露了上述信息。&lt;/p&gt;&lt;p&gt;四部门同时透露，今年的这次降费，是2015年以来幅度最大的一次，且首次同时惠及企业和个人，尤其能减少小微企业及其员工、灵活就业人员的社保缴费负担。&lt;/p&gt;&lt;p&gt;为何说幅度最大？这次城镇职工基本养老保险单位缴费比例，高于16%的省份，可降至16%。&lt;/p&gt;&lt;p&gt;而目前，各省份（含新疆生产建设兵团）企业缴费比例不统一，高的省份20%，多数省份阶段性降至19%，还有个别省份14%左右。&lt;/p&gt;&lt;p&gt;&lt;span&gt;也就是说，单位缴费比例最多可降低4个百分点，不设条件，也不是阶段性政策，而是长期性制度安排。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;仅这一条，全年可减轻企业社保缴费负担1900亿元。加上继续阶段性降低失业保险和工伤保险费率，本次降低养老、失业、工伤保险费率，预计2019年全年可减轻社保缴费负担3000多亿元。&lt;/p&gt;&lt;p&gt;单位缴费，降低企业负担。而社保缴费基数的调整，则直接惠及职工。&lt;/p&gt;&lt;p&gt;《方案》明确，将城镇非私营单位和城镇私营单位就业人员平均工资加权计算的全口径城镇单位就业人员平均工资，作为核定职工缴费基数上下限的指标。&lt;/p&gt;&lt;p&gt;而原来的政策，将非私营单位在岗职工平均工资作为社保缴费基数，未纳入广大私营单位职工的工资，并不公平。&lt;/p&gt;&lt;p&gt;这次调整后，&lt;span&gt;工资水平较低的职工，缴费基数可相应降低，缴费负担减轻。部分企业，特别是部分小微企业或劳动密集型企业，不少职工按照缴费基数下限缴费，企业缴费负担也可进一步减轻，能更多受益。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RN8wJ4FB3gBiRH\" img_width=\"504\" img_height=\"190\" alt=\"5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;举个例子：某地以前将非私营单位在岗职工平均工资6000元，作为社保缴费基数。那么个人缴费基数的下限就是3600元。某职工每月工资3000元，就按照3600元的最低缴费基数算社保费。&lt;/p&gt;&lt;p&gt;调整后，按全口径城镇单位就业人员平均工资5000元，作为新的社保缴费基数。个人缴费基数下限相应降低到3000元，该职工就可按3000元计算缴费金额。&lt;/p&gt;&lt;p&gt;前后对比，月缴费基数减少600元，个人缴费比例8%，月缴费负担相应减轻48元。扣除社保缴费，工资未到个税起征点，那么此人5月1日的到手工资，将实打实地多拿到48元。&lt;/p&gt;&lt;p&gt;&lt;span&gt;若此人所在企业，以个人缴费基数之和确定单位缴费基数，则企业每月缴费基数也相应减少600元，缴费负担可进一步减轻。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;此外，个体工商户和灵活就业人员参加养老保险，可在全口径城镇单位就业人员平均工资的60%至300%范围内选择适当的缴费基数。&lt;/p&gt;&lt;p&gt;&lt;span&gt;不仅平均工资口径调整、标准降低，选择范围也变大，选择低基数的可以进一步减轻缴费负担，收入较高的人员也可以选择较高的缴费基数，来提高自己退休后的养老金水平。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;比如，按上例，如为灵活就业人员，月缴费基数可从6000元改为以3000元下限缴费，则月缴费基数减少3000元，按20%比例缴费，月缴费负担相应减轻600元。&lt;/p&gt;&lt;p&gt;中国宏观经济研究院副研究员关博告诉21世纪经济报道记者，费基调整是此轮社保降费的最大亮点。&lt;/p&gt;&lt;p&gt;“一方面通过全口径核定费基，实现了民营企业，特别是工资性收入低于在岗职工平均工资的小微企业职工结构性降费，减负效果叠加。同时，费基是养老保险制度筹资缴费的关键参数，在降低费率过程中统一费基计算办法，在制度整合方面迈出了重要一步。”关博说。&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-10 14:40:28', '2019-04-10 14:40:20', '1', '', '5');
INSERT INTO `t_article_check` VALUES ('41', '1523899', 'admin', '1542947', '刘强东谈996：我现在还能做到8116+8', '&lt;p&gt;近段时间来，有关京东集团组织架构调整的余波仍未平息，有关互联网公司“996”工作制的利弊又引发了广泛争议。4月12日，一则疑似京东集团董事局主席兼首席执行官刘强东的朋友圈流出，内容为刘强东对京东集团本次的末位淘汰做了阐释。随即红星新闻记者从京东集团处证实了该朋友圈声明的真实性。&lt;/p&gt;&lt;p&gt;刘强东在声明开篇讲述了京东创业时期，自己作为客服24小时处理用户投诉的故事，并表示&lt;span&gt;京东已经四五年没有实施末位淘汰，导致人员急剧膨胀，发号施令和混日子的人越来越多，干活的人反倒越来越少，为了对18万员工背后的家庭负责，自己没有选择余地（末位淘汰）&lt;/span&gt;。对近期引起广泛争议的“996”刘强东也表示，自己到现在还能做到8116+8（周一到周六，早八点到晚11点，周日八小时，每月休假两天），但京东永远不会强制员工995或996，但是每个京东人都必须具备拼搏精神。&lt;/p&gt;&lt;p&gt;以下是刘强东声明全文：&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNQgTm89E52Op4\" img_width=\"640\" img_height=\"1385\" alt=\"刘强东谈996：我现在还能做到8116+8\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;', '1', '1', '1523899', 'admin', '2019-04-13 00:28:23', '2019-04-13 00:28:07', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('42', '1544263', 'wangyuchun', '1544268', '推女郎合集', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220855043.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220909629.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413220916218.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-13 22:09:47', '2019-04-13 22:09:19', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('43', '1544263', 'wangyuchun', '1544321', '王语纯的相册', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225836356.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225845281.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225854170.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413225904915.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-13 22:59:31', '2019-04-13 22:59:08', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('44', '1544324', 'zhaoweiyi', '1544326', '我的图片', '&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230147523.jpeg\" alt=\"undefined\"&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230153982.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230158498.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://www.panzhigao.vip/myimage/20190413230206559.jpeg\" alt=\"undefined\"&gt;&lt;br&gt;&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-13 23:02:27', '2019-04-13 23:02:15', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('45', '1544324', 'zhaoweiyi', '1544343', '贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......', '&lt;p&gt;昨天，网友@天涯历知幸在微博上发表了一篇文章，让我们感到，这个世界上，总有人会偷偷守护你内心柔软，让你放胆探索世界……&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RNV4odc4lKwJTV\" img_width=\"1080\" img_height=\"638\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;学校有菜有肉爱心午餐，不好意思去吃，宁愿啃馒头就免费的汤，花一样的年纪，饿得面黄肌瘦……&lt;/p&gt;&lt;p&gt;学校公开的贫困生补助，或者商家慈善资助，不好意思去申请，怕站在台上接受所有人怜悯的目光……&lt;/p&gt;&lt;p&gt;对于这样的孩子，学校很着急，老师很着急，孩子来读书，不能让孩子吃不饱啊！&lt;/p&gt;&lt;p&gt;&lt;span&gt;于是，很多学校开始想办法&lt;/span&gt;&lt;/p&gt;&lt;p&gt;南京理工大学，研究孩子饭卡，如果一个孩子，一个月在食堂吃60顿饭，平均一顿都不到7块钱，就给孩子饭卡打钱，保证孩子每顿饭能吃饱。&lt;/p&gt;&lt;p&gt;中国科学技术大学，研究一卡通消费，如果孩子每个月在食堂吃饭超过60顿，但是消费还不到240元的，他们就一次性打160到孩子卡内。&lt;/p&gt;&lt;p&gt;郑州大学，同样是研究孩子的一卡通消费，通过这个，学校挖掘出很多不愿意拿补助的孩子，靠主食就免费菜汤拼命念书，郑州大学决定，对于每个月消费不到120的孩子，学校往他卡上打钱。&lt;/p&gt;&lt;p&gt;电子科技大学，有一套专门的系统，只负责去找“隐形贫困”的孩子。这套系统，记录了学生各类在校消费，比如说，吃饭花多少钱，买水果花多少钱，超市零食花多少，日用品花多少，每个学期坐几趟学校班车等等等等，非常详细。再结合学生勤工俭学，奖学金情况，家庭经济等等进行综合分析，最后计算机会给出一份名单，告诉学校应该补助谁。&lt;/p&gt;&lt;p&gt;有类似举动的学校还有很多很多，每个学校的方法也许都不一样，但是有一点是共通的，&lt;span&gt;整个过程，不用孩子申请，钱是偷偷打到孩子卡里，只有学校和孩子知道。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;我在你身后，你无忧无虑地前进吧&lt;/span&gt;&lt;/p&gt;&lt;p&gt;有人担心，会不会有孩子知道规则后，钻空子去骗这个补贴，南京理工大学的老师，是这么说的：如果一个孩子，为了拿这个补助，连着三个月，每顿都吃几块钱，那孩子是真缺这点钱，我们愿意把这钱给孩子。&lt;/p&gt;&lt;p&gt;&lt;span&gt;这些学校，这些老师，给予孩子的，不仅给物质上的资助，他们尽最大的可能，维护着这些孩子的自尊。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;相信若干年后，这些孩子走上社会，回忆起大学时光，想起的不是饥饿和窘迫，而是食堂里香喷喷的大鸡腿，是食堂阿姨满满一勺子的红烧排骨，偶尔午夜梦回，嘴角也会带着满足的微笑吧。&lt;/p&gt;&lt;p&gt;你放胆探索世界，我守护你内心柔软。&lt;/p&gt;&lt;p&gt;所谓为人师表，大约就是这样吧。&lt;/p&gt;&lt;p&gt;人间事，多美好。&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJ5IlaPItz4xSu\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;网友点赞：&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;“真好啊，所以才叫母校吧！&lt;/span&gt;&lt;span&gt;”&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4odr5U0PW9X\" img_width=\"1080\" img_height=\"392\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RNV4oe3Bnhh9fl\" img_width=\"1080\" img_height=\"1051\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4oeEE5v7zIL\" img_width=\"1080\" img_height=\"307\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RNV4oeQCEDNPHy\" img_width=\"1080\" img_height=\"342\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;很多网友们也分享了自己的故事——&lt;/p&gt;&lt;p&gt;&lt;span&gt;帮我吃掉吧，不然就浪费了。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@点L心：学校发水果都会多发一点，我们班一个贫困生，我怕直接给他他不愿意，都是跟孩子们说多的水果我要了，然后放学的时候让他帮我提着，等到孩子们都走了，我就跟他说，我家今天买水果了，能不能帮我把这些水果吃了，不然就浪费了。&lt;/span&gt;&lt;/p&gt;&lt;br&gt;&lt;p&gt;&lt;span&gt;@撄宁行走在这人间世：本科室友在食堂买饭的时候，买一个豆沙包一个馒头，把豆沙包当成菜。后来我每次周末回家就会拿一堆水果来，放在宿舍窗台上，催着大家帮我吃，说我妈总是逼我吃我不爱吃的水果，非说这个有营养，吃不完就坏掉了。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJCOg5aF7tUyk6\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;这是我穿过最贵的鞋，108块呢！&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@小羽小事：还记得表妹考上高中，暑假来玩，我看她凉鞋都把脚磨出血来了，拉着她去商场，她不肯，说是夏天新买的。我硬是付了钱。后来妈妈跟我说，不小心看到她的日记，写到“今天到舅舅家，姐姐给我买了一双真皮的凉鞋，这是我穿过的最贵的鞋了，要108块呢！”我泪目。她大学毕业，第一笔工资惦记着给我买礼物。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/RJGd2mY93rwYGd\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;让她整合作业，我就能给她买文具了。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@叉烧喵-：原来班里有个家庭条件不好但是非常要强的孩子，便借口让她每天整合各科的作业，用便利贴贴在教室里提醒其他同学，这样我就可以经常给她买本子买笔了……啊～人间事，真美好。&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RM76ffLHYpAQ46\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;&lt;span&gt;孩子们放心吧，其实周围的同学很善良。&lt;/span&gt;&lt;/p&gt;&lt;blockquote&gt;&lt;p&gt;&lt;span&gt;@阿夹才不是怪物：也希望这些孩子不用那么敏感，周围的同学其实都很善良。我大学时，因为父亲生病家里很困难，能申请的补助都会申请，打米饭免费汤吃咸菜，还在食堂擦桌子打工。我自己从没觉得自卑，也不会瞒着同学，中午吃饭我说我回寝室吃，要打工了告诉他们不用等我。同学从来没有对我有什么不同。所以孩子们放心吧！&lt;/span&gt;&lt;/p&gt;&lt;/blockquote&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RLQfy8VCcVYkvq\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;在留言中，很多网友都欣慰地说：“还好我的学校申请助学金不用演讲！”&lt;/p&gt;&lt;p&gt;很多同学由于自尊心，宁可生活更节省一点，也不愿意走上讲台去叙述自己的困境。有时候，甚至本该属于他们的资助，也没有真正作用在他们身上。&lt;/p&gt;&lt;p&gt;相比之前的贫困生补助，需要贫困生主动提出申请，回家开出各种证明材料，学校收到之后进行评审，最后在学校里进行公示；&lt;span&gt;大数据资助贫困生，既不需要学生主动申请，也不用再提供任何证明，甚至在收到补贴前都没有学生知情，这样的资助方式当然要更为人性，也更有效率。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;很多人不愿意在同学面前展示自己的贫穷，所以干脆不去申请贫困生补助；与此同时，开证明这种确认贫困与否的办法，不仅很麻烦，而且谁也无法知道证明材料的真伪。此外，高校为了甄别贫困学生，搞过竞选贫困生、评议贫困生等很多招数，但无不遭遇侵犯学生隐私、伤害学生自尊的质疑。&lt;/p&gt;&lt;p&gt;而上述学校通过采集学生日常的生活消费数据，以及勤工俭学、社交特征、行为轨迹等方方面面的情况，大数据勾勒学生的相对贫困情况，自然更为准确。只要你在生活数据上是个贫困生，那么你就是一个可以得到帮助的贫困生。&lt;span&gt;这样的助困方法，提高的是助困效率，保护的是贫困生脆弱的自尊。&lt;/span&gt;&lt;/p&gt;&lt;p&gt;利用大数据资助贫困生，是高校利用高科技提供更好服务的一大创新，终于让人感觉到我们的高校，没有与这个鼓励创新的时代脱节。&lt;/p&gt;&lt;p&gt;孩子们，放心吧，你的柔软心灵，总有人在默默守护。&lt;/p&gt;&lt;p&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/RI3AzvmEkSvqhZ\" img_width=\"800\" img_height=\"800\" alt=\"贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......\" inline=\"0\"&gt;&lt;/p&gt;&lt;p&gt;妈妈也许忘记了很多，但一定不会忘记爱你！&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-13 23:16:04', '2019-04-13 23:15:49', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('46', '1544324', 'zhaoweiyi', '1544349', 'wwwwww', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232020989.jpeg\" alt=\"undefined\"&gt;', '1', '0', '1523899', 'admin', '2019-04-13 23:20:41', '2019-04-13 23:20:23', '0', '不好看', '0');
INSERT INTO `t_article_check` VALUES ('47', '1544324', 'zhaoweiyi', '1544352', '55555555555', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232216562.jpeg\" alt=\"undefined\"&gt;', '1', '0', '1523899', 'admin', '2019-04-13 23:22:34', '2019-04-13 23:22:18', '0', '标题不行', '0');
INSERT INTO `t_article_check` VALUES ('48', '1544324', 'zhaoweiyi', '1544352', '55555555555', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232216562.jpeg\" alt=\"undefined\"&gt;', '1', '1', '1523899', 'admin', '2019-04-13 23:23:03', '2019-04-13 23:22:55', '0', '444', '0');
INSERT INTO `t_article_check` VALUES ('49', '1544324', 'zhaoweiyi', '1544352', '青春少女', '&lt;img src=\"http://www.panzhigao.vip/myimage/20190413232216562.jpeg\" alt=\"undefined\"&gt;', '1', '1', '1523899', 'admin', '2019-04-13 23:28:57', '2019-04-13 23:24:07', '1', '', '0');
INSERT INTO `t_article_check` VALUES ('50', '1544358', 'linmengmeng', '1544364', '33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！', '&lt;p&gt;北京时间4月13日晚，CBA季后赛半决赛G3打响，广东男篮客场111-106击败深圳男篮，总比分3-0领先，夺取赛点。与上一场一样，广东男篮赢得并不轻松，在最多领先20分的情况下，末节被对手追到1分。关键时刻，易建联成为广东男篮的关键先生。本场比赛，易建联出场35分钟52秒，21投14中，其中三分球2投1中，砍下33分14篮板3抢断4盖帽，打出了本赛季季后赛最强势一战！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/0e63a53709f24f08ae1fa18cb5b9ed57\" img_width=\"898\" img_height=\"614\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;上一场比赛，广东男篮遭遇深圳男篮顽强狙击，易建联也终于出汗了，打了35分钟，17中10，砍下23分14篮板2盖帽，并且在决胜阶段完成2次关键防守和1次关键进攻，帮助球队奠定胜局。此役李慕豪缺阵，深圳男篮内线大受影响，易建联打得就更加轻松了。半场战罢，易建联就11中8，高效砍下19分，扣篮、中投、三分、勾手、空接暴扣全都有。沈梓捷是深圳男篮阵中唯一能和易建联对位的球员，但他也是被打爆。第一节进行到8分42秒，易建联一对一单打沈梓捷，直接突破转身扣篮打成2+1！深圳其他球员对位易建联的时候，根本不够看。更令深圳男篮绝望的是，易建联在防守端同样统治力十足，半场送出4次盖帽，在内线可谓一夫当关万夫莫开。&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p3.pstatp.com/large/pgc-image/e53a7c9b50654aa4b0f0f6efd8ddcab5\" img_width=\"945\" img_height=\"619\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;第三节比赛，随着分差越来越大，易建联也顺势开启了划水模式。然而，就在易建联在场下休息这段时间，深圳男篮掀起反扑，将20分分差缩小到10分。易建联重新上场后，广东男篮稍稍稳住局势，但也未能拉开比赛，领先11分进入末节。关键的第四节，在球队进攻受挫的情况下，易建联用个人能力为球队延续进攻火力。篮下强吃沈梓捷、低位吸引防守助攻任骏飞上空篮、造沈梓捷犯规2罚全中、转身跳投打中；在他一系列操作下，广东男篮顶住了深圳男篮的反扑，领先9分，掌握比赛主动权。而当深圳男篮再次掀起反扑浪潮，并且在终场前4分39秒追到只差1分的时候，易建联再次成为广东男篮的关键先生。终场前4分21秒，马尚三分不中，易建联抢下进攻篮板，完成双手暴扣，这球不仅稳住局势，而且非常提升士气。防守回合，易建联又完成抢断，帮助球队打出反击，并且接到马尚传球再次上演扣篮，将分差扩大到5分。在易建联带动下，广东男篮起势，一举收割比赛。关键的第四节，易建联砍下10分7篮板2抢断！&lt;/p&gt;&lt;div class=\"pgc-img\"&gt;&lt;img src=\"http://p1.pstatp.com/large/pgc-image/4ae6404645de4c04a27b13ff58596dd1\" img_width=\"889\" img_height=\"591\" alt=\"33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！\" inline=\"0\"&gt;&lt;p class=\"pgc-img-caption\" style=\"text-align: center;\"&gt;&lt;/p&gt;&lt;/div&gt;&lt;p&gt;最近2场比赛，深圳男篮给广东男篮制造了很大的麻烦，而易建联也终于开启季后赛模式，合砍56分27篮板4抢断6盖帽，并且奉献了9次扣篮！事实证明，当易建联体能得到保证的时候，他在第四节是非常恐怖的，他才是广东男篮的核武器。对于深圳男篮来说，尽管总比分0-3落后，但能逼出全力模式的易建联，他们已经打得足够出色！&lt;/p&gt;', '1', '0', '1523899', 'admin', '2019-04-13 23:32:19', '2019-04-13 23:31:50', '1', '', '0');

-- ----------------------------
-- Table structure for `t_collection`
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of t_collection
-- ----------------------------
INSERT INTO `t_collection` VALUES ('2', '1523899', '1524120', '2019-03-31 00:48:54', '666');
INSERT INTO `t_collection` VALUES ('3', '1523899', '1542947', '2019-04-13 00:29:01', '刘强东谈996：我现在还能做到8116+8');
INSERT INTO `t_collection` VALUES ('4', '1523899', '1544326', '2019-04-13 23:02:35', '我的图片');

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
INSERT INTO `t_comment` VALUES ('1525037', '1524965', '1524120', '6666', '', '2019-03-31 13:24:38', '1');
INSERT INTO `t_comment` VALUES ('1525052', '1524965', '1524252', '8888', '', '2019-03-31 13:37:52', '1');
INSERT INTO `t_comment` VALUES ('1538170', '1523899', '1534142', '888', '', '2019-04-09 16:33:56', '0');
INSERT INTO `t_comment` VALUES ('1544270', '1544263', '1544268', '大家瞧瞧我的图片怎么样？face[哈哈] ', '', '2019-04-13 22:10:34', '0');
INSERT INTO `t_comment` VALUES ('1544271', '1523899', '1544268', '@王语纯 图片真不错', '', '2019-04-13 22:10:57', '0');
INSERT INTO `t_comment` VALUES ('1544273', '1523899', '1544268', 'img[http://www.panzhigao.vip/myimage/20190413221120999.jpeg] 我也上传了', '', '2019-04-13 22:11:40', '0');
INSERT INTO `t_comment` VALUES ('1544332', '1523899', '1544326', '你的逼毛好黑face[心] ', '', '2019-04-13 23:06:28', '0');
INSERT INTO `t_comment` VALUES ('1544340', '1544324', '1544326', '@admin 过奖了face[哈哈] ', '', '2019-04-13 23:13:56', '0');
INSERT INTO `t_comment` VALUES ('1544359', '1544358', '1544326', 'img[http://www.panzhigao.vip/myimage/20190413232800797.jpeg] ', '', '2019-04-13 23:28:07', '0');

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
INSERT INTO `t_link` VALUES ('15', '腾讯', 'http://www.qq.com', '2', '1', '2019-03-23 22:30:04', '1523899', '2019-04-12 23:40:06', '1523899');
INSERT INTO `t_link` VALUES ('16', '新浪', 'http://www.sina.com', '3', '1', '2019-03-23 22:30:36', '1523899', '2019-03-23 22:30:46', '1523899');
INSERT INTO `t_link` VALUES ('17', '淘宝', 'http://www.taobao.com', '4', '1', '2019-04-12 23:42:21', '1523899', '2019-04-12 23:42:26', '1523899');

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
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;

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
INSERT INTO `t_login_history` VALUES ('203', '1523899', 'admin', '2019-04-12 22:20:35', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('204', '1523899', 'admin', '2019-04-12 23:39:18', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('205', '1523899', 'admin', '2019-04-13 20:50:08', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('206', '1544263', 'wangyuchun', '2019-04-13 22:05:58', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('207', '1523899', 'admin', '2019-04-13 22:07:38', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('208', '1523899', 'admin', '2019-04-13 22:53:53', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('209', '1544324', 'zhaoweiyi', '2019-04-13 23:01:15', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('210', '1544358', 'linmengmeng', '2019-04-13 23:27:20', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `t_login_history` VALUES ('211', '1523899', 'admin', '2019-04-13 23:32:09', '2130706433', 'Mozilla/5.0 &#40;Windows NT 6.1; Win64; x64) AppleWebKit/537.36 &#40;KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');

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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='消息通知表';

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES ('32', '1', '1523899', 'admin', '1523899', '3', '1524120', '0', '666', '2019-03-30 22:48:12', '');
INSERT INTO `t_message` VALUES ('33', '1', '1523899', '钢铁侠', '1524965', '1', '1524120', '0', '666', '2019-03-31 13:24:38', '6666');
INSERT INTO `t_message` VALUES ('34', '1', '1523899', '钢铁侠', '1524965', '1', '1524252', '0', '66666', '2019-03-31 13:37:52', '8888');
INSERT INTO `t_message` VALUES ('35', '1', '1523899', 'admin', '1523899', '3', '1534142', '0', '江小白LOGO被宣告无效，世间要再无江小白？！', '2019-04-06 21:05:48', '');
INSERT INTO `t_message` VALUES ('36', '1', '1523899', 'admin', '1523899', '3', '1539499', '0', '5年降社保费8000亿元，下月起这部分人收入提高，看看你符合条件吗？', '2019-04-10 14:40:28', '');
INSERT INTO `t_message` VALUES ('37', '1', '1523899', 'admin', '1523899', '3', '1542947', '0', '刘强东谈996：我现在还能做到8116+8', '2019-04-13 00:28:23', '');
INSERT INTO `t_message` VALUES ('38', '1', '1544263', 'admin', '1523899', '3', '1544268', '0', '推女郎合集', '2019-04-13 22:09:47', '');
INSERT INTO `t_message` VALUES ('39', '1', '1544263', 'admin', '1523899', '1', '1544268', '0', '推女郎合集', '2019-04-13 22:10:57', '@王语纯 图片真不错');
INSERT INTO `t_message` VALUES ('40', '0', '1544263', 'admin', '1523899', '1', '1544268', '0', '推女郎合集', '2019-04-13 22:11:40', 'img[http://www.panzhigao.vip/myimage/20190413221120999.jpeg] 我也上传了');
INSERT INTO `t_message` VALUES ('41', '1', '1544263', 'admin', '1523899', '3', '1544321', '0', '王语纯的相册', '2019-04-13 22:59:31', '');
INSERT INTO `t_message` VALUES ('42', '1', '1544324', 'admin', '1523899', '3', '1544326', '0', '我的图片', '2019-04-13 23:02:27', '');
INSERT INTO `t_message` VALUES ('43', '1', '1544324', 'admin', '1523899', '1', '1544326', '0', '我的图片', '2019-04-13 23:06:28', '你的逼毛好黑face[心] ');
INSERT INTO `t_message` VALUES ('44', '1', '1544324', 'admin', '1523899', '3', '1544343', '0', '贫困补助不好意思申请，爱心午餐不好意思去吃，他们这样做......', '2019-04-13 23:16:04', '');
INSERT INTO `t_message` VALUES ('45', '1', '1544324', 'admin', '1523899', '2', '1544349', '0', 'wwwwww', '2019-04-13 23:20:41', '不好看');
INSERT INTO `t_message` VALUES ('46', '1', '1544324', 'admin', '1523899', '2', '1544352', '0', '55555555555', '2019-04-13 23:22:34', '标题不行');
INSERT INTO `t_message` VALUES ('47', '1', '1544324', 'admin', '1523899', '2', '1544352', '0', '55555555555', '2019-04-13 23:23:03', '444');
INSERT INTO `t_message` VALUES ('48', '0', '1544324', '林萌萌', '1544358', '1', '1544326', '0', '我的图片', '2019-04-13 23:28:07', 'img[http://www.panzhigao.vip/myimage/20190413232800797.jpeg] ');
INSERT INTO `t_message` VALUES ('49', '0', '1544324', 'admin', '1523899', '3', '1544352', '0', '青春少女', '2019-04-13 23:28:57', '');
INSERT INTO `t_message` VALUES ('50', '1', '1544358', 'admin', '1523899', '3', '1544364', '0', '33分14板3断4帽！男篮一哥终于露獠牙，他才是广东的核武器！', '2019-04-13 23:32:19', '');

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
  `ip` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

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
INSERT INTO `t_operate_log` VALUES ('162', '1523899', 'admin', 'admin(admin)文章分类下线：下线文章分类：国际', '604', '2130706433', '2019-04-10 14:02:43');
INSERT INTO `t_operate_log` VALUES ('163', '1523899', 'admin', 'admin(admin)文章分类下线：下线文章分类：999', '604', '2130706433', '2019-04-10 14:03:35');
INSERT INTO `t_operate_log` VALUES ('164', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类（国际）', '603', '2130706433', '2019-04-10 14:11:57');
INSERT INTO `t_operate_log` VALUES ('165', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类（999）', '603', '2130706433', '2019-04-10 14:11:59');
INSERT INTO `t_operate_log` VALUES ('166', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（财经）', '601', '2130706433', '2019-04-10 14:39:05');
INSERT INTO `t_operate_log` VALUES ('167', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类（财经）', '605', '2130706433', '2019-04-10 14:39:08');
INSERT INTO `t_operate_log` VALUES ('168', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（体育）', '601', '2130706433', '2019-04-10 14:39:29');
INSERT INTO `t_operate_log` VALUES ('169', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类（体育）', '603', '2130706433', '2019-04-10 14:39:31');
INSERT INTO `t_operate_log` VALUES ('170', '1523899', 'admin', 'admin(admin)文章分类删除：ArticleCategory(id=2, categoryName=999, sort=1, status=1)', '603', '2130706433', '2019-04-10 14:39:38');
INSERT INTO `t_operate_log` VALUES ('171', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（科技）', '601', '2130706433', '2019-04-10 14:39:45');
INSERT INTO `t_operate_log` VALUES ('172', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类（科技）', '605', '2130706433', '2019-04-10 14:39:49');
INSERT INTO `t_operate_log` VALUES ('173', '1523899', 'admin', 'admin(admin)编辑系统配置：网站名：好好学习-->好好学习,天天向上；', '401', '2130706433', '2019-04-12 22:22:02');
INSERT INTO `t_operate_log` VALUES ('174', '1523899', 'admin', 'admin(admin)编辑系统配置：', '401', '2130706433', '2019-04-12 22:22:19');
INSERT INTO `t_operate_log` VALUES ('175', '1523899', 'admin', 'admin(admin)编辑系统配置：网站标题：好好学习社区-->好好学习社区，天天向上；', '401', '2130706433', '2019-04-12 22:24:22');
INSERT INTO `t_operate_log` VALUES ('177', '1523899', 'admin', 'admin(admin)链接新增：链接名：淘宝，链接地址：http://www.taobao.com', '501', '2130706433', '2019-04-12 23:42:21');
INSERT INTO `t_operate_log` VALUES ('178', '1523899', 'admin', 'admin(admin)链接下线：上线链接，链接id=17', '504', '2130706433', '2019-04-12 23:42:26');
INSERT INTO `t_operate_log` VALUES ('179', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=555, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-04-13 21:32:20');
INSERT INTO `t_operate_log` VALUES ('180', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=4455, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-04-13 21:32:45');
INSERT INTO `t_operate_log` VALUES ('181', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=5553, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-04-13 21:32:55');
INSERT INTO `t_operate_log` VALUES ('182', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=5553, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-04-13 21:32:59');
INSERT INTO `t_operate_log` VALUES ('183', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=4455, marker=null, superAdminFlag=0)', '201', '2130706433', '2019-04-13 21:33:58');
INSERT INTO `t_operate_log` VALUES ('184', '1523899', 'admin', 'admin(admin)编辑角色：3,555-->555333', '202', '2130706433', '2019-04-13 21:34:29');
INSERT INTO `t_operate_log` VALUES ('185', '1523899', 'admin', 'admin(admin)编辑角色：3,555333-->555333', '202', '2130706433', '2019-04-13 21:40:23');
INSERT INTO `t_operate_log` VALUES ('186', '1523899', 'admin', 'admin(admin)编辑角色：3,555333-->555333', '202', '2130706433', '2019-04-13 21:41:32');
INSERT INTO `t_operate_log` VALUES ('187', '1523899', 'admin', 'admin(admin)编辑角色：3,555333-->555333', '202', '2130706433', '2019-04-13 21:46:20');
INSERT INTO `t_operate_log` VALUES ('188', '1523899', 'admin', 'admin(admin)编辑角色：1,超级管理员-->超级管理员', '202', '2130706433', '2019-04-13 21:49:30');
INSERT INTO `t_operate_log` VALUES ('189', '1523899', 'admin', 'admin(admin)编辑角色：2,注册用户-->注册用户', '202', '2130706433', '2019-04-13 21:49:53');
INSERT INTO `t_operate_log` VALUES ('190', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=555333, marker=null, superAdminFlag=0, remark=333)', '201', '2130706433', '2019-04-13 21:49:56');
INSERT INTO `t_operate_log` VALUES ('191', '1523899', 'admin', 'admin(admin)编辑角色：1,超级管理员-->超级管理员', '202', '2130706433', '2019-04-13 21:50:06');
INSERT INTO `t_operate_log` VALUES ('192', '1523899', 'admin', 'admin(admin)新增角色：Role(roleName=33, marker=null, superAdminFlag=0, remark=222)', '201', '2130706433', '2019-04-13 21:53:14');
INSERT INTO `t_operate_log` VALUES ('193', '1523899', 'admin', 'admin(admin)编辑角色：6,33-->33', '202', '2130706433', '2019-04-13 21:53:17');
INSERT INTO `t_operate_log` VALUES ('194', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（漫画)', '601', '2130706433', '2019-04-13 21:59:36');
INSERT INTO `t_operate_log` VALUES ('195', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类漫画', '605', '2130706433', '2019-04-13 21:59:56');
INSERT INTO `t_operate_log` VALUES ('196', '1523899', 'admin', 'admin(admin)编辑角色：角色id：6，编辑内容：角色备注：222333-->2223334444；', '202', '2130706433', '2019-04-13 22:01:16');
INSERT INTO `t_operate_log` VALUES ('197', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（图片)', '601', '2130706433', '2019-04-13 22:08:16');
INSERT INTO `t_operate_log` VALUES ('198', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类图片', '605', '2130706433', '2019-04-13 22:54:15');
INSERT INTO `t_operate_log` VALUES ('199', '1523899', 'admin', 'admin(admin)文章分类新增：分类名称（社会)', '601', '2130706433', '2019-04-13 23:15:15');
INSERT INTO `t_operate_log` VALUES ('200', '1523899', 'admin', 'admin(admin)文章分类上线：上线文章分类社会', '605', '2130706433', '2019-04-13 23:15:18');

-- ----------------------------
-- Table structure for `t_permission`
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='图片信息表';

-- ----------------------------
-- Records of t_picture
-- ----------------------------
INSERT INTO `t_picture` VALUES ('19', '2019-03-31 14:17:57', '1524965', 'http://www.panzhigao.vip/myimage/20190331141757933.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190331141757933.jpg');
INSERT INTO `t_picture` VALUES ('20', '2019-03-31 14:18:13', '1524965', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190331141813320.jpg');
INSERT INTO `t_picture` VALUES ('21', '2019-04-13 20:51:37', '1523899', 'http://www.panzhigao.vip/myimage/20190413205137933.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413205137933.jpg');
INSERT INTO `t_picture` VALUES ('22', '2019-04-13 22:06:58', '1544263', 'http://www.panzhigao.vip/myimage/20190413220658765.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413220658765.jpg');
INSERT INTO `t_picture` VALUES ('23', '2019-04-13 22:08:55', '1544263', 'http://www.panzhigao.vip/myimage/20190413220855043.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413220855043.jpeg');
INSERT INTO `t_picture` VALUES ('24', '2019-04-13 22:09:09', '1544263', 'http://www.panzhigao.vip/myimage/20190413220909629.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413220909629.jpeg');
INSERT INTO `t_picture` VALUES ('25', '2019-04-13 22:09:16', '1544263', 'http://www.panzhigao.vip/myimage/20190413220916218.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413220916218.jpeg');
INSERT INTO `t_picture` VALUES ('26', '2019-04-13 22:11:20', '1523899', 'http://www.panzhigao.vip/myimage/20190413221120999.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413221120999.jpeg');
INSERT INTO `t_picture` VALUES ('27', '2019-04-13 22:52:32', '1544263', 'http://www.panzhigao.vip/myimage/20190413225232407.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413225232407.jpg');
INSERT INTO `t_picture` VALUES ('28', '2019-04-13 22:58:36', '1544263', 'http://www.panzhigao.vip/myimage/20190413225836356.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413225836356.jpeg');
INSERT INTO `t_picture` VALUES ('29', '2019-04-13 22:58:45', '1544263', 'http://www.panzhigao.vip/myimage/20190413225845281.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413225845281.jpeg');
INSERT INTO `t_picture` VALUES ('30', '2019-04-13 22:58:54', '1544263', 'http://www.panzhigao.vip/myimage/20190413225854170.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413225854170.jpeg');
INSERT INTO `t_picture` VALUES ('31', '2019-04-13 22:59:04', '1544263', 'http://www.panzhigao.vip/myimage/20190413225904915.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413225904915.jpeg');
INSERT INTO `t_picture` VALUES ('32', '2019-04-13 23:01:34', '1544324', 'http://www.panzhigao.vip/myimage/20190413230134388.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413230134388.jpg');
INSERT INTO `t_picture` VALUES ('33', '2019-04-13 23:01:47', '1544324', 'http://www.panzhigao.vip/myimage/20190413230147523.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413230147523.jpeg');
INSERT INTO `t_picture` VALUES ('34', '2019-04-13 23:01:53', '1544324', 'http://www.panzhigao.vip/myimage/20190413230153982.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413230153982.jpeg');
INSERT INTO `t_picture` VALUES ('35', '2019-04-13 23:01:58', '1544324', 'http://www.panzhigao.vip/myimage/20190413230158498.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413230158498.jpeg');
INSERT INTO `t_picture` VALUES ('36', '2019-04-13 23:02:06', '1544324', 'http://www.panzhigao.vip/myimage/20190413230206559.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413230206559.jpeg');
INSERT INTO `t_picture` VALUES ('37', '2019-04-13 23:20:20', '1544324', 'http://www.panzhigao.vip/myimage/20190413232020989.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413232020989.jpeg');
INSERT INTO `t_picture` VALUES ('38', '2019-04-13 23:22:16', '1544324', 'http://www.panzhigao.vip/myimage/20190413232216562.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413232216562.jpeg');
INSERT INTO `t_picture` VALUES ('39', '2019-04-13 23:27:42', '1544358', 'http://www.panzhigao.vip/myimage/20190413232742715.jpg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413232742715.jpg');
INSERT INTO `t_picture` VALUES ('40', '2019-04-13 23:28:00', '1544358', 'http://www.panzhigao.vip/myimage/20190413232800797.jpeg', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\20190413232800797.jpeg');

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
) ENGINE=InnoDB AUTO_INCREMENT=1525054 DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of t_praise
-- ----------------------------
INSERT INTO `t_praise` VALUES ('1525050', '1524120', '1525037', '1524965', '2019-03-31 13:37:12');
INSERT INTO `t_praise` VALUES ('1525053', '1524252', '1525052', '1524965', '2019-03-31 13:37:54');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '2019-03-30 19:56:04', '1523899', '2019-04-13 21:50:06', '1523899', '1', '该角色不能删除，拥有最大权限');
INSERT INTO `t_role` VALUES ('2', '注册用户', '2019-03-31 12:20:02', '1523899', '2019-04-13 21:49:53', '1523899', '0', '普通用户注册后获取的权限');
INSERT INTO `t_role` VALUES ('6', '33', '2019-04-13 21:53:14', '1523899', '2019-04-13 22:01:16', '1523899', '0', '2223334444');

-- ----------------------------
-- Table structure for `t_role_permission`
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
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

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
  PRIMARY KEY (`id`),
  KEY `idex_user_id_type_score_date` (`user_id`,`type`,`score_date`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8 COMMENT='积分历史表';

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
INSERT INTO `t_score_history` VALUES ('269', '1523899', '1', '登陆', '5', '2019-04-12', '2019-04-12 22:20:35');
INSERT INTO `t_score_history` VALUES ('270', '1523899', '4', '签到', '5', '2019-04-12', '2019-04-12 23:46:09');
INSERT INTO `t_score_history` VALUES ('271', '1523899', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 00:28:23');
INSERT INTO `t_score_history` VALUES ('272', '1523899', '4', '签到', '5', '2019-04-13', '2019-04-13 00:28:47');
INSERT INTO `t_score_history` VALUES ('273', '1523899', '1', '登陆', '5', '2019-04-13', '2019-04-13 20:50:08');
INSERT INTO `t_score_history` VALUES ('274', '1544263', '5', '注册', '20', '2019-04-13', '2019-04-13 22:05:58');
INSERT INTO `t_score_history` VALUES ('275', '1544263', '1', '登陆', '5', '2019-04-13', '2019-04-13 22:05:58');
INSERT INTO `t_score_history` VALUES ('276', '1544263', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 22:09:47');
INSERT INTO `t_score_history` VALUES ('277', '1544263', '3', '回帖', '2', '2019-04-13', '2019-04-13 22:10:34');
INSERT INTO `t_score_history` VALUES ('278', '1523899', '3', '回帖', '2', '2019-04-13', '2019-04-13 22:10:57');
INSERT INTO `t_score_history` VALUES ('279', '1523899', '3', '回帖', '2', '2019-04-13', '2019-04-13 22:11:40');
INSERT INTO `t_score_history` VALUES ('280', '1544263', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 22:59:31');
INSERT INTO `t_score_history` VALUES ('281', '1544324', '5', '注册', '20', '2019-04-13', '2019-04-13 23:01:15');
INSERT INTO `t_score_history` VALUES ('282', '1544324', '1', '登陆', '5', '2019-04-13', '2019-04-13 23:01:15');
INSERT INTO `t_score_history` VALUES ('283', '1544324', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 23:02:27');
INSERT INTO `t_score_history` VALUES ('284', '1523899', '3', '回帖', '2', '2019-04-13', '2019-04-13 23:06:28');
INSERT INTO `t_score_history` VALUES ('285', '1544324', '3', '回帖', '2', '2019-04-13', '2019-04-13 23:13:56');
INSERT INTO `t_score_history` VALUES ('286', '1544324', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 23:16:04');
INSERT INTO `t_score_history` VALUES ('287', '1544358', '5', '注册', '20', '2019-04-13', '2019-04-13 23:27:20');
INSERT INTO `t_score_history` VALUES ('288', '1544358', '1', '登陆', '5', '2019-04-13', '2019-04-13 23:27:20');
INSERT INTO `t_score_history` VALUES ('289', '1544358', '3', '回帖', '2', '2019-04-13', '2019-04-13 23:28:07');
INSERT INTO `t_score_history` VALUES ('290', '1544324', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 23:28:57');
INSERT INTO `t_score_history` VALUES ('291', '1544358', '2', '发表文章成功', '5', '2019-04-13', '2019-04-13 23:32:19');

-- ----------------------------
-- Table structure for `t_system_config`
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
INSERT INTO `t_system_config` VALUES ('1', '好好学习,天天向上', '好好学习社区，天天向上', '好好学习社区', '好好学习社区，致力于为web学习交流', 'C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\myimage\\', '2019 © &lt;a href=\"/\" target=\"_blank\"&gt;panzhigao.vip 出品&lt;/a&gt;京ICP备18031226号', 'body {\n\n}', 'https://s22.cnzz.com/z_stat.php?id=1274156186&web_id=1274156186', '2019-03-16 12:31:50', '1523899', '2019-04-12 22:24:22', '1523899');

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
  `update_user` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人id',
  `admin_flag` int(1) NOT NULL DEFAULT '0' COMMENT '管理员标志，0-否，1-是',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1523899', '1', 'admin', 'admin', '6FA456619C9CD1E640134D615945CBA05D5F26DC32003EF3E59DA2A5', '2019-03-30 18:41:28', '2019-04-13 23:32:09', '1', '18911536627', '2019-04-13 21:26:22', 'http://www.panzhigao.vip/myimage/20190413205137933.jpg', '0', '1', '北京');
INSERT INTO `t_user` VALUES ('1524965', '1', 'gangtiexia', '钢铁侠', 'D2D5F45EA8D6F5D54EDE7A768BF1A3625F93B61F428B71539F7D9B7A', '2019-03-31 12:16:08', '2019-03-31 13:10:48', '1', '', '2019-03-31 14:18:13', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', '0', '0', '');
INSERT INTO `t_user` VALUES ('1525055', '0', 'lvdengxia', '绿灯侠', '0701BC65DCF322E5A6304ED34D683AEE3F80DD262C26C8043BCB14B9', '2019-03-31 13:38:25', '2019-03-31 13:38:25', '1', '', '2019-03-31 13:38:25', '/static/images/default_portrait.jpg', '0', '0', '');
INSERT INTO `t_user` VALUES ('1544263', '2', 'wangyuchun', '王语纯', 'B49D723D4317CAC3FDE01FCC3903DC0EE96BE711857028CD7B6E58FC', '2019-04-13 22:05:58', '2019-04-13 22:05:58', '1', '', '2019-04-13 22:52:32', 'http://www.panzhigao.vip/myimage/20190413225232407.jpg', '0', '0', '天津');
INSERT INTO `t_user` VALUES ('1544324', '2', 'zhaoweiyi', '赵惟依', 'BE46D1B6B2A08B9971AAD73907F693CA55A523F85A491A5277507FFC', '2019-04-13 23:01:15', '2019-04-13 23:01:15', '1', '', '2019-04-13 23:01:34', 'http://www.panzhigao.vip/myimage/20190413230134388.jpg', '0', '0', '');
INSERT INTO `t_user` VALUES ('1544358', '2', 'linmengmeng', '林萌萌', 'C8DC00AEE90FDC74474EE951EF79A4126C9CB7DBAB87157D16D0B9C1', '2019-04-13 23:27:20', '2019-04-13 23:27:20', '1', '', '2019-04-13 23:27:42', 'http://www.panzhigao.vip/myimage/20190413232742715.jpg', '0', '0', '');

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
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `continuous_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续登陆天数',
  `continuous_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  `total_login_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共登陆天数',
  `total_check_in_days` int(11) NOT NULL DEFAULT '0' COMMENT '总共签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1544359 DEFAULT CHARSET=utf8 COMMENT='用户拓展表';

-- ----------------------------
-- Records of t_user_extension
-- ----------------------------
INSERT INTO `t_user_extension` VALUES ('1523899', 'admin', 'http://www.panzhigao.vip/myimage/20190413205137933.jpg', '哈哈23333333333333333333', '2019-03-30 18:41:28', '2019-04-13 23:06:28', '4', '4', '113', '8', '5', '8', '5');
INSERT INTO `t_user_extension` VALUES ('1524965', '钢铁侠', 'http://www.panzhigao.vip/myimage/20190331141813320.jpg', '', '2019-03-31 12:16:08', '2019-03-31 14:18:13', '0', '2', '29', '0', '1', '0', '1');
INSERT INTO `t_user_extension` VALUES ('1525055', '绿灯侠', '/static/images/default_portrait.jpg', '', '2019-03-31 13:38:25', '2019-03-31 13:38:36', '0', '0', '30', '1', '1', '1', '1');
INSERT INTO `t_user_extension` VALUES ('1544263', '王语纯', 'http://www.panzhigao.vip/myimage/20190413225232407.jpg', '', '2019-04-13 22:05:58', '2019-04-13 22:59:31', '2', '1', '37', '1', '0', '1', '0');
INSERT INTO `t_user_extension` VALUES ('1544324', '赵惟依', 'http://www.panzhigao.vip/myimage/20190413230134388.jpg', '', '2019-04-13 23:01:15', '2019-04-13 23:28:57', '3', '1', '42', '1', '0', '1', '0');
INSERT INTO `t_user_extension` VALUES ('1544358', '林萌萌', 'http://www.panzhigao.vip/myimage/20190413232742715.jpg', '', '2019-04-13 23:27:20', '2019-04-13 23:32:19', '1', '1', '32', '1', '0', '1', '0');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(10) NOT NULL DEFAULT '0' COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1523899', '1', '2019-03-30 18:41:28', '1523898');
INSERT INTO `t_user_role` VALUES ('2', '1524965', '2', '2019-03-31 12:16:08', '1524964');
INSERT INTO `t_user_role` VALUES ('3', '1525055', '2', '2019-03-31 13:38:25', '1525055');
INSERT INTO `t_user_role` VALUES ('4', '1544263', '2', '2019-04-13 22:05:58', '1544263');
INSERT INTO `t_user_role` VALUES ('5', '1544324', '2', '2019-04-13 23:01:15', '1544324');
INSERT INTO `t_user_role` VALUES ('6', '1544358', '2', '2019-04-13 23:27:20', '1544358');
