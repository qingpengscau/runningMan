/*
Navicat MySQL Data Transfer

Source Server         : qingpeng
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : runningman

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2018-10-12 15:38:53
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of address_info
-- ----------------------------

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

  `type_prewait_time` varchar(20) NOT NULL COMMENT '用户预计等待时间',
  `order_start_time` varchar(20) DEFAULT NULL COMMENT '订单开始时间，从被接单开始算起',
  `order_finish_time` varchar(20) DEFAULT NULL COMMENT '订单完成时间',
  `fee` double(10,0) NOT NULL COMMENT '所需费用',
  `status` int(1) NOT NULL COMMENT '用来标记该订单是否被完成，0仅发布未有人接单，1已被接单但未完成，2已完成',
  `comment` varchar(255) DEFAULT NULL COMMENT '订单备注',
  PRIMARY KEY (`id`),
  KEY `FK_User_Releaseman` (`release_man_id`),
  KEY `FK_User_Excute_man` (`execute_man_id`),
  KEY `FK_Departure_id` (`departure_id`),
  KEY `FK_Destination_id` (`destination_id`),
  CONSTRAINT `FK_Departure_id` FOREIGN KEY (`departure_id`) REFERENCES `address_info` (`id`),
  CONSTRAINT `FK_Destination_id` FOREIGN KEY (`destination_id`) REFERENCES `address_info` (`id`),
  CONSTRAINT `FK_User_Excute_man` FOREIGN KEY (`execute_man_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_User_Releaseman` FOREIGN KEY (`release_man_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of fetch_order_info
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of secure_info
-- ----------------------------

-- ----------------------------
-- Table structure for `shopping_order_info`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_order_info`;
CREATE TABLE `shopping_order_info` (
  `id` varchar(255) NOT NULL,
  `release_man_id` varchar(50) CHARACTER SET latin1 NOT NULL,
  `execute_man_id` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '执行人，与用户表主键绑定',
  `release_time` varchar(20) CHARACTER SET latin1 NOT NULL COMMENT '订单发布时间',
  `shopping_address` varchar(255) NOT NULL,
  `destination_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '目的地，从address表中选取',
  `pick_time` varchar(20) NOT NULL,
  `prewait_time` varchar(20) NOT NULL,
  `comment` varchar(255) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单备注',
  `price` double(10,0) NOT NULL,
  `order_start_time` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单开始时间，从被接单开始算起',
  `order_finish_time` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单完成时间',
  `fee` double(10,0) NOT NULL COMMENT '所需费用',
  `status` int(1) NOT NULL COMMENT '用来标记该订单是否被完成，0仅发布未有人接单，1已被接单但未完成，2已完成',
  `commodity` varchar(255) DEFAULT NULL,
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
  `user_sex` int(1) DEFAULT NULL,
  `image_path` varchar(50) DEFAULT NULL COMMENT '用户头像图片路径',
  `location` varchar(20) DEFAULT NULL COMMENT '用户所在地',
  `gold` int(10) DEFAULT NULL COMMENT '用户所拥有的金币',
  `user_cell` varchar(11) NOT NULL,
  `user_regtime` varchar(20) DEFAULT NULL,
  `user_mark` int(1) DEFAULT '0' COMMENT '标记位，删除时被置1，默认为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'qingpeng', '123456', '1', '', 'huashan', '100', '1231231', '2017-09-08', null);
INSERT INTO `user_info` VALUES ('e2fb964abc18425a81ef0de7831b254c', null, '87654321', '0', '', null, '0', '15813362826', '2018-09-30 17:19:46', '0');
