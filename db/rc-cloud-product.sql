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

-- ----------------------------
-- Table structure for config_info
-- ----------------------------

-- ----------------------------
-- Table structure for pro_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pro_attribute`;
CREATE TABLE `pro_attribute` (
     `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
     `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
     `content` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性值',
     `sort_id` int(11) DEFAULT NULL COMMENT '排序',
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

DROP TABLE IF EXISTS `pro_attribute_value`;
CREATE TABLE `pro_attribute_value` (
       `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
       `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
       `attribute_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性ID',
       `content` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性值',
       `sort_id` int(11) DEFAULT NULL COMMENT '排序',
       `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
       `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
       `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
       `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='属性值表';


-- ----------------------------
-- Table structure for pro_product
-- ----------------------------
DROP TABLE IF EXISTS `pro_product`;
CREATE TABLE `pro_product` (
       `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
       `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
       `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名',
       `product_origin` int(11) DEFAULT NULL COMMENT '商品来源',
       `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部id',
       `product_type` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品类型',
       `product_status` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品状态',
       `is_onshelf` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上架状态',
       `brand_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '品牌ID',
       `first_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '1级类目',
       `second_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '2级类目',
       `third_category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '3级类目',
       `video_url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频URL',
       `video_img` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视频封面URL',
       `freight_type` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '运费类型',
       `freight_template_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品使用运费模板',
       `freight_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '运费价格',
       `sort_id` int(11) DEFAULT NULL COMMENT '排序',
       `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
       `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
       `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
       `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SPU表 ';




-- ----------------------------
-- Table structure for pro_product_item
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_item`;
CREATE TABLE `pro_product_item` (
        `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
        `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
        `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
        `article_number` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '货号',
        `is_refund` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否可以退款 0无法退款，1可以退款',
        `price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '价格',
        `supply_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '供货价',
        `weight` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '重量',
        `has_image` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否有图片 0无图片，1有图片',
        `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部ID',
        `limit_buy` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '限购',
        `inventory` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '库存',
        `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
        `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
        `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU表 ';




-- ----------------------------
-- Table structure for pro_product_item_seckill
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_item_seckill`;
CREATE TABLE `pro_product_item_seckill` (
            `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
            `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
            `product_item_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品规格ID',
            `seckill_limit_buy` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '限购',
            `seckill_price` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '秒杀价格',
            `seckill_inventory` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '秒杀库存',
            `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
            `created_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
            `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            `updated_by` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
            `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU表 ';



-- ----------------------------
-- Table structure for pro_product_detail
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_detail`;
CREATE TABLE `pro_product_detail` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
          `detail` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详情',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品详情表 ';





-- ----------------------------
-- Table structure for pro_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_attribute`;
CREATE TABLE `pro_product_attribute` (
         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
         `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
         `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
         `content` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JSON',

         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU属性表 ';



-- ----------------------------
-- Table structure for pro_product_item_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_item_attribute`;
CREATE TABLE `pro_product_item_attribute` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `article_number` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
          `content` text COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JSON',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU属性表 ';




-- ----------------------------
-- Table structure for pro_product_image
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_image`;
CREATE TABLE `pro_product_image` (
             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
             `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
             `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
             `url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'URL',
             `is_default` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否是默认',
             `sort_id` int(11) DEFAULT NULL COMMENT '排序',
             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SPU图片表 ';


-- ----------------------------
-- Table structure for pro_product_item_image
-- ----------------------------
DROP TABLE IF EXISTS `pro_product_item_image`;
CREATE TABLE `pro_product_item_image` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ID',
          `url` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'URL',
          `is_default` int(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否是默认',
          `sort_id` int(11) DEFAULT NULL COMMENT '排序',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品SKU图片表';

