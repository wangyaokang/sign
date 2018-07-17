/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 80011
Source Host           : 127.0.0.1:3306
Source Database       : sign

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-18 00:18:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_classes
-- ----------------------------
DROP TABLE IF EXISTS `tb_classes`;
CREATE TABLE `tb_classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '班级名称',
  `admin_id` int(11) DEFAULT NULL COMMENT '管理者ID',
  `status` int(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
