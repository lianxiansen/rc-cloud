CREATE TABLE `brand`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `name`         varchar(50) DEFAULT NULL COMMENT '品牌名',
    `logo`         varchar(256) NULL DEFAULT NULL COMMENT 'logo',
    `type`         varchar(50) DEFAULT NULL COMMENT '品牌类型',
    `enabled_flag` bit         DEFAULT false COMMENT '状态 1-正常状态，0-未启用',
    `sort`         int(11) DEFAULT 99 COMMENT '排序',
    `deleted`      bit         DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`      varchar(32) DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(32) DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='品牌表';


-- ----------------------------
-- 租户商品分类表（类似于店铺的商品自定义分类）
-- 现在系统ffcat商品分类应该放到这里
-- ----------------------------


CREATE TABLE `product_category`
(
    `id`                          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`                   varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `name`                        varchar(50)  DEFAULT NULL COMMENT '分类名',
    `english_name`                varchar(50)  DEFAULT NULL COMMENT '分类名（英文名）',
    `icon`                        varchar(256) DEFAULT NULL COMMENT '图标图片',
    `product_category_page_image` varchar(256) DEFAULT NULL COMMENT '商品分类页面图片URL',
    `product_list_page_image`     varchar(256) DEFAULT NULL COMMENT '商品列表页面图片URL',
    `parent_id`                   varchar(32)  DEFAULT NULL COMMENT '父级id',
    `layer`                       int(11) DEFAULT 0 COMMENT '层级',
    `enabled_flag`                bit          DEFAULT '0' COMMENT '状态 1-正常状态，0-未启用',
    `sort`                        int(11) DEFAULT 99 COMMENT '排序',
    `deleted`                     bit          DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`                     varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time`                 datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                     varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time`                 datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品分类表';


-- ----------------------------
-- 自定义分类
-- ----------------------------
CREATE TABLE `custom_classification`
(
    `id`                           varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`                    varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `name`                         varchar(50)  DEFAULT NULL COMMENT '自定义分类名',
    `custom_classification_image`  varchar(128) DEFAULT NULL COMMENT '分类图片URL',
    `product_poster`               varchar(128) DEFAULT NULL COMMENT '商品海报URL',
    `custom_classification_poster` varchar(128) DEFAULT NULL COMMENT '分类海报URL',
    `parent_id`                   varchar(32)  DEFAULT NULL COMMENT '父级id',
    `layer`                       int(11) DEFAULT 0 COMMENT '层级',
    `enabled_flag`                 bit          DEFAULT '0' COMMENT '状态 1-正常状态，0-未启用',
    `sort`                         int(11) DEFAULT 99 COMMENT '排序',
    `deleted`                      bit          DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`                      varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time`                  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                      varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time`                  datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品分类表';



-- ----------------------------
-- 颜色
-- ----------------------------


CREATE TABLE `attribute`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`   varchar(32) DEFAULT NULL COMMENT '所属租户',
    `name`        varchar(32) DEFAULT NULL COMMENT '属性名',
    `sort`        int(11) DEFAULT 99 COMMENT '排序',
    `deleted`     bit         DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`     varchar(32) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(32) DEFAULT NULL COMMENT '更新人',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='属性表';



-- ----------------------------
-- 颜色 红
-- ----------------------------


CREATE TABLE `attribute_value`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`    varchar(32) DEFAULT NULL COMMENT '所属租户',
    `attribute_id` varchar(32) DEFAULT NULL COMMENT '属性ID',
    `name`         varchar(32) DEFAULT NULL COMMENT '属性值名',
    `sort`         int(11) DEFAULT 99 COMMENT '排序',
    `deleted`      bit         DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`      varchar(32) DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(32) DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='属性值表';


-- ----------------------------
-- 商品表
-- ----------------------------

CREATE TABLE `product`
(
    `id`                         varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`                  varchar(32)    DEFAULT NULL COMMENT '所属租户',
    `custom_classification_id`   varchar(32)    DEFAULT NULL COMMENT '自定义分类id',
    `name`                       varchar(200)   DEFAULT NULL COMMENT '商品名',
    `remark`                     varchar(200)   DEFAULT NULL COMMENT '商品简介',
    `tag`                        varchar(200)   DEFAULT NULL COMMENT '商品标签',
    `list_image`                 varchar(128)   DEFAULT NULL COMMENT '主图URL',
    `spu_code`                   varchar(200)   DEFAULT NULL COMMENT '商品code',
    `product_origin`             int(11) DEFAULT 0 COMMENT '商品来源，0：自营',
    `out_id`                     varchar(32)    DEFAULT NULL COMMENT '外部id',
    `product_type`               int(11) DEFAULT 0 COMMENT '商品类型，0：普通商品',
    `onshelf_status`             int(11) DEFAULT 0 COMMENT '上架状态 0-上架初始，1-上架中，2-下架中',
    `brand_id`                   varchar(32)    DEFAULT NULL COMMENT '品牌ID',
    `first_category`             varchar(32)    DEFAULT NULL COMMENT '1级类目',
    `second_category`            varchar(32)    DEFAULT NULL COMMENT '2级类目',
    `third_category`             varchar(32)    DEFAULT NULL COMMENT '3级类目',
    `video_url`                  varchar(128)   DEFAULT NULL COMMENT '视频URL',
    `video_img`                  varchar(128)   DEFAULT NULL COMMENT '视频封面URL',
    `free_shipping_flag`         bit            DEFAULT '0' COMMENT '是否包邮 0不包邮，1包邮',
    `freight_type`               int(11) DEFAULT 0 COMMENT '运费类型，0统一运费，1运费模板',
    `freight_template_id`        varchar(32)    DEFAULT NULL COMMENT '商品使用运费模板',
    `freight_price`              decimal(18, 2) DEFAULT 0 COMMENT '运费价格',
    `get_integral`               decimal(18, 2) DEFAULT 0 COMMENT '可获得积分',
    `packing_lowest_buy_flag`    bit            DEFAULT '0' COMMENT '按照装箱数起购 0否，1是',
    `popularization_amount_rate` decimal(18, 2) DEFAULT NULL COMMENT '推广佣金比例',
    `distribution_flag`          bit            DEFAULT '0' COMMENT '是否参与分销 0否，1是',
    `refund_flag`                bit            DEFAULT '0' COMMENT '是否可以退款 0无法退款，1可以退款',
    `seckill_flag`               bit            DEFAULT '0' COMMENT '是否是抢购活动 0否，1是',
    `seckill_name`               varchar(128)   DEFAULT NULL COMMENT '抢购名',
    `seckill_master_image`       varchar(128)   DEFAULT NULL COMMENT '抢购主图',
    `seckill_begin_time`         datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '抢购开始时间',
    `seckill_end_time`           datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '抢购结束时间',
    `new_flag`                   bit            DEFAULT '0' COMMENT '是否新品 0否，1是',
    `explosives_flag`            bit            DEFAULT '0' COMMENT '是否是超级单品 0否，1是',
    `explosives_image`           varchar(128)   DEFAULT NULL COMMENT '超级单品海报URL，分类海报显示在产品分类页，尺寸500*280',
    `public_flag`                bit            DEFAULT '0' COMMENT '是否公开 0否，1是',
    `recommend_flag`             bit            DEFAULT '0' COMMENT '是否推荐 0否，1是',
    `sort`                       int(11)        DEFAULT 99 COMMENT '排序',
    `creator`                    varchar(32)    DEFAULT NULL COMMENT '创建人',
    `create_time`                datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                    varchar(32)    DEFAULT NULL COMMENT '更新人',
    `update_time`                datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                    bit            DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `recycle_flag`               bit            DEFAULT '0' COMMENT '是否回收 0否，1是',
    `promotion_image`            varchar(128)   DEFAULT NULL COMMENT '推广图URL',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SPU表 ';


