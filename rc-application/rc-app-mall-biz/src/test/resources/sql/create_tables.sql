/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : zx_ceshi

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 28/06/2023 10:16:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for distributor
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor"  (
                                "id" bigint NOT NULL COMMENT 'id',
                                "company_name" varchar(255)  NULL DEFAULT '' COMMENT '企业名称',
                                "password" varchar(25)  NULL DEFAULT '' COMMENT '密码',
                                "province" varchar(255)  NULL DEFAULT '' COMMENT '省',
                                "city" varchar(255)  NULL DEFAULT '' COMMENT '市',
                                "county" varchar(255)  NULL DEFAULT '' COMMENT '区',
                                "address" varchar(255)  NULL DEFAULT '' COMMENT '详细地址',
                                "start_time" varchar(25)  NULL DEFAULT '' COMMENT '合作开始',
                                "end_time" varchar(25)  NULL DEFAULT '' COMMENT '合作结束',
                                "status" int NULL DEFAULT 1 COMMENT '对接状态1未对接2已对接',
                                "remarks" clob  NULL COMMENT '备注',
                                "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                "admin_id" bigint NULL DEFAULT NULL COMMENT '管理员id',
                                "telephone" varchar(255)  NULL DEFAULT '' COMMENT '联系电话',
                                "channel" bigint NULL DEFAULT 0 COMMENT '客户渠道id',
                                "source" bigint NULL DEFAULT 0 COMMENT '获客方式id',
                                "level" bigint NULL DEFAULT 0 COMMENT '客户等级id',
                                "reputation" bigint NULL DEFAULT 0 COMMENT '信誉等级',
                                "established_time" varchar(255)  NULL DEFAULT '' COMMENT '成立时间',
                                "deleted" int NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                "locking" int NULL DEFAULT 0 COMMENT '是否锁定',
                                "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间'
);
-- ----------------------------
-- Table structure for distributor_channel
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_channel"  (
                                "id" bigint NOT NULL COMMENT 'id',
                                "name" varchar(255)  NULL DEFAULT NULL COMMENT '渠道名称',
                                "description" varchar(255)  NULL DEFAULT NULL COMMENT '说明',
                                "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间'
);
-- -- ----------------------------
-- -- Table structure for distributor_contact
-- -- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_contact"  (
                                        "id" bigint NOT NULL,
                                        "name" varchar(255)  NULL DEFAULT '' COMMENT '联系人',
                                        "mobile" varchar(255)  NULL DEFAULT '' COMMENT '手机',
                                        "password" varchar(255)  NULL DEFAULT '' COMMENT '密码',
                                        "distributor_id" bigint NULL DEFAULT NULL,
                                        "lastread_time" varchar(255)  NULL DEFAULT NULL COMMENT '最后浏览时间',
                                        "click" int NULL DEFAULT 0 COMMENT '用户浏览商品数量',
                                        "headimage" varchar(255)  NULL DEFAULT NULL
);
-- ----------------------------
-- Table structure for distributor_detail
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_detail"  (
                                       "id" bigint NOT NULL COMMENT 'id',
                                       "distributor_id" bigint NULL DEFAULT NULL COMMENT '经销商id',
                                       "distributor_detail" clob NULL
);
-- ----------------------------
-- Table structure for distributor_level
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_level"  (
                                      "id" bigint NOT NULL COMMENT 'id',
                                      "name" varchar(255)  NULL DEFAULT '' COMMENT '客户等级',
                                      "description" varchar(255)  NULL DEFAULT '' COMMENT '说明',
                                      "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间'
);
-- ----------------------------
-- Table structure for distributor_reputation
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_reputation"  (
                                           "id" bigint NOT NULL COMMENT 'id',
                                           "name" varchar(255)  NULL DEFAULT NULL COMMENT '信誉等级',
                                           "description" varchar(255)  NULL DEFAULT NULL COMMENT '说明',
                                           "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间'
);
-- ----------------------------
-- Table structure for distributor_source
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_source"  (
                                       "id" bigint NOT NULL COMMENT 'id',
                                       "name" varchar(255)  NULL DEFAULT '' COMMENT '获客方式',
                                       "description" varchar(255)  NULL DEFAULT '' COMMENT '说明',
                                       "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间'
);