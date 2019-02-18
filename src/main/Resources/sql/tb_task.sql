/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:04:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info_id` int(11) DEFAULT NULL COMMENT '任务信息ID',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `up_file_url` varchar(255) DEFAULT NULL COMMENT '上传文件url',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `score` int(3) DEFAULT NULL COMMENT '评分',
  `status` int(1) DEFAULT NULL COMMENT '任务状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