-- ----------------------------
-- 商品sku
-- ----------------------------

CREATE TABLE `product_sku`
(
    `id`                      varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`               varchar(32)    DEFAULT NULL COMMENT '所属租户',
    `product_id`              varchar(32)    DEFAULT NULL COMMENT '商品ID',
    `sku_code`                varchar(32)    DEFAULT NULL COMMENT '货号',
    `supply_price`            decimal(18, 2) DEFAULT NULL COMMENT '供货价',
    `weight`                  varchar(32)    DEFAULT NULL COMMENT '重量',
    `out_id`                  varchar(32)    DEFAULT NULL COMMENT '外部ID',
    `limit_buy`               int(11) DEFAULT NULL COMMENT '限购',
    `price`                   decimal(18, 2) DEFAULT NULL COMMENT '价格',
    `inventory`               int(11) DEFAULT NULL COMMENT '库存',
    `seckill_limit_buy`       int(11) DEFAULT NULL COMMENT '秒杀限购',
    `seckill_price`           decimal(18, 2) DEFAULT NULL COMMENT '秒杀价格',
    `seckill_inventory`       int(11) DEFAULT NULL COMMENT '秒杀库存',
    `seckill_total_inventory` int(11) DEFAULT NULL COMMENT '秒杀总库存，计算已抢购百分比使用',
    `carton_size_length`      int(11) DEFAULT NULL COMMENT '箱规：长',
    `carton_size_width`       int(11) DEFAULT NULL COMMENT '箱规：宽',
    `carton_size_height`      int(11) DEFAULT NULL COMMENT '箱规：高',
    `packing_number`          int(11) DEFAULT NULL COMMENT '装箱数',
    `sort`                    int(11) DEFAULT 99 COMMENT '排序',
    `deleted`                 bit            DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`                 varchar(32)    DEFAULT NULL COMMENT '创建人',
    `create_time`             datetime       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                 varchar(32)    DEFAULT NULL COMMENT '更新人',
    `update_time`             datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SKU表 ';



-- ----------------------------
-- 商品关联表
-- ----------------------------

CREATE TABLE `product_dict`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`   varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_id`  varchar(32)  DEFAULT NULL COMMENT '商品ID',
    `dict_key`    varchar(200) DEFAULT NULL COMMENT '商品展示key',
    `dict_value`  varchar(200) DEFAULT NULL COMMENT '商品展示value',
    `sort`        int(11) DEFAULT 99 COMMENT '排序',
    `deleted`     bit          DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`     varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品字典表 ';



-- ----------------------------
-- 商品操作记录表
-- ----------------------------

CREATE TABLE `product_operate`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`    varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_id`   varchar(32)  DEFAULT NULL COMMENT '商品ID',
    `name`         varchar(200) DEFAULT NULL COMMENT '操作人名字',
    `ip`           varchar(200) DEFAULT NULL COMMENT '操作人ip',
    `operate_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SPU操作记录表 ';



-- ----------------------------
-- 商品详情
-- ----------------------------


CREATE TABLE `product_detail`
(
    `id`                varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`         varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_id`        varchar(32)  DEFAULT NULL COMMENT '商品ID',
    `detail`            text         DEFAULT NULL COMMENT '详情',
    `install_video_url` varchar(128) DEFAULT NULL COMMENT '安装视频URL',
    `install_video_img` varchar(128) DEFAULT NULL COMMENT '安装视频封面URL',
    `install_detail`    text         DEFAULT NULL COMMENT '安装详情',
    `deleted`           bit          DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`           varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品详情表';



-- ----------------------------
-- 商品属性表
-- 存储[
-- {
--             "attribute_name" : "尺码",
--             "attribute_value" : "42",
--             "sort" : 42
--         },
--         {
--             "attribute_name" : "尺码",
--             "attribute_value" : "44",
--             "sort" : 42
--         },
--         {
--             "attribute_name" : "颜色",
--             "attribute_value" : "卡其色",
--             "sort" : 42
--         },
--         {
--             "attribute_name" : "尺码",
--             "attribute_value" : "39",
--             "sort" : 42
--         }
--     ]
-- ----------------------------

CREATE TABLE `product_attribute`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`   varchar(32) DEFAULT NULL COMMENT '所属租户',
    `product_id`  varchar(32) DEFAULT NULL COMMENT '商品ID',
    `content`     text        DEFAULT NULL COMMENT 'JSON',
    `deleted`     bit         DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`     varchar(32) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(32) DEFAULT NULL COMMENT '更新人',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SKU属性表 ';



-- ----------------------------
-- 商品SKU属性表
-- 存储[
-- {
--                     "attribute_name" : "颜色",
--                     "attribute_value" : "卡其色",
--                     "sort" : 42
--                 },
--                 {
--                     "attribute_name" : "尺码",
--                     "attribute_value" : "44",
--                     "sort" : 42
--                 }
--             ]
-- ----------------------------

CREATE TABLE `product_sku_attribute`
(
    `id`             varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`      varchar(32) DEFAULT NULL COMMENT '所属租户',
    `product_sku_id` varchar(32) DEFAULT NULL COMMENT '商品skuID',
    `content`        text        DEFAULT NULL COMMENT 'JSON',
    `deleted`        bit         DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`        varchar(32) DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(32) DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SKU属性表 ';



-- ----------------------------
-- 商品图片表
-- ----------------------------

CREATE TABLE `product_image`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`   varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_id`  varchar(32)  DEFAULT NULL COMMENT '商品ID',
    `url`         varchar(128) DEFAULT NULL COMMENT 'URL',
    `image_type`  int(11) DEFAULT 0 COMMENT '图片类型',
    `sort`        int(11) DEFAULT 99 COMMENT '排序',
    `deleted`     bit          DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`     varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SPU图片表 ';


-- ----------------------------
-- 商品SKU    图片表
-- ----------------------------

CREATE TABLE `product_sku_image`
(
    `id`             varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`      varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_sku_id` varchar(32)  DEFAULT NULL COMMENT '商品skuID',
    `url`            varchar(128) DEFAULT NULL COMMENT 'URL',
    `sort`           int(11) DEFAULT 99 COMMENT '排序',
    `deleted`        bit          DEFAULT false COMMENT '删除标识 0未删除，1已删除',
    `creator`        varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='商品SKU图片表';


-- ----------------------------
-- 相关商品组表
-- ----------------------------
-- CREATE TABLE `product_related_group`
-- (
--     `id`                 varchar(32) NOT NULL COMMENT '主键',
--     `name`               varchar(200) DEFAULT NULL COMMENT '商品组名',
--     `tenant_id`          varchar(32)  DEFAULT NULL COMMENT '所属租户',
--     `product_id`         varchar(32)  DEFAULT NULL COMMENT '当前商品id',
--     `related_product_id` varchar(32)  DEFAULT NULL COMMENT '相关商品id',
--     `sort`            int(11) DEFAULT 99 COMMENT '排序',
--     `deleted`            bit      DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
--     `creator`            varchar(32)  DEFAULT NULL COMMENT '创建人',
--     `create_time`        datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--     `updater`            varchar(32)  DEFAULT NULL COMMENT '更新人',
--     `update_time`        datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--     PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB  COMMENT='商品相关表';


DROP TABLE IF EXISTS `product_group`;
CREATE TABLE `product_group`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`   varchar(32) NULL DEFAULT NULL COMMENT '所属租户',
    `name`        varchar(200) NULL DEFAULT NULL COMMENT '商品组名',
    `product_id`  varchar(32) NULL DEFAULT NULL COMMENT '当前商品id',
    `deleted`     bit(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`     varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '商品组合表';

DROP TABLE IF EXISTS `product_group_item`;
CREATE TABLE `product_group_item`
(
    `id`               varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`        varchar(32)  DEFAULT NULL COMMENT '所属租户',
    `product_group_id` varchar(32) NULL DEFAULT NULL COMMENT '组合id',
    `product_id`       varchar(32) NULL DEFAULT NULL COMMENT '当前商品id',
    `deleted`          bit(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`          varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`          varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '商品组合项表';


DROP TABLE IF EXISTS `product_recommend`;
CREATE TABLE `product_recommend`
(
    `id`                   varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`            varchar(32) NULL DEFAULT NULL COMMENT '所属租户',
    `product_id`           varchar(32) NULL DEFAULT NULL COMMENT '当前商品id',
    `recommend_product_id` varchar(32) NULL DEFAULT NULL COMMENT '推荐商品id',
    `deleted`              bit(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`              varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`              varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`          datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '商品推荐' ROW_FORMAT = DYNAMIC;

