/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 80011
Source Host           : 127.0.0.1:3306
Source Database       : sign

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-18 00:19:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_task_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_info`;
CREATE TABLE `tb_task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL COMMENT '管理者ID',
  `course_id` int(11) DEFAULT NULL COMMENT '课程ID',
  `class_id` int(11) DEFAULT NULL COMMENT '班级ID',
  `deadline_time` datetime DEFAULT NULL COMMENT '截止时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
