/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:03:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sign_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign_info`;
CREATE TABLE `tb_sign_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL COMMENT '签到起始时间',
  `stop_date` datetime NOT NULL COMMENT '签到截止时间',
  `address` varchar(255) NOT NULL COMMENT '签到地点',
  `latitude` varchar(255) NOT NULL COMMENT '纬度',
  `longitude` varchar(255) NOT NULL COMMENT '经度',
  `admin_id` int(11) NOT NULL COMMENT '管理者ID',
  `class_id` int(11) NOT NULL COMMENT '班级ID',
  `course_id` int(11) NOT NULL COMMENT '课程ID',
  `status` int(1) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
