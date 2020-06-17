/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50096
 Source Host           : localhost:3306
 Source Schema         : im-esb-api

 Target Server Type    : MySQL
 Target Server Version : 50096
 File Encoding         : 65001

 Date: 17/06/2020 19:03:17
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `api_id` int(8) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_context` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_transcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_status` int(11) NULL DEFAULT NULL,
  `api_menu` int(5) NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`api_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES (1, '查询文件列表', '模拟查询文件列表接口', 'http://80.8.102.179:10002/file/list', '{\n	\"currentPage\":1,\n	\"pageSize\":10\n}', '2001001', 1, 2);
INSERT INTO `api` VALUES (2, '查询文件详情', '模拟查询文件详情接口', 'http://80.8.102.179:10002/file/object', '{\n	\"id\":\"1000000180417913\"\n}', '2001002', 1, 2);
INSERT INTO `api` VALUES (3, '上传文件', '模拟上传文件接口', 'http://80.8.102.179:10002/file/upload', NULL, '2001003', 1, 2);
INSERT INTO `api` VALUES (4, '用户注册接口', '模拟用户注册', 'http://localhost:10001/user/register', '{     \"phone\":\"17621663876\",     \"password\":\"shiqilong\",     \"smscode\":\"123456\",     \"ip\":\"127.0.01\" }', '1001001', 1, 1);
INSERT INTO `api` VALUES (5, '用户登录接口', '模拟用户登录', 'http://localhost:10001/user/login', '{     \"phone\":\"17621663876\",     \"password\":\"shiqilong\",     \"loginType\":\"1  1：手机号验证码  2：账号密码\",     \"smscode\":\"123456\" }', '1001002', 1, 1);
INSERT INTO `api` VALUES (6, '检查手机号是否存在', '模拟检查手机号是否存在接口', 'http://localhost:10001/user/check_phone', '{     \"phone\":\"17621663876\" }', '1001003', 1, 1);
INSERT INTO `api` VALUES (7, '用户修改密码', '模拟用户修改密码接口', 'http://localhost:10001/user/update_password', '{     \"phone\":\"17621663876\",     \"password\":\"shiqilong\" }', '1001004', 1, 1);
INSERT INTO `api` VALUES (8, '后台-查询所有用户接口', '模拟查询所有用户接口', 'http://localhost:10001/user/query_user', '{ 	\"currentPage\":1, 	\"pageSize\":10 }', '1001005', 1, 1);
INSERT INTO `api` VALUES (9, '后台-根据用户ID查询用户信息', '模拟管理员根据用户ID查询用户信息接口', 'http://localhost:10001/user/findUserById', '{     \"userId\":\"1\" }', '1001006', 1, 1);
INSERT INTO `api` VALUES (10, '后台-修改用户状态接口', '模拟管理员修改用户状态接口', 'http://localhost:10001/user/update_status', '{     \"userId\":\"1\",     \"status\":\"1\" }', '1001007', 1, 1);

-- ----------------------------
-- Table structure for api_login
-- ----------------------------
DROP TABLE IF EXISTS `api_login`;
CREATE TABLE `api_login`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `createtime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NULL DEFAULT NULL,
  `updatetime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of api_login
-- ----------------------------
INSERT INTO `api_login` VALUES (1, 'admin', 'admin', 1, '1', 1, '2020-06-16 20:47:18:018');
INSERT INTO `api_login` VALUES (2, 'shiqilong', NULL, 1, '2020-06-16 21:15:13:013', 1, '2020-06-16 21:15:13:013');
INSERT INTO `api_login` VALUES (3, 'shiqilong', 'shiqilong', 1, '2020-06-16 21:16:59:059', 1, '2020-06-16 21:16:59:059');

SET FOREIGN_KEY_CHECKS = 1;
