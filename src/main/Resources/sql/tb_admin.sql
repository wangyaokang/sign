/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:01:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(255) NOT NULL COMMENT '微信ID',
  `wx_name` varchar(255) DEFAULT NULL COMMENT '微信昵称',
  `wx_avatar_url` varchar(255) DEFAULT NULL COMMENT '微信头像url',
  `real_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型',
  `status` int(1) unsigned DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `wxId` (`wx_id`),
  FULLTEXT KEY `ft_ad_name` (`real_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
