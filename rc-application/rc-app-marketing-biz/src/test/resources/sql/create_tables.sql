DROP TABLE IF EXISTS `order_cart`;
CREATE TABLE `order_cart`
(
    `id`                 varchar(50) NOT NULL COMMENT 'id',
    `user_id`            varchar(50) NULL DEFAULT NULL COMMENT '用户id',
    `type`               int NULL DEFAULT NULL COMMENT '类型 1.产品 2.拼单 3.秒杀 4.砍价',
    `product_id`         varchar(50) NULL DEFAULT NULL COMMENT '产品id',
    `product_uniqueid`   varchar(50) NULL DEFAULT NULL COMMENT '产品属性唯一id',
    `product_image`      varchar(2000) NULL DEFAULT NULL COMMENT '产品图片',
    `product_name`       varchar(255) NULL DEFAULT NULL COMMENT '产品名称',
    `spu_code`           varchar(255) NULL DEFAULT NULL COMMENT '货号',
    `sku_code`           varchar(255) NULL DEFAULT NULL COMMENT 'sku编号',
    `out_id`             varchar(255) NULL DEFAULT NULL COMMENT '外部id',
    `num`                int NULL DEFAULT NULL COMMENT '数量',
    `create_time`        datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `payed`              int NULL DEFAULT NULL COMMENT '是否支付',
    `new_state`          int NULL DEFAULT NULL COMMENT '是否为立即购买',
    `combination_id`     varchar(50) NULL DEFAULT NULL COMMENT '拼团id',
    `seckill_id`         varchar(50) NULL DEFAULT NULL COMMENT '秒杀产品id',
    `bargain_id`         varchar(50) NULL DEFAULT NULL COMMENT '砍价id',
    `shop_id`            varchar(50) NULL DEFAULT NULL COMMENT '店铺id',
    `carton_size_length` double NULL DEFAULT NULL COMMENT '长',
    `carton_size_height` double NULL DEFAULT NULL COMMENT '高',
    `carton_size_width`  double NULL DEFAULT NULL COMMENT '宽',
    `sku_attributes`     varchar(255) NULL DEFAULT NULL COMMENT '属性',
    `price`              double NULL DEFAULT NULL COMMENT '价格',
    `weight`             double NULL DEFAULT NULL COMMENT '重量',
    `packing_number`     int NULL DEFAULT NULL COMMENT '装箱数',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '购物车' ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `delivery_address`;
CREATE TABLE `delivery_address`
(
    `id`            varchar(32) NOT NULL COMMENT '主键',
    `customer_id`   varchar(32) NULL DEFAULT NULL COMMENT '用户id',
    `name`          varchar(64) NULL DEFAULT NULL COMMENT '用户名',
    `mobile`        varchar(255) NULL DEFAULT NULL COMMENT '手机号',
    `zipcode`       varchar(255) NULL DEFAULT NULL COMMENT '邮政编号',
    `province`      varchar(255) NULL DEFAULT NULL COMMENT '省',
    `province_code` varchar(255) NULL DEFAULT NULL COMMENT '省编码',
    `city`          varchar(255) NULL DEFAULT NULL COMMENT '市',
    `city_code`     varchar(255) NULL DEFAULT NULL COMMENT '市编码',
    `district`      varchar(255) NULL DEFAULT NULL COMMENT '区',
    `district_code` varchar(255) NULL DEFAULT NULL COMMENT '区编码',
    `detail`        varchar(255) NULL DEFAULT NULL COMMENT '详细地址',
    `defaulted`     bit(1) NULL DEFAULT '0' COMMENT '是否为默认 0：不是 1：是',
    `deleted`       char(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '收货地址';


DROP TABLE IF EXISTS `regular_order`;
CREATE TABLE `regular_order`
(
    `id`                      varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`               varchar(32) NULL DEFAULT NULL COMMENT '所属租户',
    `order_number`            varchar(32) NULL DEFAULT NULL COMMENT '订单编号',
    `order_status`            int(32) NULL DEFAULT NULL COMMENT '订单状态,0:等待卖家审核 1:等待买家付款 2:等待卖家发货 3:等待买家收货 4:交易完成',
    `product_quantity`        int(32) NULL DEFAULT NULL COMMENT '商品数量合计',
    `product_amount`          decimal(18, 2) NULL DEFAULT NULL COMMENT '商品金额',
    `freight_amount`          decimal(18, 2) NULL DEFAULT NULL COMMENT '运费金额',
    `pay_amount`              decimal(18, 2) NULL DEFAULT NULL COMMENT '支付金额',
    `change_amount`           decimal(18, 2) NULL DEFAULT NULL COMMENT '改价金额',
    `trade_type`              tinyint(4) NULL DEFAULT 0 COMMENT '支付方式 0：扫码支付',
    `pay_status`              tinyint(4) NULL DEFAULT NULL COMMENT '付款状态 0:未付款 1：已付款',
    `pay_time`                datetime NULL DEFAULT NULL COMMENT '付款时间',
    `consign_status`          tinyint(4) NULL DEFAULT NULL COMMENT '发货状态,0:未发货，1：已发货，2：已收货',
    `consign_time`            datetime NULL DEFAULT NULL COMMENT '发货时间',
    `end_time`                datetime NULL DEFAULT NULL COMMENT '完成时间',
    `close_time`              datetime NULL DEFAULT NULL COMMENT '关闭时间',
    `buyer_id`                varchar(32) NULL DEFAULT NULL COMMENT '买家id',
    `buyer_name`              varchar(32) NULL DEFAULT NULL COMMENT '买家名称',
    `buyer_order`             varchar(32) NULL DEFAULT NULL COMMENT '下单人',
    `buyer_account`           varchar(32) NULL DEFAULT NULL COMMENT '会员账号',
    `receiver_contact`        varchar(32) NULL DEFAULT NULL COMMENT '收货人',
    `receiver_province`       varchar(32) NULL DEFAULT NULL COMMENT '收货地址-省',
    `receiver_city`           varchar(32) NULL DEFAULT NULL COMMENT '收货地址-市',
    `receiver_district`       varchar(32) NULL DEFAULT NULL COMMENT '收货地址-区',
    `receiver_address_detail` varchar(200) NULL DEFAULT NULL COMMENT '收货地址详细信息',
    `receiver_mobile`         varchar(32) NULL DEFAULT NULL COMMENT '收货人手机号',
    `remark`                  varchar(500) NULL DEFAULT NULL COMMENT '订单备注',
    `trade_no`                varchar(32) NULL DEFAULT NULL COMMENT '交易号',
    `deleted`                 char(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`                 varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`             datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                 varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`             datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '常规订单';


DROP TABLE IF EXISTS `regular_order_line`;
CREATE TABLE `regular_order_line`
(
    `id`                 varchar(32) NOT NULL COMMENT '主键',
    `tenant_id`          varchar(32) NULL DEFAULT NULL COMMENT '所属租户',
    `regular_order_id`   varchar(32) NULL DEFAULT NULL COMMENT '订单ID',
    `product_id`         varchar(32) NULL DEFAULT NULL COMMENT '商品id',
    `product_name`       varchar(255) NULL DEFAULT NULL COMMENT '商品名称',
    `product_image`      varchar(500) NULL DEFAULT NULL COMMENT '商品图片',
    `product_article_no` varchar(200) NULL DEFAULT NULL COMMENT '商品货号',
    `product_attribute`  varchar(500) NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
    `product_price`      decimal(18, 4) NULL DEFAULT NULL COMMENT '商品项价格',
    `product_quantity`   int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
    `product_amount`     decimal(18, 2) NULL DEFAULT NULL COMMENT '商品金额',
    `deleted`            char(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`            varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`            varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`        datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '常规订单项信息';


DROP TABLE IF EXISTS `settlement_order`;
CREATE TABLE `settlement_order`
(
    `id`            varchar(32) NOT NULL COMMENT '主键',
    `trade_no`      varchar(64) NULL DEFAULT NULL COMMENT '系统内部订单号，只能是数字、大小写字母_-*且唯一',
    `out_trade_no`  varchar(64) NULL DEFAULT NULL COMMENT '外部订单流水号，比如：微信支付系统生成的订单号',
    `trade_type`      tinyint(4) NULL DEFAULT NULL COMMENT '支付方式 0 手动代付 1 微信支付 2 支付宝',
    `pay_amount`    decimal(18, 2) NULL DEFAULT NULL COMMENT '实付金额',
    `actual_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '支付金额',
    `buyer_id`      varchar(32) NULL DEFAULT NULL COMMENT '用户ID',
    `settled`       tinyint(4) NULL DEFAULT NULL COMMENT '是否清算 0:否 1:是',
    `settled_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '清算时间',
    `pay_status`    tinyint(4) NULL DEFAULT NULL COMMENT '0已支付，1未支付',
    `deleted`       char(1) NULL DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
    `creator`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`       varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '结算订单';
