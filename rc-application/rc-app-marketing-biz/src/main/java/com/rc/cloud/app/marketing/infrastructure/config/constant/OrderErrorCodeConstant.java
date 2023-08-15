package com.rc.cloud.app.marketing.infrastructure.config.constant;

import com.rc.cloud.common.core.exception.ErrorCode;

/**
 * @ClassName: OrderErrorCodeConstant
 * @Author: liandy
 * @Date: 2023/7/12 09:26
 * @Description:订单错误码
 */
public interface OrderErrorCodeConstant {
    ErrorCode PLACE_ORDER_WHEN_CART_EMPTY = new ErrorCode(111111111, "请选择购物车");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_COMFIRM_ORDER_ID_IS_EMPTY = new ErrorCode(111111111, "确认订单无效，请重新下单");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_ADDRESS_ID_IS_EMPTY = new ErrorCode(111111111, "请选择收货地址");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_TRADE_TYPE_IS_EMPTY = new ErrorCode(111111111, "请交易方式");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_TYPE_IS_EMPTY = new ErrorCode(111111111, "请配送方式");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_COMFIRM_ORDER_IS_NULL = new ErrorCode(111111111, "确认订单已失效，请重新下单");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_ADDRESS_IS_NULL = new ErrorCode(111111111, "收货地址无效，请重新选择收货地址");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_TRADE_TYPE_IS_NULL = new ErrorCode(111111111, "不支持的交易方式");
    ErrorCode SUBMIT_COMFIRM_ORDER_WHEN_DELIVERY_TYPE_IS_NULL  = new ErrorCode(111111111, "不支持的配送方式");
}


