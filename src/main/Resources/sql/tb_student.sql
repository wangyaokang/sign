/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 80011
Source Host           : 127.0.0.1:3306
Source Database       : sign

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-18 00:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(255) DEFAULT NULL COMMENT '微信ID',
  `wx_name` varchar(255) DEFAULT NULL COMMENT '微信昵称',
  `wx_avatar_url` varchar(255) DEFAULT NULL COMMENT '微信头像URL',
  `real_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型',
  `sno` varchar(255) DEFAULT NULL COMMENT '学号',
  `class_id` int(11) DEFAULT NULL COMMENT '班级ID',
  `status` int(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
