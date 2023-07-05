
CREATE TABLE `product_category`  (
                                     `id` varchar(32) NOT NULL COMMENT '主键',
                                     `tenant_id` varchar(32) NULL DEFAULT NULL COMMENT '所属租户',
                                     `name` varchar(50) NULL DEFAULT NULL COMMENT '分类名',
                                     `english_name` varchar(50) NULL DEFAULT NULL COMMENT '分类名（英文名）',
                                     `icon` varchar(128) NULL DEFAULT NULL COMMENT '图标图片',
                                     `product_category_page_image` varchar(128) NULL DEFAULT NULL COMMENT '商品分类页面图片URL',
                                     `product_list_page_image` varchar(128) NULL DEFAULT NULL COMMENT '商品列表页面图片URL',
                                     `parent_id` varchar(32) NULL DEFAULT NULL COMMENT '父级id',
                                     `layer` int(11) NULL DEFAULT 0 COMMENT '层级',
                                     `enabled_flag` char(1) NULL DEFAULT '0' COMMENT '状态 1-正常状态，0-未启用',
                                     `sort_id` int(11) NULL DEFAULT 99 COMMENT '排序',
                                     `deleted` char(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
                                     `creator` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
                                     `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(32) NULL DEFAULT NULL COMMENT '更新人',
                                     `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ;
