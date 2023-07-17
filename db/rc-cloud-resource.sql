/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : rc-cloud-resource

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 17/07/2023 13:17:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'AUTO_INCREMENT' COMMENT '对象存储主键',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原名',
  `file_suffix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件后缀名',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'URL地址',
  `service` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'minio' COMMENT '服务商',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '上传人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OSS对象存储表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
INSERT INTO `sys_oss` VALUES ('1', '2022/03/02/78999280cd1f4bd0aba89e4f8d1e482e.jpg', 'test-user.jpg', '.jpg', 'http://127.0.0.1:9000/test/2022/03/02/78999280cd1f4bd0aba89e4f8d1e482e.jpg', 'minio', '2022-03-02 13:06:19', NULL, '2022-03-02 13:06:19', NULL, NULL, '0');
INSERT INTO `sys_oss` VALUES ('2', '2022/03/02/2e66dcd77e0d44f6bc51aa85dff081c7.jpg', 'test-user.jpg', '.jpg', 'http://127.0.0.1:9000/test/2022/03/02/2e66dcd77e0d44f6bc51aa85dff081c7.jpg', 'minio', '2022-03-02 13:07:07', NULL, '2022-03-02 13:07:07', NULL, NULL, '0');
INSERT INTO `sys_oss` VALUES ('3', '2022/03/02/305de0f5efa74c1b9a7ad185d96d6a0a.png', '6c03813cb57e1f4be9a9bc313efbe44f_2456x2133.png', '.png', 'http://127.0.0.1:9000/test/2022/03/02/305de0f5efa74c1b9a7ad185d96d6a0a.png', 'minio', '2022-03-02 13:09:16', NULL, '2022-03-02 13:09:16', NULL, NULL, '0');
INSERT INTO `sys_oss` VALUES ('4', '2022/03/02/8ee91616afd241a786648c2583c535f0.jpg', 'test-user.jpg', '.jpg', 'http://127.0.0.1:9000/test/2022/03/02/8ee91616afd241a786648c2583c535f0.jpg', 'minio', '2022-03-02 13:11:04', NULL, '2022-03-02 13:11:04', NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'AUTO_INCREMENT' COMMENT '主建',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '配置key',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'accessKey',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '秘钥',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '桶名称',
  `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前缀',
  `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '访问站点',
  `is_https` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否https（Y=是,N=否）',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '域',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（0=正常,1=停用）',
  `ext1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对象存储配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES ('1', 'minio', 'minioadmin', 'minioadmin', 'test', '', 'http://127.0.0.1:9000', 'N', '', '0', '', NULL, 'admin', '2022-02-25 13:31:53', 'admin', '2022-02-25 13:31:53', b'0', NULL);
INSERT INTO `sys_oss_config` VALUES ('2', 'qiniu', 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXXXXX', 'ruoyi', '', 'http://XXX.XXXX.com', 'N', 'z0', '1', '', NULL, 'admin', '2022-02-25 13:31:53', 'admin', '2022-02-25 13:31:53', b'0', NULL);
INSERT INTO `sys_oss_config` VALUES ('3', 'aliyun', 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXXXXX', 'ruoyi', '', 'http://oss-cn-beijing.aliyuncs.com', 'N', '', '1', '', NULL, 'admin', '2022-02-25 13:31:53', 'admin', '2022-02-25 13:31:53', b'0', NULL);
INSERT INTO `sys_oss_config` VALUES ('4', 'qcloud', 'XXXXXXXXXXXXXXX', 'XXXXXXXXXXXXXXX', 'ruoyi-1250000000', '', 'http://cos.ap-beijing.myqcloud.com', 'N', 'ap-beijing', '1', '', NULL, 'admin', '2022-02-25 13:31:53', 'admin', '2022-02-25 13:31:53', b'0', NULL);

SET FOREIGN_KEY_CHECKS = 1;
