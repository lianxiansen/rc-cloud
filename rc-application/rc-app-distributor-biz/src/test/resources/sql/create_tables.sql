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
                                "id" varchar(255) NOT NULL COMMENT 'id',
                                "company_name" varchar(255)  NULL DEFAULT '' COMMENT '企业名称',
                                "contact" varchar(255)  NULL DEFAULT '' COMMENT '联系人，用逗号隔开',
                                "mobile" varchar(1000)  NULL DEFAULT '' COMMENT '联系方式，用逗号隔开',
                                "password" varchar(25)  NULL DEFAULT '' COMMENT '密码',
                                "province" varchar(255)  NULL DEFAULT '' COMMENT '省',
                                "city" varchar(255)  NULL DEFAULT '' COMMENT '市',
                                "county" varchar(255)  NULL DEFAULT '' COMMENT '区',
                                "province_code" varchar(255)  NULL DEFAULT '' COMMENT '省编码',
                                "city_code" varchar(255)  NULL DEFAULT '' COMMENT '市编码',
                                "county_code" varchar(255)  NULL DEFAULT '' COMMENT '区编码',
                                "address" varchar(255)  NULL DEFAULT '' COMMENT '详细地址',
                                "start_time" timestamp NULL DEFAULT NULL  COMMENT '合作开始',
                                "end_time" timestamp NULL DEFAULT NULL COMMENT '合作结束',
                                "status" int NULL DEFAULT 1 COMMENT '对接状态1未对接2已对接',
                                "remarks" clob  NULL COMMENT '备注',
                                "admin_id" varchar(255) NULL DEFAULT NULL COMMENT '管理员id',
                                "telephone" varchar(255)  NULL DEFAULT '' COMMENT '联系电话',
                                "channel" varchar(255) NULL DEFAULT 0 COMMENT '客户渠道id',
                                "source" varchar(255) NULL DEFAULT 0 COMMENT '获客方式id',
                                "level" varchar(255) NULL DEFAULT 0 COMMENT '客户等级id',
                                "reputation" varchar(255) NULL DEFAULT 0 COMMENT '信誉等级',
                                "established_time" varchar(255)  NULL DEFAULT '' COMMENT '成立时间',
                                "deleted" int NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                "recycle_flag" int NULL DEFAULT 0 COMMENT '是否在回收站, 0否, 1是',
                                "locking" int NULL DEFAULT 0 COMMENT '是否锁定',
                                "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- ----------------------------
-- Table structure for distributor_channel
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_channel"  (
                                "id" varchar(255) NOT NULL COMMENT 'id',
                                "name" varchar(255)  NULL DEFAULT NULL COMMENT '渠道名称',
                                "description" varchar(255)  NULL DEFAULT NULL COMMENT '说明',
                                "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                "deleted" int NOT NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- -- ----------------------------
-- -- Table structure for distributor_contact
-- -- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_contact"  (
                                        "id" varchar(255) NOT NULL,
                                        "name" varchar(255)  NULL DEFAULT '' COMMENT '联系人',
                                        "mobile" varchar(255)  NULL DEFAULT '' COMMENT '手机',
                                        "password" varchar(255)  NULL DEFAULT '' COMMENT '密码',
                                        "distributor_id" varchar(255) NULL DEFAULT NULL,
                                        "lastread_time" varchar(255)  NULL DEFAULT NULL COMMENT '最后浏览时间',
                                        "click" int NULL DEFAULT 0 COMMENT '用户浏览商品数量',
                                        "headimage" varchar(255)  NULL DEFAULT NULL,
                                        "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- ----------------------------
-- Table structure for distributor_detail
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_detail"  (
                                       "id" varchar(255) NOT NULL COMMENT 'id',
                                       "distributor_id" varchar(255) NULL DEFAULT NULL COMMENT '经销商id',
                                       "distributor_detail" clob NULL,
                                        "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- ----------------------------
-- Table structure for distributor_level
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_level"  (
                                      "id" varchar(255) NOT NULL COMMENT 'id',
                                      "name" varchar(255)  NULL DEFAULT '' COMMENT '客户等级',
                                      "description" varchar(255)  NULL DEFAULT '' COMMENT '说明',
                                    "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                    "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                    "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                    "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                    "deleted" int NOT NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                    "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- ----------------------------
-- Table structure for distributor_reputation
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_reputation"  (
                                           "id" varchar(255) NOT NULL COMMENT 'id',
                                           "name" varchar(255)  NULL DEFAULT NULL COMMENT '信誉等级',
                                           "description" varchar(255)  NULL DEFAULT NULL COMMENT '说明',
                                            "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                            "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                            "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                            "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                            "deleted" int NOT NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                            "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);
-- ----------------------------
-- Table structure for distributor_source
-- ----------------------------
CREATE TABLE IF NOT EXISTS "distributor_source"  (
                                       "id" varchar(255) NOT NULL COMMENT 'id',
                                       "name" varchar(255)  NULL DEFAULT '' COMMENT '获客方式',
                                       "description" varchar(255)  NULL DEFAULT '' COMMENT '说明',
                                        "creator" varchar(255)  NULL DEFAULT NULL COMMENT '数据添加人员',
                                        "update_time" timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                        "updater" varchar(255)  NULL DEFAULT NULL COMMENT '更新人',
                                        "create_time" timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                        "deleted" int NOT NULL DEFAULT 0 COMMENT '是否删除, 0否, 1是',
                                        "tenant_id" varchar(255)  NULL DEFAULT NULL COMMENT '租户id'
);