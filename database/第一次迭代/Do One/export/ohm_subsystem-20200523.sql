/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : ohm_subsystem

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 23/05/2020 00:41:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ohms_college
-- ----------------------------
DROP TABLE IF EXISTS `ohms_college`;
CREATE TABLE `ohms_college`  (
  `id` tinyint(0) NOT NULL AUTO_INCREMENT COMMENT '学院id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '学院描述',
  `datetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '导入时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学院表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_college
-- ----------------------------
INSERT INTO `ohms_college` VALUES (53, '数据科学和信息工程学院', '数据科学和信息工程学院', '2020-05-22 23:00:48');
INSERT INTO `ohms_college` VALUES (54, '机械电子工程学院', '机械电子工程学院', '2020-05-22 23:00:48');
INSERT INTO `ohms_college` VALUES (55, '文学院', '文学院', '2020-05-22 23:00:48');
INSERT INTO `ohms_college` VALUES (56, '商学院', 'jpa save保存成功与否', '2020-05-22 23:00:48');
INSERT INTO `ohms_college` VALUES (57, '传媒学院', '传媒学院', '2020-05-22 23:00:48');
INSERT INTO `ohms_college` VALUES (58, '体育学院', NULL, '2020-05-22 23:00:48');

-- ----------------------------
-- Table structure for ohms_course_group
-- ----------------------------
DROP TABLE IF EXISTS `ohms_course_group`;
CREATE TABLE `ohms_course_group`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '课群id',
  `teacher_id` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教职工号',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课群名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课群介绍',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '课群状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `ohms_course_group_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `ohms_teacher` (`teacher_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课群表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_course_group
-- ----------------------------

-- ----------------------------
-- Table structure for ohms_login_record
-- ----------------------------
DROP TABLE IF EXISTS `ohms_login_record`;
CREATE TABLE `ohms_login_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '登录记录id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `login_ip` varchar(33) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录的ip',
  `province` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的省',
  `province_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省代码',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的城市',
  `city_code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市代码',
  `address` varchar(98) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的地址',
  `agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'user-agent',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `ohms_login_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ohms_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_login_record
-- ----------------------------
INSERT INTO `ohms_login_record` VALUES (1, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (2, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (3, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (4, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (5, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (6, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (7, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (8, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (9, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (10, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (11, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (12, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (13, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (14, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (15, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (16, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (17, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (18, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (19, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (20, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (21, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (22, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (23, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (24, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (25, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (26, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (27, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (28, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (29, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (30, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (31, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (32, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (33, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (34, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (35, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (36, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (37, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (38, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (39, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (40, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (41, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (42, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (43, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (44, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (45, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (46, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (47, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (48, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (49, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (50, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (51, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (52, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (53, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (54, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (55, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (56, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (57, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (58, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (59, 1, '127.0.0.1', '', '999999', '', '0', NULL, NULL);
INSERT INTO `ohms_login_record` VALUES (60, 7, '127.0.0.1', '', '999999', '', '0', NULL, NULL);

-- ----------------------------
-- Table structure for ohms_major
-- ----------------------------
DROP TABLE IF EXISTS `ohms_major`;
CREATE TABLE `ohms_major`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '专业id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名',
  `college_id` tinyint(0) NOT NULL COMMENT '学院id',
  `datetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '导入时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `college_id`(`college_id`) USING BTREE,
  CONSTRAINT `ohms_major_ibfk_1` FOREIGN KEY (`college_id`) REFERENCES `ohms_college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_major
-- ----------------------------

-- ----------------------------
-- Table structure for ohms_role
-- ----------------------------
DROP TABLE IF EXISTS `ohms_role`;
CREATE TABLE `ohms_role`  (
  `id` tinyint(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `datetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加角色时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_role
-- ----------------------------
INSERT INTO `ohms_role` VALUES (1, 'admin', '超级管理员', '2020-05-22 14:19:13');
INSERT INTO `ohms_role` VALUES (2, 'teachingSecretary', '教学秘书', '2020-05-22 14:19:13');
INSERT INTO `ohms_role` VALUES (3, 'teacher', '教师', '2020-05-22 14:19:13');
INSERT INTO `ohms_role` VALUES (4, 'student', '学生', '2020-05-22 14:19:13');

-- ----------------------------
-- Table structure for ohms_student
-- ----------------------------
DROP TABLE IF EXISTS `ohms_student`;
CREATE TABLE `ohms_student`  (
  `student_id` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `major_id` int(0) NOT NULL COMMENT '专业id',
  PRIMARY KEY (`student_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `major_id`(`major_id`) USING BTREE,
  CONSTRAINT `ohms_student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ohms_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ohms_student_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `ohms_major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_student
-- ----------------------------

-- ----------------------------
-- Table structure for ohms_student_course_group
-- ----------------------------
DROP TABLE IF EXISTS `ohms_student_course_group`;
CREATE TABLE `ohms_student_course_group`  (
  `student_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `course_group_id` bigint(0) NOT NULL COMMENT '课群id',
  `join_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '学生加入的时间',
  PRIMARY KEY (`student_id`, `course_group_id`) USING BTREE,
  INDEX `course_group_id`(`course_group_id`) USING BTREE,
  CONSTRAINT `ohms_student_course_group_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `ohms_student` (`student_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ohms_student_course_group_ibfk_2` FOREIGN KEY (`course_group_id`) REFERENCES `ohms_course_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生加入的课群' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_student_course_group
-- ----------------------------

-- ----------------------------
-- Table structure for ohms_teacher
-- ----------------------------
DROP TABLE IF EXISTS `ohms_teacher`;
CREATE TABLE `ohms_teacher`  (
  `teacher_id` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教职工号',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`teacher_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `ohms_teacher_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ohms_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '老师表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_teacher
-- ----------------------------
INSERT INTO `ohms_teacher` VALUES ('201742010122', 1);
INSERT INTO `ohms_teacher` VALUES ('201742010153', 7);
INSERT INTO `ohms_teacher` VALUES ('201942010125', 8);
INSERT INTO `ohms_teacher` VALUES ('201742010244', 9);
INSERT INTO `ohms_teacher` VALUES ('201742010227', 11);

-- ----------------------------
-- Table structure for ohms_user
-- ----------------------------
DROP TABLE IF EXISTS `ohms_user`;
CREATE TABLE `ohms_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `real_name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密过的密码',
  `salt` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密用到的盐',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'M' COMMENT '用户性别',
  `avatar` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  `email` varchar(78) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_user
-- ----------------------------
INSERT INTO `ohms_user` VALUES (1, 'gzmu-201742010122', '王国富', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M', NULL, NULL, NULL);
INSERT INTO `ohms_user` VALUES (7, 'gzmu-201742010153', '汪海浪', 'e19d1d0a3850bcb7ab9d559093c30e73', 'fd7e025ecd394b50909c301baf2b8a5b', 'M', NULL, NULL, NULL);
INSERT INTO `ohms_user` VALUES (8, 'gzmu-201942010125', '程桥凤', '56f67202a605a12e68b044fe6748937e', '89bd7d0afe2740ae94cf20922f13b434', 'F', NULL, NULL, NULL);
INSERT INTO `ohms_user` VALUES (9, 'gzmu-201742010244', '尚宏程', '511322bdd090ab82d617692e266af0a7', '0ecccaca76c34653a9efa716684b748f', 'M', NULL, NULL, NULL);
INSERT INTO `ohms_user` VALUES (11, 'gzmu-201742010227', '李仁材', 'beb6487ae244b6f26d6e046ab67c5d1a', '50b8905eb6184262999f18182ed29cc8', 'M', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ohms_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ohms_user_role`;
CREATE TABLE `ohms_user_role`  (
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `role_id` tinyint(0) NOT NULL COMMENT '角色id',
  `datetime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '授于角色的时间',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `ohms_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ohms_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ohms_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `ohms_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ohms_user_role
-- ----------------------------
INSERT INTO `ohms_user_role` VALUES (1, 1, '2020-05-22 14:19:20');
INSERT INTO `ohms_user_role` VALUES (1, 2, '2020-05-22 14:19:20');
INSERT INTO `ohms_user_role` VALUES (7, 2, '2020-05-23 00:38:24');
INSERT INTO `ohms_user_role` VALUES (7, 3, '2020-05-23 00:36:36');
INSERT INTO `ohms_user_role` VALUES (8, 3, '2020-05-23 00:36:36');
INSERT INTO `ohms_user_role` VALUES (9, 3, '2020-05-23 00:36:37');
INSERT INTO `ohms_user_role` VALUES (11, 3, '2020-05-23 00:36:37');

-- ----------------------------
-- View structure for ohms_view_student
-- ----------------------------
DROP VIEW IF EXISTS `ohms_view_student`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ohms_view_student` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`real_name` AS `real_name`,`u`.`password` AS `password`,`u`.`salt` AS `salt`,`u`.`sex` AS `sex`,`u`.`avatar` AS `avatar`,`u`.`email` AS `email`,`u`.`phone` AS `phone`,`s`.`student_id` AS `student_id`,`s`.`major_id` AS `major_id` from (`ohms_user` `u` join `ohms_student` `s`) where (`u`.`id` = `s`.`user_id`);

-- ----------------------------
-- View structure for ohms_view_teacher
-- ----------------------------
DROP VIEW IF EXISTS `ohms_view_teacher`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ohms_view_teacher` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`real_name` AS `real_name`,`u`.`password` AS `password`,`u`.`salt` AS `salt`,`u`.`sex` AS `sex`,`u`.`avatar` AS `avatar`,`u`.`email` AS `email`,`u`.`phone` AS `phone`,`t`.`teacher_id` AS `teacher_id` from (`ohms_user` `u` join `ohms_teacher` `t`) where (`t`.`user_id` = `u`.`id`);

SET FOREIGN_KEY_CHECKS = 1;
