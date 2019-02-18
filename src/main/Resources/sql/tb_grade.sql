/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:03:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_grade
-- ----------------------------
DROP TABLE IF EXISTS `tb_grade`;
CREATE TABLE `tb_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test_score` int(3) DEFAULT '0' COMMENT '考试分',
  `course_score` int(3) DEFAULT '0' COMMENT '平时分',
  `elective_id` int(11) DEFAULT NULL,
  `stu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
