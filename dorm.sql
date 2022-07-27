/*
Navicat MySQL Data Transfer

Source Server         : dorm
Source Server Version : 50738
Source Database       : dorm

Target Server Type    : MYSQL
Target Server Version : 50738
File Encoding         : 65001

Date: 2022-07-27 16:43:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dorm`
-- ----------------------------
DROP TABLE IF EXISTS `dorm`;
CREATE TABLE `dorm` (
  `id` varchar(20) NOT NULL COMMENT '宿舍标识',
  `dorm_number` varchar(20) NOT NULL COMMENT '楼房号，如:西三404',
  `water_bill` decimal(10,3) NOT NULL COMMENT '水费',
  `electric_bill` decimal(10,3) NOT NULL COMMENT '电费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dorm
-- ----------------------------
INSERT INTO `dorm` VALUES ('c403', '西403', '332.000', '22.000');
INSERT INTO `dorm` VALUES ('c404', '西404', '2.000', '2.000');
INSERT INTO `dorm` VALUES ('c406', '西406', '10.000', '4.000');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(20) NOT NULL COMMENT '学生标识',
  `student_name` varchar(255) NOT NULL COMMENT '学生名',
  `dorm_id` varchar(20) NOT NULL COMMENT '宿舍标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (' 6', '萧圣森', '-1');
INSERT INTO `student` VALUES ('1', '鸿哥', '西三-408');
INSERT INTO `student` VALUES ('1 or 1=1', '我负责注入', '南池子甲字81号');
INSERT INTO `student` VALUES ('1551797663296016386', '张高丽', '西三-408');
INSERT INTO `student` VALUES ('1551837162851319810', 'xss', '西三-406');
INSERT INTO `student` VALUES ('1551865406942117890', 'Xi', '中南海');
INSERT INTO `student` VALUES ('1551913894371422210', '于林红', '西三-408');
INSERT INTO `student` VALUES ('1551913907323432962', '无郡结', '西三-422');
INSERT INTO `student` VALUES ('1551913907327627266', '也规划', '西三-406');
INSERT INTO `student` VALUES ('1551913907331821569', '消声槮', '西三-406');
INSERT INTO `student` VALUES ('1551913907331821570', '蛇苳羽', '西三-408');
INSERT INTO `student` VALUES ('1551913907336015874', '吾亭颉', '西三-408');
INSERT INTO `student` VALUES ('1551913907340210177', '荔氦焘', '西三-406');
INSERT INTO `student` VALUES ('1551955434469834754', 'aaa', 'aa');
INSERT INTO `student` VALUES ('1552130900404207617', 'Li', '中南海');
INSERT INTO `student` VALUES ('1552181833314734082', '木子十口儿弓虽', '乾清宫');
INSERT INTO `student` VALUES ('2', '俊杰哥', '西三-422');
INSERT INTO `student` VALUES ('3', '华子哥', '西三-406');
