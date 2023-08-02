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

DROP TABLE IF EXISTS `order_cart`;
CREATE TABLE `order_cart`  (
                               `id` varchar(50)  NOT NULL COMMENT 'id',
                               `userid` varchar(50) NULL DEFAULT NULL COMMENT '用户id',
                               `type` int NULL DEFAULT NULL COMMENT '类型 1.产品 2.拼单 3.秒杀 4.砍价',
                               `productid` varchar(50) NULL DEFAULT NULL COMMENT '产品id',
                               `productuniqueid` varchar(50) NULL DEFAULT NULL COMMENT '产品属性唯一id',
                               `product_image` varchar(2000) NULL DEFAULT NULL COMMENT '产品图片',
                               `product_name` varchar(255) NULL DEFAULT NULL COMMENT '产品名称',
                               `outid` varchar(255) NULL DEFAULT NULL COMMENT '货号',
                               `num` int NULL DEFAULT NULL COMMENT '数量',
                               `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `payed` int NULL DEFAULT NULL COMMENT '是否支付',
                               `newstate` int NULL DEFAULT NULL COMMENT '是否为立即购买',
                               `combinationid` varchar(50) NULL DEFAULT NULL COMMENT '拼团id',
                               `seckillid` varchar(50) NULL DEFAULT NULL COMMENT '秒杀产品id',
                               `bargainid` varchar(50) NULL DEFAULT NULL COMMENT '砍价id',
                               `shopid` varchar(50) NULL DEFAULT NULL COMMENT '店铺id',
                               `cartonSizeLength` double NULL DEFAULT NULL COMMENT '长',
                               `cartonSizeHeight` double NULL DEFAULT NULL COMMENT '高',
                               `cartonSizeWidth` double NULL DEFAULT NULL COMMENT '宽',
                               `skuAttributes` varchar(255) NULL DEFAULT NULL COMMENT '属性',
                               `price` double NULL DEFAULT NULL COMMENT '价格',
                               `weight` double NULL DEFAULT NULL COMMENT '重量',
                               `packingNumber` int NULL DEFAULT NULL COMMENT '装箱数',
                               PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '购物车' ROW_FORMAT = DYNAMIC;