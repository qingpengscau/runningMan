/*
Navicat MySQL Data Transfer

Source Server         : qingpeng
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : runningman

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-09 11:04:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fetch_order_info
-- ----------------------------
DROP TABLE IF EXISTS `fetch_order_info`;
CREATE TABLE `fetch_order_info` (
  `id` varchar(255) NOT NULL,
  `release_man_id` varchar(50) NOT NULL,
  `execute_man_id` varchar(50) DEFAULT NULL,
  `receive_man_id` varchar(50) NOT NULL,
  `release_time` varchar(20) NOT NULL,
  `departure` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `weight` double(5,0) DEFAULT NULL,
  `type` int(1) NOT NULL,
  `prewait_time` int(5) DEFAULT NULL,
  `express_company` varchar(20) DEFAULT NULL,
  `order_start_time` varchar(20) DEFAULT NULL,
  `order_finish_time` varchar(20) DEFAULT NULL,
  `fee` double(10,0) NOT NULL,
  `issue_descri` varchar(255) NOT NULL,
  `status` int(1) NOT NULL,
  `comment` varchar(50) DEFAULT NULL,
  `remark` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_User_Releaseman` (`release_man_id`),
  KEY `FK_User_Excute_man` (`execute_man_id`),
  KEY `FK_Receiver` (`receive_man_id`),
  CONSTRAINT `FK_Receiver` FOREIGN KEY (`receive_man_id`) REFERENCES `receiver_info` (`id`),
  CONSTRAINT `FK_User_Excute_man` FOREIGN KEY (`execute_man_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_User_Releaseman` FOREIGN KEY (`release_man_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of fetch_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for receiver_info
-- ----------------------------
DROP TABLE IF EXISTS `receiver_info`;
CREATE TABLE `receiver_info` (
  `id` varchar(50) NOT NULL,
  `receiver_name` varchar(20) NOT NULL,
  `deliver_address` varchar(50) NOT NULL,
  `receiver_cell` varchar(20) NOT NULL,
  `receiver_sex` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of receiver_info
-- ----------------------------

-- ----------------------------
-- Table structure for secure_info
-- ----------------------------
DROP TABLE IF EXISTS `secure_info`;
CREATE TABLE `secure_info` (
  `id` varchar(50) NOT NULL,
  `student_id` int(20) NOT NULL,
  `secure_question` varchar(50) DEFAULT NULL,
  `secure_answer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_USER_SECURE` FOREIGN KEY (`id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of secure_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(50) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `user_sex` int(1) NOT NULL,
  `location` varchar(20) NOT NULL,
  `gold` int(10) NOT NULL,
  `user_cell` int(11) DEFAULT NULL,
  `user_regtime` varchar(20) DEFAULT NULL,
  `user_mark` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'qingpeng', '123456', '1', 'huashan', '100', '1231231', '2017-09-08', null);
