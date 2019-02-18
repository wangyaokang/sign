/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:03:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sign
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign`;
CREATE TABLE `tb_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info_id` int(11) NOT NULL COMMENT '签到信息ID',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `sign_date` datetime DEFAULT NULL COMMENT '签到时间',
  `sign_address` varchar(255) DEFAULT NULL COMMENT '签到地址',
  `status` int(1) DEFAULT NULL COMMENT '签到状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_stu_info` (`info_id`,`stu_id`) COMMENT '签到信息和学生id唯一'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
