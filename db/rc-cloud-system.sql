/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : rc-cloud-system

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 05/07/2023 13:10:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for _temp
-- ----------------------------
DROP TABLE IF EXISTS `_temp`;
CREATE TABLE `_temp`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of _temp
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint(20) NOT NULL,
  `SCHED_TIME` bigint(20) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(20) NOT NULL,
  `CHECKIN_INTERVAL` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerName', 'PC-20211219OMLM1688521637688', 1688533809230, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint(20) NOT NULL,
  `REPEAT_INTERVAL` bigint(20) NOT NULL,
  `TIMES_TRIGGERED` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(20) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(20) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint(20) NOT NULL,
  `END_TIME` bigint(20) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(6) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` bigint(20) NULL DEFAULT NULL COMMENT '负责人ID',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, '柔川信息', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-06-12 17:03:49', b'0', 1);
INSERT INTO `sys_dept` VALUES (101, '黄岩总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', NULL, '2023-06-27 13:32:23', b'0', 1);
INSERT INTO `sys_dept` VALUES (102, '杭州分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2023-06-12 17:04:07', b'0', 1);
INSERT INTO `sys_dept` VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', NULL, '2023-06-27 13:32:18', b'0', 1);
INSERT INTO `sys_dept` VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', b'0', 1);
INSERT INTO `sys_dept` VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-06-27 13:32:56', b'0', 1);
INSERT INTO `sys_dept` VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', b'0', 1);
INSERT INTO `sys_dept` VALUES (107, '运维部门', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:33', b'0', 1);
INSERT INTO `sys_dept` VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', b'0', 1);
INSERT INTO `sys_dept` VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', b'0', 1);
INSERT INTO `sys_dept` VALUES (112, '测试部门001', 0, 0, NULL, NULL, NULL, 0, 'admin', '2023-06-30 17:14:29', 'admin', '2023-07-04 09:37:50', b'0', 1);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1244 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1236, 3, '未知', '0', 'user_sex', 0, 'warning', '', '未知', NULL, '2023-06-28 14:01:14', 'admin', '2023-07-05 10:18:41', b'0');
INSERT INTO `sys_dict_data` VALUES (1237, 1, '男', '1', 'user_sex', 0, 'primary', '', '', NULL, '2023-06-28 14:01:34', NULL, '2023-06-28 15:16:44', b'0');
INSERT INTO `sys_dict_data` VALUES (1238, 2, '女', '2', 'user_sex', 0, 'danger', '', '女', NULL, '2023-06-28 14:01:53', NULL, '2023-06-28 15:16:53', b'0');
INSERT INTO `sys_dict_data` VALUES (1239, 0, '正常', '0', 'common_status', 0, 'success', '', '', NULL, '2023-06-28 15:19:30', NULL, '2023-06-28 15:19:30', b'0');
INSERT INTO `sys_dict_data` VALUES (1240, 1, '停用', '1', 'common_status', 0, 'danger', '', '', NULL, '2023-06-28 15:19:43', NULL, '2023-06-28 15:19:43', b'0');
INSERT INTO `sys_dict_data` VALUES (1241, 1, '目录', '1', 'menu_type', 0, 'primary', '', '', 'admin', '2023-07-05 10:21:23', 'admin', '2023-07-05 10:22:33', b'0');
INSERT INTO `sys_dict_data` VALUES (1242, 2, '菜单', '2', 'menu_type', 0, 'danger', '', '', 'admin', '2023-07-05 10:22:24', 'admin', '2023-07-05 10:26:19', b'0');
INSERT INTO `sys_dict_data` VALUES (1243, 3, '按钮', '3', 'menu_type', 0, 'warning', '', '', 'admin', '2023-07-05 10:23:02', 'admin', '2023-07-05 10:26:09', b'0');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 173 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (170, '用户性别', 'user_sex', 0, '用户管理', NULL, '2023-06-28 14:00:25', NULL, '2023-07-04 09:51:16', b'0');
INSERT INTO `sys_dict_type` VALUES (171, '通用状态', 'common_status', 0, '通用状态', NULL, '2023-06-28 15:18:51', NULL, '2023-06-28 15:18:51', b'0');
INSERT INTO `sys_dict_type` VALUES (172, '菜单类型', 'menu_type', 0, '菜单类型', 'admin', '2023-07-05 10:20:33', 'admin', '2023-07-05 10:20:45', b'0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint(4) NOT NULL COMMENT '菜单类型',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
  `open_style` tinyint(4) NULL DEFAULT 0 COMMENT '打开方式   0：内部   1：外部',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2217 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2162, '产品', '', 1, 0, 0, NULL, '', 'product', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:29:01', NULL, '2023-06-27 14:06:53', b'0', 1);
INSERT INTO `sys_menu` VALUES (2163, '品牌', '', 1, 1, 0, NULL, NULL, 'brand', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:36:38', NULL, '2023-06-27 14:06:54', b'0', 1);
INSERT INTO `sys_menu` VALUES (2164, '经销商', '', 1, 2, 0, NULL, NULL, 'dealer', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:37:26', NULL, '2023-06-27 14:06:55', b'0', 1);
INSERT INTO `sys_menu` VALUES (2165, '经营', '', 1, 3, 0, NULL, NULL, 'operate', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:38:05', NULL, '2023-06-27 14:06:55', b'0', 1);
INSERT INTO `sys_menu` VALUES (2166, '数据', '', 1, 4, 0, NULL, NULL, 'data', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:38:32', NULL, '2023-06-27 14:06:56', b'0', 1);
INSERT INTO `sys_menu` VALUES (2167, '内容', '', 1, 5, 0, NULL, NULL, 'content', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:38:57', NULL, '2023-06-27 14:06:56', b'0', 1);
INSERT INTO `sys_menu` VALUES (2168, '系统', '', 1, 6, 0, NULL, NULL, NULL, NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:39:12', NULL, '2023-06-27 14:06:57', b'0', 1);
INSERT INTO `sys_menu` VALUES (2169, '权限管理', '', 2, 0, 2168, NULL, 'icon-safetycertificate', '', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:42:10', NULL, '2023-06-27 14:06:58', b'0', 1);
INSERT INTO `sys_menu` VALUES (2170, '系统设置', '', 2, 1, 2168, NULL, 'icon-setting', NULL, NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:43:22', NULL, '2023-06-27 14:06:59', b'0', 1);
INSERT INTO `sys_menu` VALUES (2171, '日志管理', '', 2, 2, 2168, NULL, 'icon-filedone', NULL, NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:44:25', NULL, '2023-06-27 14:48:42', b'0', 1);
INSERT INTO `sys_menu` VALUES (2172, '在线开发', '', 2, 3, 2168, NULL, 'icon-cloud', NULL, NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:45:17', NULL, '2023-06-27 14:07:00', b'0', 1);
INSERT INTO `sys_menu` VALUES (2173, '用户管理', '', 2, 0, 2169, NULL, 'icon-user', 'sys/user/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 16:58:30', NULL, '2023-06-27 14:07:00', b'0', 1);
INSERT INTO `sys_menu` VALUES (2174, '部门管理', '', 2, 1, 2169, NULL, 'icon-cluster', 'sys/dept/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:07:26', NULL, '2023-06-30 17:26:44', b'0', 1);
INSERT INTO `sys_menu` VALUES (2175, '岗位管理', '', 2, 2, 2169, NULL, 'icon-solution', 'sys/post/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:08:11', NULL, '2023-06-27 14:07:02', b'0', 1);
INSERT INTO `sys_menu` VALUES (2176, '角色管理', '', 2, 3, 2169, NULL, 'icon-team', 'sys/role/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:09:04', NULL, '2023-06-27 14:07:03', b'0', 1);
INSERT INTO `sys_menu` VALUES (2177, '查询', 'sys:user:query', 3, 0, 2173, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:13:58', NULL, '2023-06-28 10:11:49', b'0', 1);
INSERT INTO `sys_menu` VALUES (2178, '新增', 'sys:user:create', 3, 1, 2173, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:15:34', NULL, '2023-06-27 17:06:28', b'0', 1);
INSERT INTO `sys_menu` VALUES (2179, '修改', 'sys:user:update', 3, 2, 2173, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:16:07', NULL, '2023-06-27 17:06:53', b'0', 1);
INSERT INTO `sys_menu` VALUES (2180, '删除', 'sys:user:delete', 3, 3, 2173, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-26 17:16:23', NULL, '2023-06-27 16:52:14', b'0', 1);
INSERT INTO `sys_menu` VALUES (2181, '菜单管理', '', 2, 0, 2170, NULL, 'icon-menu', 'sys/menu/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 13:49:20', NULL, '2023-06-27 14:07:06', b'0', 1);
INSERT INTO `sys_menu` VALUES (2182, '数据字典', '', 2, 1, 2170, NULL, 'icon-insertrowabove', 'sys/dict/type', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 13:50:05', NULL, '2023-06-27 14:07:07', b'0', 1);
INSERT INTO `sys_menu` VALUES (2183, '附件管理', '', 2, 3, 2170, NULL, 'icon-folder-fill', 'sys/attachment/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 13:50:43', NULL, '2023-06-27 14:07:07', b'0', 1);
INSERT INTO `sys_menu` VALUES (2184, '参数管理', '', 2, 2, 2170, NULL, 'icon-filedone', 'sys/params/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 13:51:41', NULL, '2023-06-27 14:07:08', b'0', 1);
INSERT INTO `sys_menu` VALUES (2185, '接口文档', '', 2, 10, 2170, NULL, 'icon-file-text-fill', '{{apiUrl}}/doc.html', NULL, 0, b'1', b'1', b'1', 1, NULL, '2023-06-27 13:52:12', NULL, '2023-06-27 14:07:11', b'0', 1);
INSERT INTO `sys_menu` VALUES (2186, '日志列表', '', 2, 1, 2171, NULL, '', '', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 14:48:00', NULL, '2023-06-27 14:50:35', b'1', 1);
INSERT INTO `sys_menu` VALUES (2187, '日志列表', '', 0, 1, 2171, NULL, 'icon-rotate-left', 'product', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 16:15:41', NULL, '2023-06-27 16:15:52', b'1', 1);
INSERT INTO `sys_menu` VALUES (2188, '重置密码', 'sys:user:update-password', 3, 4, 2173, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 16:51:37', NULL, '2023-06-27 16:51:37', b'0', 1);
INSERT INTO `sys_menu` VALUES (2189, '查询', 'sys:dept:query', 3, 0, 2174, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 17:07:48', NULL, '2023-06-27 17:07:48', b'0', 1);
INSERT INTO `sys_menu` VALUES (2190, '新增', 'sys:dept:create', 3, 1, 2174, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 17:08:49', NULL, '2023-06-27 17:08:49', b'0', 1);
INSERT INTO `sys_menu` VALUES (2191, '修改', 'sys:dept:update', 3, 2, 2174, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 17:09:44', NULL, '2023-06-27 17:09:44', b'0', 1);
INSERT INTO `sys_menu` VALUES (2192, '删除', 'sys:dept:delete', 3, 3, 2174, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-27 17:14:50', NULL, '2023-06-27 17:14:50', b'0', 1);
INSERT INTO `sys_menu` VALUES (2193, '查询', 'sys:post:query', 3, 0, 2175, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 08:58:37', NULL, '2023-06-28 10:49:03', b'0', 1);
INSERT INTO `sys_menu` VALUES (2194, '新增', 'sys:post:create', 3, 1, 2175, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 09:00:03', NULL, '2023-06-28 09:00:03', b'0', 1);
INSERT INTO `sys_menu` VALUES (2195, '修改', 'sys:post:update', 3, 2, 2175, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 09:00:39', NULL, '2023-06-28 09:00:39', b'0', 1);
INSERT INTO `sys_menu` VALUES (2196, '删除', 'sys:post:delete', 3, 3, 2175, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 09:01:19', NULL, '2023-06-28 09:01:19', b'0', 1);
INSERT INTO `sys_menu` VALUES (2197, '查询', 'sys:role:query', 3, 0, 2176, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:50:35', NULL, '2023-06-28 10:50:35', b'0', 1);
INSERT INTO `sys_menu` VALUES (2198, '新增', 'sys:role:create', 3, 1, 2176, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:51:12', NULL, '2023-06-28 10:51:12', b'0', 1);
INSERT INTO `sys_menu` VALUES (2199, '修改', 'sys:role:update', 3, 2, 2176, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:51:54', NULL, '2023-06-28 10:51:54', b'0', 1);
INSERT INTO `sys_menu` VALUES (2200, '删除', 'sys:role:delete', 3, 3, 2176, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:52:23', NULL, '2023-06-28 10:52:23', b'0', 1);
INSERT INTO `sys_menu` VALUES (2201, '查询', 'sys:menu:query', 3, 0, 2181, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:54:13', NULL, '2023-06-28 10:54:13', b'0', 1);
INSERT INTO `sys_menu` VALUES (2202, '新增', 'sys:menu:create', 3, 1, 2181, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:54:43', NULL, '2023-06-28 10:54:43', b'0', 1);
INSERT INTO `sys_menu` VALUES (2203, '修改', 'sys:menu:update', 3, 2, 2181, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:55:16', NULL, '2023-06-28 10:55:16', b'0', 1);
INSERT INTO `sys_menu` VALUES (2204, '删除', 'sys:menu:delete', 3, 3, 2181, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:55:41', NULL, '2023-06-28 10:55:41', b'0', 1);
INSERT INTO `sys_menu` VALUES (2205, '查询', 'sys:dict:query', 3, 0, 2182, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:58:18', NULL, '2023-06-28 10:58:18', b'0', 1);
INSERT INTO `sys_menu` VALUES (2206, '新增', 'sys:dict:create', 3, 0, 2182, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:58:51', NULL, '2023-06-28 10:58:51', b'0', 1);
INSERT INTO `sys_menu` VALUES (2207, '修改', 'sys:dict:update', 3, 2, 2182, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:59:22', NULL, '2023-06-28 11:00:17', b'0', 1);
INSERT INTO `sys_menu` VALUES (2208, '删除', 'sys:dict:delete', 3, 3, 2182, '', '', '', '', 0, b'1', b'1', b'1', 0, NULL, '2023-06-28 10:59:46', NULL, '2023-06-28 10:59:46', b'0', 1);
INSERT INTO `sys_menu` VALUES (2209, '经销商管理', '', 2, 0, 2164, NULL, '', '', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:20:31', NULL, '2023-06-29 16:20:31', b'0', 1);
INSERT INTO `sys_menu` VALUES (2210, '参数配置', '', 2, 1, 2164, NULL, '', '', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:20:48', NULL, '2023-06-29 16:20:48', b'0', 1);
INSERT INTO `sys_menu` VALUES (2211, '未对接经销商', '', 2, 0, 2209, NULL, '', 'distributor/admin/un-abutmented/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:21:28', NULL, '2023-06-30 14:01:25', b'0', 1);
INSERT INTO `sys_menu` VALUES (2212, '经销商回收站', '', 2, 1, 2209, NULL, '', 'distributor/admin/recycle/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:21:48', NULL, '2023-06-30 14:01:30', b'0', 1);
INSERT INTO `sys_menu` VALUES (2213, '客户渠道', '', 2, 0, 2210, NULL, '', 'distributor/config/customer-channel/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:22:10', NULL, '2023-06-30 14:01:34', b'0', 1);
INSERT INTO `sys_menu` VALUES (2214, '获客方式', '', 2, 1, 2210, NULL, '', 'distributor/config/customer-acquisition-method/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:22:29', NULL, '2023-06-30 14:01:39', b'0', 1);
INSERT INTO `sys_menu` VALUES (2215, '客户等级', '', 2, 2, 2210, NULL, '', 'distributor/config/customer-level/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:22:45', NULL, '2023-06-30 14:01:44', b'0', 1);
INSERT INTO `sys_menu` VALUES (2216, '信誉等级', '', 2, 3, 2210, NULL, '', 'distributor/config/reputation-level/index', NULL, 0, b'1', b'1', b'1', 0, NULL, '2023-06-29 16:22:58', NULL, '2023-06-30 14:01:48', b'0', 1);

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`  (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源列表',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '域',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '认证类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色列表',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT 'token 有效期',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '刷新令牌有效期',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '令牌扩展字段JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否自动放行',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '终端信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_oauth_client_details` VALUES ('client', NULL, 'client', 'server', 'client_credentials', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_oauth_client_details` VALUES ('gen', NULL, 'gen', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_oauth_client_details` VALUES ('rc', NULL, 'rc', 'server', 'password,app,refresh_token,authorization_code,client_credentials', 'https://rc.com', NULL, NULL, NULL, NULL, 'true', NULL, '2023-06-24 16:32:05', NULL, NULL, b'0');
INSERT INTO `sys_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, b'0');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2023-02-11 15:19:04', b'0', 1);
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', b'0', 1);
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工7770', 4, 0, '111', 'admin', '2021-01-05 17:03:48', 'admin', '2023-06-30 16:53:53', b'0', 1);
INSERT INTO `sys_post` VALUES (5, 'test001', '测试001', 0, 0, NULL, 'admin', '2023-06-30 17:06:09', 'admin', '2023-06-30 17:10:43', b'1', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `data_scope` tinyint(4) NOT NULL COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据范围(指定部门数组)',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `type` tinyint(4) NOT NULL COMMENT '角色类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0', 1);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0', 1);
INSERT INTO `sys_role` VALUES (101, '测试账号', 'test', 0, 1, '[]', 0, 2, '132', '', '2021-01-06 13:49:35', NULL, '2023-06-29 14:21:56', b'0', 1);
INSERT INTO `sys_role` VALUES (140, '测试账号2', 'test2', 0, 1, '', 0, 2, '', NULL, '2023-06-29 08:08:23', NULL, '2023-06-29 08:15:47', b'1', 1);
INSERT INTO `sys_role` VALUES (141, '测试账号1333', 'test15', 3, 1, NULL, 0, 2, '', NULL, '2023-06-29 08:16:49', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role` VALUES (142, '产品经理', 'product', 0, 1, NULL, 0, 2, '', NULL, '2023-06-29 13:30:47', NULL, '2023-06-29 14:21:45', b'1', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2995 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2901, 1, 2162, '', '2023-06-26 16:51:04', '', '2023-06-26 16:51:24', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2902, 1, 2163, '', '2023-06-26 16:51:41', '', '2023-06-26 16:51:45', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2903, 1, 2164, '', '2023-06-26 16:51:54', '', '2023-06-26 16:52:14', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2904, 1, 2165, '', '2023-06-26 16:52:13', '', '2023-06-26 16:52:13', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2905, 1, 2166, '', '2023-06-26 16:52:25', '', '2023-06-26 16:52:25', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2906, 1, 2167, '', '2023-06-26 16:52:43', '', '2023-06-26 16:52:43', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2907, 1, 2168, '', '2023-06-26 16:52:51', '', '2023-06-26 16:52:51', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2908, 1, 2169, '', '2023-06-26 16:52:59', '', '2023-06-26 16:52:59', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2909, 1, 2170, '', '2023-06-26 16:53:07', '', '2023-06-26 16:53:07', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2910, 1, 2171, '', '2023-06-26 16:53:14', '', '2023-06-26 16:53:14', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2911, 1, 2172, '', '2023-06-26 16:53:23', '', '2023-06-26 16:53:23', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2912, 1, 2173, '', '2023-06-26 16:58:50', '', '2023-06-26 16:58:50', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2913, 1, 2174, '', '2023-06-26 17:09:23', '', '2023-06-26 17:09:23', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2914, 1, 2175, '', '2023-06-26 17:09:33', '', '2023-06-26 17:09:33', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2915, 1, 2176, '', '2023-06-26 17:09:43', '', '2023-06-26 17:09:43', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2916, 1, 2177, '', '2023-06-26 17:14:24', '', '2023-06-26 17:14:24', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2917, 1, 2178, '', '2023-06-26 17:16:51', '', '2023-06-26 17:16:51', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2918, 1, 2179, '', '2023-06-26 17:16:58', '', '2023-06-26 17:16:58', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2919, 1, 2180, '', '2023-06-26 17:17:06', '', '2023-06-26 17:17:06', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2920, 1, 2181, '', '2023-06-27 13:57:46', '', '2023-06-27 13:57:46', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2921, 1, 2182, '', '2023-06-27 13:57:54', '', '2023-06-27 13:57:54', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2922, 1, 2183, '', '2023-06-27 13:58:02', '', '2023-06-27 13:58:02', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2923, 1, 2184, '', '2023-06-27 13:58:09', '', '2023-06-27 13:58:09', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2924, 1, 2185, '', '2023-06-27 13:58:16', '', '2023-06-27 13:58:16', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2925, 1, 2186, '', '2023-06-27 14:49:17', '', '2023-06-27 14:50:35', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2926, 1, 2187, '', '2023-06-28 09:58:28', '', '2023-06-28 09:58:28', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2927, 1, 2188, '', '2023-06-28 09:58:36', '', '2023-06-28 09:58:36', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2928, 1, 2189, '', '2023-06-28 09:58:43', '', '2023-06-28 09:58:43', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2929, 1, 2190, '', '2023-06-28 09:58:50', '', '2023-06-28 09:58:50', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2930, 1, 2191, '', '2023-06-28 09:58:57', '', '2023-06-28 09:58:57', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2931, 1, 2192, '', '2023-06-28 09:59:03', '', '2023-06-28 09:59:03', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2932, 1, 2193, '', '2023-06-28 09:59:07', '', '2023-06-28 09:59:09', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2933, 1, 2194, '', '2023-06-28 09:59:16', '', '2023-06-28 09:59:16', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2934, 1, 2195, '', '2023-06-28 09:59:24', '', '2023-06-28 09:59:24', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2935, 1, 2196, '', '2023-06-28 09:59:32', '', '2023-06-28 09:59:32', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2936, 1, 2197, '', '2023-06-28 11:07:12', '', '2023-06-28 11:08:05', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2937, 1, 2198, '', '2023-06-28 11:07:18', '', '2023-06-28 11:08:08', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2938, 1, 2199, '', '2023-06-28 11:07:31', '', '2023-06-28 11:08:12', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2939, 1, 2200, '', '2023-06-28 11:08:22', '', '2023-06-28 11:08:22', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2940, 1, 2201, '', '2023-06-28 11:08:31', '', '2023-06-28 11:08:31', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2941, 1, 2202, '', '2023-06-28 11:08:38', '', '2023-06-28 11:08:38', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2942, 1, 2203, '', '2023-06-28 11:08:44', '', '2023-06-28 11:08:44', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2943, 1, 2204, '', '2023-06-28 11:08:52', '', '2023-06-28 11:08:52', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2944, 1, 2205, '', '2023-06-28 11:08:59', '', '2023-06-28 11:08:59', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2945, 1, 2206, '', '2023-06-28 11:09:07', '', '2023-06-28 11:09:07', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2946, 1, 2207, '', '2023-06-28 11:09:13', '', '2023-06-28 11:09:13', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2947, 1, 2208, '', '2023-06-28 11:09:19', '', '2023-06-28 11:09:19', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2948, 141, 2162, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2949, 141, 2163, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2950, 141, 2164, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2951, 141, 2165, NULL, '2023-06-29 13:29:06', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2952, 141, 2167, NULL, '2023-06-29 13:29:19', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2953, 141, 2176, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2954, 141, 2177, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2955, 141, 2178, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2956, 141, 2179, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2957, 141, 2180, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2958, 141, 2181, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2959, 141, 2182, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2960, 141, 2183, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2961, 141, 2184, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2962, 141, 2185, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2963, 141, 2188, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2964, 141, 2189, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2965, 141, 2190, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2966, 141, 2191, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2967, 141, 2192, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2968, 141, 2193, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2969, 141, 2194, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2970, 141, 2195, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2971, 141, 2196, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2972, 141, 2197, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2973, 141, 2198, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2974, 141, 2199, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2975, 141, 2200, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2976, 141, 2201, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2977, 141, 2202, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2978, 141, 2203, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2979, 141, 2204, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2980, 141, 2205, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2981, 141, 2206, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2982, 141, 2207, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2983, 141, 2208, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2984, 141, 2168, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2985, 141, 2169, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2986, 141, 2170, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2987, 141, 2171, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2988, 141, 2172, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2989, 141, 2173, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2990, 141, 2174, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2991, 141, 2175, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2992, 142, 2162, NULL, '2023-06-29 13:30:47', NULL, '2023-06-29 14:21:45', b'1', 1);
INSERT INTO `sys_role_menu` VALUES (2993, 101, 2162, NULL, '2023-06-29 14:21:56', NULL, '2023-06-29 14:21:56', b'0', 1);
INSERT INTO `sys_role_menu` VALUES (2994, 101, 2163, NULL, '2023-06-29 14:21:56', NULL, '2023-06-29 14:21:56', b'0', 1);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
  `contact_user_id` bigint(20) NULL DEFAULT NULL COMMENT '联系人的用户编号',
  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
  `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
  `package_id` bigint(20) NOT NULL COMMENT '租户套餐编号',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `account_count` int(11) NOT NULL COMMENT '账号数量',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '柔川信息', 1, '张三', '13777777777', 0, 'https://www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2023-06-13 14:23:35', b'0');
INSERT INTO `sys_tenant` VALUES (121, '小租户', 110, '小王2', '15601691300', 0, 'http://www.iocoder.cn', 111, '2024-03-11 00:00:00', 20, '1', '2022-02-22 00:56:14', '1', '2022-05-17 10:03:59', b'0');
INSERT INTO `sys_tenant` VALUES (122, '测试租户', 113, '李四', '15601691300', 0, 'https://www.iocoder.cn', 111, '2022-04-30 00:00:00', 50, '1', '2022-03-07 21:37:58', '1', '2023-06-12 17:05:10', b'0');
INSERT INTO `sys_tenant` VALUES (150, 'zuhu1', 126, 'huang', NULL, 0, 'www.baidu.com', 111, '2024-05-31 00:00:00', 1000, '1', '2023-05-31 13:52:15', '1', '2023-05-31 13:52:15', b'0');

-- ----------------------------
-- Table structure for sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `menu_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant_package
-- ----------------------------
INSERT INTO `sys_tenant_package` VALUES (111, '普通套餐', 0, '小功能', '[1,1036,1037,1038,1039,100,101,1063,103,1064,1001,1065,1002,1003,107,1004,1005,1006,1007,1008,1009,1010,1011,1012,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2022-09-21 22:48:12', b'0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `post_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位ID数组',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号',
  `sex` tinyint(4) NULL DEFAULT 0 COMMENT '用户性别（0未知，1男，2女）',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`, `update_time`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$3EqFTg5lyyBkNTXqTpe5xeGwU6QSgBxzxSBc16fZARDZ.NYIk7w0W', 'rc', '管理员', 100, '[1]', 'aoteman@126.com', '15612345678', 1, 'https://cdn.maku.net/images/avatar.png', 0, '127.0.0.1', '2023-05-31 11:26:15', 'admin', '2021-01-05 17:03:47', NULL, '2023-06-28 16:43:56', b'0', 1);
INSERT INTO `sys_user` VALUES (128, 'hqftest123', '{bcrypt}$2a$10$6tM5ihqRHZloc96TTZ0wGegAoPY7sR1F5KHhAwOnpfUn1q0dWidNW', 'hqf', '管理员', 105, '[4]', '', '13777777777', 1, 'https://cdn.maku.net/images/avatar.png', 0, '', NULL, NULL, '2023-06-28 17:01:24', 'admin', '2023-07-03 16:06:06', b'0', 1);
INSERT INTO `sys_user` VALUES (129, 'hqf12345', '{bcrypt}$2a$10$UxzKHyZuBu/.J77RStGYou2s2wdAH4doOFHPK5AE2478QbMxkZe16', 'hqf', '管理员', 105, '[4]', '', '13777766664', 1, 'https://cdn.maku.net/images/avatar.png', 0, '', NULL, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:24', b'1', 1);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '岗位ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `sys_user_post` VALUES (118, 1, 2, NULL, '2023-06-28 16:06:03', NULL, '2023-06-28 16:06:34', b'1', 1);
INSERT INTO `sys_user_post` VALUES (119, 128, 4, NULL, '2023-06-28 17:01:24', NULL, '2023-06-28 17:01:24', b'0', 1);
INSERT INTO `sys_user_post` VALUES (120, 129, 4, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:14', b'0', 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除（0正常，1删除）',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', b'0', 1);
INSERT INTO `sys_user_role` VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2023-06-28 16:40:08', b'1', 1);
INSERT INTO `sys_user_role` VALUES (32, 128, 101, NULL, '2023-06-28 17:05:39', NULL, '2023-06-28 17:05:39', b'0', 1);
INSERT INTO `sys_user_role` VALUES (33, 129, 101, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:14', b'0', 1);
INSERT INTO `sys_user_role` VALUES (34, 128, 142, NULL, '2023-06-29 14:19:12', NULL, '2023-06-29 14:21:28', b'1', 1);
INSERT INTO `sys_user_role` VALUES (35, 128, 142, NULL, '2023-06-29 14:21:34', NULL, '2023-06-29 14:21:40', b'1', 1);
INSERT INTO `sys_user_role` VALUES (36, 1, 142, NULL, '2023-06-29 14:21:34', NULL, '2023-06-29 14:21:40', b'1', 1);

SET FOREIGN_KEY_CHECKS = 1;
