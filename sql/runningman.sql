/*
Navicat MySQL Data Transfer

Source Server         : fly
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : runningman

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2018-10-14 16:29:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `address_info`
-- ----------------------------
DROP TABLE IF EXISTS `address_info`;
CREATE TABLE `address_info` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` varchar(50) NOT NULL,
  `cell` varchar(20) NOT NULL,
  `sex` int(1) DEFAULT NULL,
  `area` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User` (`user_id`),
  CONSTRAINT `FK_User` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address_info
-- ----------------------------
INSERT INTO `address_info` VALUES ('0a64c571dcd542d9b267be3cc570a943', 'e2fb964abc18425a81ef0de7831b254c', 'sss', 'sdsgsgs', '15899999999', '0', '???');
INSERT INTO `address_info` VALUES ('1', 'e2fb964abc18425a81ef0de7831b254c', 'qingpeng', '123123', '123123', '1', 'huashan');
INSERT INTO `address_info` VALUES ('90a5844076e440fd978ecf54ac12d08b', 'e2fb964abc18425a81ef0de7831b254c', '1', '1', '15822222222', '0', '???');
INSERT INTO `address_info` VALUES ('a3222b21e865438695229cf445bd6e46', 'e2fb964abc18425a81ef0de7831b254c', '1', '1', '15822222222', '0', '???');
INSERT INTO `address_info` VALUES ('a8fccff83d504f179e4f97fa6e6bb8ae', 'e2fb964abc18425a81ef0de7831b254c', 'fsdfs', 'dasdsa', '15855555555', '0', '???');
INSERT INTO `address_info` VALUES ('ca2806337d844b418a84e46da47731d6', 'e2fb964abc18425a81ef0de7831b254c', 'zhenhui', '18dong', '15802035370', '0', '???');
INSERT INTO `address_info` VALUES ('e133a48db6324def84f1df0b0be5549d', 'e2fb964abc18425a81ef0de7831b254c', '1', '1', '15822222222', '0', '???');
INSERT INTO `address_info` VALUES ('e5d16ff67499491382396121375195f3', 'e2fb964abc18425a81ef0de7831b254c', 'vg', 'cgcfgcf', '15800000000', '0', '???');
INSERT INTO `address_info` VALUES ('fc0790fa9454489b9af7225765139334', 'e2fb964abc18425a81ef0de7831b254c', '12311', '123123', '15811111555', '0', '???');
INSERT INTO `address_info` VALUES ('fed2437135cd4a5abcbd7784b7009ed2', 'e2fb964abc18425a81ef0de7831b254c', 'opLW', '18', '15802035370', '0', '???');

-- ----------------------------
-- Table structure for `fetch_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `fetch_order_info`;
CREATE TABLE `fetch_order_info` (
  `id` varchar(255) NOT NULL,
  `release_man_id` varchar(50) NOT NULL COMMENT '发布人id，与用户表主键绑定',
  `execute_man_id` varchar(50) DEFAULT NULL COMMENT '执行人，与用户表主键绑定',
  `release_time` varchar(20) NOT NULL COMMENT '订单发布时间',
  `departure_id` varchar(50) NOT NULL COMMENT '起始地，从address表中选取',
  `destination_id` varchar(50) NOT NULL COMMENT '目的地，从address表中选取',
  `type_weight` varchar(50) DEFAULT NULL COMMENT '物品重量',
  `pick_time` varchar(50) NOT NULL,
  `order_start_time` varchar(20) DEFAULT NULL COMMENT '订单开始时间，从被接单开始算起',
  `order_finish_time` varchar(20) DEFAULT NULL COMMENT '订单完成时间',
  `fee` double(10,0) NOT NULL COMMENT '所需费用',
  `comment` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `prewait_time` varchar(50) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '0表示進發佈，1表示已發佈但未被接收，2表示已完成，3表示超時',
  PRIMARY KEY (`id`),
  KEY `FK_User_Releaseman` (`release_man_id`),
  KEY `FK_User_Excute_man` (`execute_man_id`),
  KEY `FK_Departure_id` (`departure_id`),
  KEY `FK_Destination_id` (`destination_id`),
  CONSTRAINT `FK_Departure_id` FOREIGN KEY (`departure_id`) REFERENCES `address_info` (`id`),
  CONSTRAINT `FK_Destination_id` FOREIGN KEY (`destination_id`) REFERENCES `address_info` (`id`),
  CONSTRAINT `FK_User_Excute_man` FOREIGN KEY (`execute_man_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_User_Releaseman` FOREIGN KEY (`release_man_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fetch_order_info
-- ----------------------------
INSERT INTO `fetch_order_info` VALUES ('7650a61213bc4974a463b081dc2536ad', 'e2fb964abc18425a81ef0de7831b254c', null, '10-14 16:13', 'ca2806337d844b418a84e46da47731d6', 'fed2437135cd4a5abcbd7784b7009ed2', '???2kg', '10-14 16:20', null, null, '5', '1111', '10-14 16:40', '0');
INSERT INTO `fetch_order_info` VALUES ('92b07c9ba95e4d46aa32729d1cfad461', 'e2fb964abc18425a81ef0de7831b254c', null, '10-14 16:10', 'fed2437135cd4a5abcbd7784b7009ed2', 'ca2806337d844b418a84e46da47731d6', '???2kg', '10-14 08:20', null, null, '10', '1111', '10-14 08:30', '3');
INSERT INTO `fetch_order_info` VALUES ('ec2aa4d5820c477999f57aa665e5d79f', 'e2fb964abc18425a81ef0de7831b254c', null, '10-14 13:59', 'fed2437135cd4a5abcbd7784b7009ed2', 'ca2806337d844b418a84e46da47731d6', '?????2kg', '10-14 06:00', null, null, '5', 'ffff', '10-14 16:36', '0');

-- ----------------------------
-- Table structure for `secure_info`
-- ----------------------------
DROP TABLE IF EXISTS `secure_info`;
CREATE TABLE `secure_info` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) NOT NULL COMMENT '与用户绑定的键',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学生的学号，用来以后与学校的数据库做校对，确保身份安全',
  `secure_question` varchar(50) DEFAULT NULL COMMENT '密保问题，一个用户有两个密保问题',
  `secure_answer` varchar(50) DEFAULT NULL,
  KEY `FK_User_Secure` (`id`),
  KEY `FK_User_id` (`user_id`),
  CONSTRAINT `FK_User_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of secure_info
-- ----------------------------
INSERT INTO `secure_info` VALUES ('3b3894c56e934f1ea0f03bc3944c0f3e', '486670f5e4554463a52fc1d94e225747', null, '???????', '1');
INSERT INTO `secure_info` VALUES ('7a688538c3a84012b354cf548d873105', '486670f5e4554463a52fc1d94e225747', null, '???????', '1');

-- ----------------------------
-- Table structure for `shopping_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_order_info`;
CREATE TABLE `shopping_order_info` (
  `id` varchar(255) NOT NULL,
  `release_man_id` varchar(50) CHARACTER SET utf8 NOT NULL,
  `execute_man_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '执行人，与用户表主键绑定',
  `release_time` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '订单发布时间',
  `shopping_address` varchar(255) NOT NULL,
  `destination_id` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '目的地，从address表中选取',
  `pick_time` varchar(20) NOT NULL,
  `prewait_time` varchar(20) NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '订单备注',
  `price` double(10,0) NOT NULL,
  `order_start_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '订单开始时间，从被接单开始算起',
  `order_finish_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '订单完成时间',
  `fee` double(10,0) NOT NULL COMMENT '所需费用',
  `status` int(1) NOT NULL COMMENT '用來表示是取送件還是代購（配合之前的工作）',
  `commodity` varchar(255) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '0表示未接單，1表示已接單但未完成，2表示完成，3表示超時',
  PRIMARY KEY (`id`),
  KEY `FK_shopping_Departure_id` (`destination_id`) USING BTREE,
  KEY `FK_shopping_Excute_man` (`execute_man_id`) USING BTREE,
  KEY `FK_Shopping_Releaseman` (`release_man_id`),
  CONSTRAINT `FK_Shopping_Departure_id` FOREIGN KEY (`destination_id`) REFERENCES `address_info` (`id`),
  CONSTRAINT `FK_Shopping_Excute_man` FOREIGN KEY (`execute_man_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_Shopping_Releaseman` FOREIGN KEY (`release_man_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shopping_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(50) NOT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_password` varchar(20) NOT NULL,
  `user_sex` int(1) DEFAULT NULL COMMENT '0男1女2未知',
  `image_path` varchar(50) DEFAULT NULL COMMENT '用户头像图片路径',
  `location` varchar(20) DEFAULT NULL COMMENT '用户所在地',
  `gold` int(10) DEFAULT NULL COMMENT '用户所拥有的金币',
  `user_cell` varchar(11) NOT NULL,
  `user_regtime` varchar(20) DEFAULT NULL,
  `user_mark` int(1) DEFAULT '0' COMMENT '标记位，删除时被置1，默认为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'qingpeng', '123456', '1', '', 'huashan', '100', '1231231', '2017-09-08', null);
INSERT INTO `user_info` VALUES ('486670f5e4554463a52fc1d94e225747', '', '12345678', '2', null, '', '0', '18922358421', '2018-10-14 16:09:15', '0');
INSERT INTO `user_info` VALUES ('e2fb964abc18425a81ef0de7831b254c', 'opLw', '87654321', '0', '', '1551', '0', '15813362826', '2018-09-30 17:19:46', '0');
