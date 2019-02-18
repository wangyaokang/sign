/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:04:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_task_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_info`;
CREATE TABLE `tb_task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL COMMENT '教师ID',
  `course_id` int(11) NOT NULL COMMENT '课程ID',
  `class_id` int(11) NOT NULL COMMENT '班级ID',
  `deadline_time` datetime NOT NULL COMMENT '任务截止时间',
  `title` varchar(255) DEFAULT NULL COMMENT '题目',
  `content` text COMMENT '作业内容',
  `status` int(1) DEFAULT NULL COMMENT '任务是否有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft_conent` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
