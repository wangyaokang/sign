/*
Navicat MySQL Data Transfer

Source Server         : 签到
Source Server Version : 50624
Source Host           : 127.0.0.1:3306
Source Database       : tbSign

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-09-14 11:03:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '主题',
  `image_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '主题图片',
  `upload_file_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '文件url',
  `content` text CHARACTER SET utf8 COMMENT '内容',
  `creator_id` int(11) DEFAULT NULL COMMENT '发布人',
  `status` int(1) DEFAULT NULL COMMENT '是否是热点',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
