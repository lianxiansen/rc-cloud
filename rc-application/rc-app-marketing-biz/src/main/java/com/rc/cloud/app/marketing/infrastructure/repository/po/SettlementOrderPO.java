package com.rc.cloud.app.marketing.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.app.marketing.domain.entity.common.PayStatus;
import com.rc.cloud.app.marketing.domain.entity.common.SettledEnum;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName SettlementOrderPO
 * @Author liandy
 * @Date 2023/8/3 11:29
 * @Description 结算订单持久化对象
 * @Version 1.0
 */
@TableName("settlement_order")
@Data
@EqualsAndHashCode(callSuper = true)
public class SettlementOrderPO extends BaseDO {

    /**
     * 支付结算单据ID
     */
    private String id;

    /**
     * 系统内部订单号，只能是数字、大小写字母_-*且唯一。
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 外部订单流水号，比如：微信支付系统生成的订单号
     */
    @TableField("out_trade_no")
    private String outTradeNo;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @TableField("pay_type")
    private int payType;

    /**
     * 应付金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;

    /**
     * 用户ID
     */
    @TableField("buyer_id")
    private String buyerId;

    /**
     * 是否清算 0:否 1:是
     */
    @TableField("settled")
    private SettledEnum settled;
    /**
     * 清算时间
     */
    @TableField("settled_time")
    private LocalDateTime settledTime;

    /**
     * 支付状态
     */
    @TableField("pay_status")
    private PayStatus payStatus;
}
