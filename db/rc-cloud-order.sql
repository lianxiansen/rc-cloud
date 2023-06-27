SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- 统一支付订单表,多店铺一起支付
-- ----------------------------

DROP TABLE IF EXISTS `unified_payment_order`;
CREATE TABLE `unified_payment_order` (
         `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
         `user_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
         `trade_number` char(64) DEFAULT NULL COMMENT '交易单号',
         `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部id',
         `pay_status` tinyint(4) DEFAULT NULL COMMENT '0已支付，1未支付',
         `realpay_amount` decimal(18,2) DEFAULT NULL COMMENT '实际支付金额',
         `wechat_order_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信订单no',
         `alipay_order_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付宝订单no',
         `deleted` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
         `creator` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         `updater` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一支付订单';

-- ----------------------------
-- 订单表
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
         `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
         `unified_payment_order_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '统一支付订单',
         `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
         `user_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
         `share_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分享记录ID',
         `share_user_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分享人ID',
         `order_origin` int(11) DEFAULT 0 COMMENT '订单来源，0：ffcat',
         `out_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部id',
         `order_number` char(64) DEFAULT NULL COMMENT '订单号',
         `payable_amount` decimal(18,2) DEFAULT NULL COMMENT '应付总额,商品金额+运费金额',
         `product_fee` decimal(18,2) DEFAULT NULL COMMENT '商品金额',
         `express_fee` decimal(18,2) DEFAULT NULL COMMENT '运费金额',
         `realpay_product_fee` decimal(18,2) DEFAULT NULL COMMENT '实际支付商品金额（改价产生）',
         `realpay_express_fee` decimal(18,2) DEFAULT NULL COMMENT '实际支付运费金额（改价产生）',
         `realpay_amount` decimal(18,2) DEFAULT NULL COMMENT '实际支付金额',
         `product_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '商品优惠金额（促销价、满减、阶梯价）',
         `store_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '店铺优惠金额',
         `selected_store_coupon_id` varchar(200) DEFAULT NULL COMMENT '店铺优惠券id',
         `selected_store_coupon_name` varchar(200) DEFAULT NULL COMMENT '店铺优惠券名',
         `platform_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '平台优惠金额，多店铺优惠按照支付金额平摊',
         `selected_platform_coupon_id` varchar(32) DEFAULT NULL COMMENT '平台优惠id',
         `selected_platform_coupon_name` varchar(200) DEFAULT NULL COMMENT '平台优惠名',
         `redbag_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '红包优惠金额，多店铺优惠按照支付金额平摊',
         `selected_redbag_id_list` varchar(500) DEFAULT NULL COMMENT '使用红包的列表（可以多红包一起使用）',
         `discount_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
         `popularization_amount` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推广佣金',
         `use_integral` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '下单时使用的积分',
         `get_integral` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '可获得积分',
         `payment_type` tinyint(4) DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
         `payment_status` tinyint(4) DEFAULT NULL COMMENT '支付状态【0->待付款；1->已付款】',
         `refund_amount` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '退款金额',
         `refund_popularization_amount` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '退款推广佣金',
         `refund_get_integral` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '退款积分',
         `receiver_name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
         `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
         `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
         `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
         `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',
         `receiver_region` varchar(32) DEFAULT NULL COMMENT '区',
         `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
         `remark` varchar(500) DEFAULT NULL COMMENT '订单备注',
         `confirm_status` tinyint(4) DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
         `cancel_reason` varchar(64) DEFAULT NULL COMMENT '取消订单原因',
         `check_status` tinyint(4) DEFAULT NULL COMMENT '下单后审核通过，后可以支付',
         `check_time` datetime DEFAULT NULL COMMENT '审核时间',
         `cancel_time` datetime DEFAULT NULL COMMENT '取消订单时间',
         `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
         `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
         `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
         `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
         `deleted` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
         `creator` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         `updater` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';


-- ----------------------------
-- 子订单表
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
          `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
          `order_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单ID',
          `out_order_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部订单ID',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `out_product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部商品id',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品id',
          `product_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品code',
          `product_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
          `product_image` varchar(500) DEFAULT NULL COMMENT 'spu_pic',
          `product_brand` varchar(200) DEFAULT NULL COMMENT '品牌',
          `first_category` varchar(32) DEFAULT NULL COMMENT '1级分类',
          `second_category` varchar(32) DEFAULT NULL COMMENT '2级分类',
          `third_category` varchar(32) DEFAULT NULL COMMENT '3级分类',
          `out_product_sku_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '外部货号',
          `product_sku_id` varchar(32) DEFAULT NULL COMMENT '商品sku编号',
          `product_sku_code` varchar(32) DEFAULT NULL COMMENT '商品sku货号',
          `product_sku_name` varchar(255) DEFAULT NULL COMMENT '商品sku名字',
          `product_sku_image` varchar(500) DEFAULT NULL COMMENT '商品sku图片',
          `product_sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品sku价格',
          `product_sku_quantity` int(11) DEFAULT NULL COMMENT '商品购买的数量',
          `product_sku_attrbutes` varchar(500) DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
          `product_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '商品优惠金额（促销价、满减、阶梯价）',
          `product_coupon_name` varchar(200) DEFAULT NULL COMMENT '商品优惠名（促销价、满减、阶梯价）',
          `store_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '店铺优惠金额',
          `platform_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '平台优惠金额，多店铺优惠按照支付金额平摊',
          `redbag_coupon_amount` decimal(18,2) DEFAULT NULL COMMENT '红包优惠金额，多店铺优惠按照支付金额平摊',
          `product_fee` decimal(18,2) DEFAULT NULL COMMENT '商品金额',
          `realpay_product_fee` decimal(18,2) DEFAULT NULL COMMENT '实际支付商品金额（改价产生）',
          `refund_status` tinyint(4) DEFAULT NULL COMMENT '退款状态[0->未发起退款，1->退款中，2->退款成功 ，3->退款失败，4->退款撤销]',
          `refund_quantity` int(11) DEFAULT NULL COMMENT  '退款数量',
          `refund_amount` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '退款金额',
          `express_status` tinyint(4) DEFAULT NULL COMMENT '发货状态[0->未发货，1->已发货，2->已收货]',
          `popularization_amount` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推广佣金',
          `popularization_amount_rate` decimal(18,2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推广佣金比率',
          `use_integral` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '下单时使用的积分',
          `get_integral` decimal(18,2) COLLATE utf8mb4_bin DEFAULT 0 COMMENT '可获得积分',
          `deleted` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
          `creator` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
          `updater` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项信息';


-- ----------------------------
-- 子订单发货表
-- ----------------------------
DROP TABLE IF EXISTS `order_item_express`;
CREATE TABLE `order_item_express` (
          `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
          `order_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单ID',
          `order_item_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ID',
          `tenant_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属租户',
          `product_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品id',
          `product_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
          `express_code` varchar(500) DEFAULT NULL COMMENT '物流code，yunda',
          `express_name` varchar(200) DEFAULT NULL COMMENT '物流名',
          `express_numer` varchar(200) DEFAULT NULL COMMENT '物流单号',
          `express_status` tinyint(4) DEFAULT NULL COMMENT '发货状态[0->未发货，1->已发货，2->已收货]',
          `delivery_quantity` tinyint(4) DEFAULT NULL COMMENT '发货数量',
          `delivery_name` varchar(100) DEFAULT NULL COMMENT '发货姓名',
          `delivery_phone` varchar(32) DEFAULT NULL COMMENT '发货电话',
          `delivery_post_code` varchar(32) DEFAULT NULL COMMENT '发货邮编',
          `delivery_province` varchar(32) DEFAULT NULL COMMENT '发货省份/直辖市',
          `delivery_city` varchar(32) DEFAULT NULL COMMENT '发货城市',
          `delivery_region` varchar(32) DEFAULT NULL COMMENT '发货区',
          `delivery_detail_address` varchar(200) DEFAULT NULL COMMENT '发货详细地址',
          `deleted` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识 0未删除，1已删除',
          `creator` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
          `updater` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单运费表';