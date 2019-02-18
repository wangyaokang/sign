/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:03:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_classes_course_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_classes_course_admin`;
CREATE TABLE `tb_classes_course_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL COMMENT '班级ID',
  `course_id` int(11) DEFAULT NULL COMMENT '课程ID',
  `admin_id` int(11) DEFAULT NULL COMMENT '教师ID',
  `course_score_ratio` int(3) DEFAULT '50' COMMENT '平时分占比',
  `test_score_ratio` int(3) DEFAULT '50' COMMENT '考试分占比',
  `term_start_date` datetime DEFAULT NULL COMMENT '学期起始日期',
  `term_stop_date` datetime DEFAULT NULL COMMENT '学期结束日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_id` (`class_id`,`course_id`,`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
