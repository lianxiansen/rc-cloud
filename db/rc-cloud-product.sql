/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : rc-cloud-config

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 20/06/2023 08:12:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
     `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
     `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
     `content` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性值',
     `sort_id` int(11) DEFAULT 99 COMMENT '排序',
     `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
     `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
     `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
     `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='属性表';



-- ----------------------------
-- Table structure for pro_attribute_value
-- ----------------------------

DROP TABLE IF EXISTS `attribute_value`;
CREATE TABLE `attribute_value` (
       `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
       `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
       `attribute_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性ID',
       `content` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性值',
       `sort_id` int(11) DEFAULT 99 COMMENT '排序',
       `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
       `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
       `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
       `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='属性值表';


-- ----------------------------
-- 商品表
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
       `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
       `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
       `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名',
       `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品简介',
       `tag` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品标签',
       `master_image`  varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主图URL',
       `spu_code` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品code',
       `product_origin` int(11) DEFAULT 0 COMMENT '商品来源，0：ffcat',
       `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部id',
       `product_type` int(11) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '商品类型，0：普通商品',
       `product_status` int(11) COLLATE utf8mb4_bin DEFAULT 1 COMMENT '商品状态 1-正常状态',
       `onshelf_status` int(11) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '上架状态 0-上架初始，1-上架中，2-下架中',
       `brand_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '品牌ID',
       `first_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '1级类目',
       `second_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '2级类目',
       `third_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '3级类目',
       `video_url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频URL',
       `video_img` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频封面URL',
       `install_video_url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '安装视频URL',
       `install_video_img` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '安装视频封面URL',
       `free_shipping_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否包邮 0不包邮，1包邮',
       `freight_type` int(11) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '运费类型，0统一运费，1运费模板',
       `freight_template_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品使用运费模板',
       `freight_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '运费价格',
       `lowest_buy` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 1 COMMENT '最低起购量',
       `popularization_amount_rate` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推广佣金比例',
       `distribution_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否参与分销 0否，1是',
       `refund_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否可以退款 0无法退款，1可以退款',
       `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
       `seckill_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否是抢购活动 0否，1是',
       `seckill_name`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '抢购名',
       `seckill_master_image`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '抢购主图',
       `seckill_begin_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '抢购开始时间',
       `seckill_end_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '抢购结束时间',
       `new_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否新品 0否，1是',
       `explosives_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否是超级单品 0否，1是',
       `explosives_image` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '超级单品海报URL，分类海报显示在产品分类页，尺寸500*280',
       `public_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否公开 0否，1是',
       `recommend_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否推荐 0否，1是',
       `sort_id` int(11) DEFAULT 99 COMMENT '排序',
       `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
       `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
       `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SPU表 ';


-- ----------------------------
-- 商品sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
        `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
        `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
        `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
        `sku_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '货号',
        `supply_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '供货价',
        `weight` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '重量',
        `has_image` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否有图片 0无图片，1有图片',
        `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部ID',
        `limit_buy` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '限购',
        `price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '价格',
        `inventory` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '库存',
        `seckill_limit_buy` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '限购',
        `seckill_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '秒杀价格',
        `seckill_inventory` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '秒杀库存',
        `seckill_total_inventory` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '秒杀总库存，计算已抢购百分比使用',
        `sort_id` int(11) DEFAULT 99 COMMENT '排序',
        `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
        `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
        `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU表 ';



-- ----------------------------
-- 商品关联表
-- ----------------------------
CREATE TABLE `product_dict` (
       `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
       `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
       `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
       `key` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品展示key',
       `value` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品展示value',
       `sort_id` int(11) DEFAULT 99 COMMENT '排序',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品字典表 ';




-- ----------------------------
-- 商品操作记录表
-- ----------------------------
CREATE TABLE `product_operate` (
                           `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                           `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
                           `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
                           `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人名字',
                           `ip` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人ip',
                           `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SPU操作记录表 ';





-- ----------------------------
-- 商品详情
-- ----------------------------
DROP TABLE IF EXISTS `product_detail`;
CREATE TABLE `product_detail` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
          `detail` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详情',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品详情表 ';





-- ----------------------------
-- 商品属性表
-- 存储[
-- {
--             "attribute_name" : "尺码",
--             "attribute_value" : "42",
--             "sort_id" : 42
--         },
--         {
--             "attribute_name" : "尺码",
--             "attribute_value" : "44",
--             "sort_id" : 42
--         },
--         {
--             "attribute_name" : "颜色",
--             "attribute_value" : "卡其色",
--             "sort_id" : 42
--         },
--         {
--             "attribute_name" : "尺码",
--             "attribute_value" : "39",
--             "sort_id" : 42
--         }
--     ]
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute` (
         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
         `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
         `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
         `content` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JSON',
         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU属性表 ';



-- ----------------------------
-- 商品SKU属性表
-- 存储[
-- {
--                     "attribute_name" : "颜色",
--                     "attribute_value" : "卡其色",
--                     "sort_id" : 42
--                 },
--                 {
--                     "attribute_name" : "尺码",
--                     "attribute_value" : "44",
--                     "sort_id" : 42
--                 }
--             ]
-- ----------------------------
DROP TABLE IF EXISTS `product_sku_attribute`;
CREATE TABLE `product_sku_attribute` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_sku_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品skuID',
          `content` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JSON',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU属性表 ';




-- ----------------------------
-- 商品图片表
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
             `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
             `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
             `url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'URL',
             `default_flag` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否是默认',
             `sort_id` int(11) DEFAULT 99 COMMENT '排序',
             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SPU图片表 ';


-- ----------------------------
-- 商品SKU    图片表
-- ----------------------------
DROP TABLE IF EXISTS `product_sku_image`;
CREATE TABLE `product_item_image` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_sku_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品skuID',
          `url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'URL',
          `default_flag` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否是默认',
          `sort_id` int(11) DEFAULT 99 COMMENT '排序',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU图片表';


-- ----------------------------
-- 相关商品组表
-- ----------------------------
DROP TABLE IF EXISTS `product_related_group`;
CREATE TABLE `product_related_group` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品组名',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '当前商品id',
          `related_product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '相关商品id',
          `sort_id` int(11) DEFAULT 99 COMMENT '排序',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品相关表';


