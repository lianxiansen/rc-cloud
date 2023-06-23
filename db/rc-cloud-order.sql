SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- 品牌表
-- ----------------------------

DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
                         `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                         `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '品牌名',
                         `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '品牌类型',
                         `enabled_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态 1-正常状态，0-未启用',
                         `sort_id` int(11) DEFAULT 99 COMMENT '排序',
                         `deleted` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
                         `creator` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updater` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
                         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='品牌表';