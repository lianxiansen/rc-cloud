package com.rc.cloud.app.marketing.infrastructure.repository.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Order
 * @Author liandy
 * @Date 2023/7/28 16:57
 * @Description 订单持久化对象
 * @Version 1.0
 */
@TableName("order")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderPO extends BaseDO {
    /**
     * 订单唯一标识
     */
    private String id;
    /**
     * 订单编号
     */
    @TableField("order_number")
    private String orderNumber;
    /**
     * 订单状态,0:等待卖家审核 1:等待买家付款 2:等待卖家发货 3:等待买家收货 4:交易完成
     *
     * @see com.rc.cloud.app.marketing.domain.entity.order.OrderStatus
     */
    @TableField("order_status")
    private int orderStatus;


    /**
     * 商品数量合计
     */
    @TableField("product_num")
    private int productNum;

    /**
     * 商品金额
     */
    @TableField("product_amount")
    private BigDecimal productAmount;
    /**
     * 运费
     */
    @TableField("freight_amount")
    private BigDecimal freightAmount;

    /**
     * 要支付的金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;


    /**
     * 改价金额
     */
    @TableField("changeAmount")
    private BigDecimal changeAmount;
    /**
     * 支付方式【0：扫码支付】
     */
    @TableField("pay_type")
    private int payType;
    /**
     * 付款状态 0:未付款 1：已付款
     *
     * @see com.rc.cloud.app.marketing.domain.entity.common.PayStatus
     */
    @TableField("pay_status")
    private int payStatus;
    /**
     * 付款时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;


    /**
     * 发货时间
     */
    @TableField("consign_time")
    private LocalDateTime consignTime;
    /**
     * 发货状态,0:未发货，1：已发货，2：已收货
     * @com.rc.cloud.app.marketing.domain.entity.order.ConsignStatus
     */
    @TableField("consign_status")
    private int consignStatus;
    /**
     * 完成时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 关闭时间
     */
    @TableField("close_time")
    private LocalDateTime closeTime;

    /**
     * 买家id
     */
    @TableField("buyer_id")
    private String buyerId;
    /**
     * 买家名称
     */
    @TableField("buyer_name")
    private String buyerName;
    /**
     * 下单人
     */
    @TableField("buyer_order")
    private String buyerOrder;


    /**
     * 会员账号
     */
    @TableField("buyer_account")
    private String buyerAccount;

    /**
     * 收货人
     */
    @TableField("receiver_contact")
    private String receiverContact;
    /**
     * 收货地址-省
     */
    @TableField("receiver_province")
    private String receiverProvince;
    /**
     * 收货地址-市
     */
    @TableField("receiver_city")
    private String receiverCity;
    /**
     * 收货地址-区
     */
    @TableField("receiver_district")
    private String receiverDistrict;
    /**
     * 收货地址详细信息
     */
    @TableField("receiver_address_detail")
    private String receiverAddressDetail;


    /**
     * 收货人手机号
     */
    @TableField("receiver_mobile")
    private String receiverMobile;

    /**
     * 订单备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 结算订单唯一标识
     */
    @TableField("trade_no")
    private String tradeNo;



}

