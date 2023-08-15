package com.rc.cloud.app.marketing.infrastructure.repository.convert;

import com.rc.cloud.app.marketing.domain.entity.order.regularorder.RegularOrder;
import com.rc.cloud.app.marketing.infrastructure.repository.po.RegularOrderPO;

/**
 * @ClassName OrderConvert
 * @Author liandy
 * @Date 2023/8/4 14:32
 * @Description 订单转换器 订单<->PO
 * @Version 1.0
 */
public class RegularOrderConvert {
    public static RegularOrderPO convertToPO(RegularOrder order){
        RegularOrderPO po=new RegularOrderPO();
        po.setId(order.getId());
        po.setOrderNumber(order.getOrderNumber());
        po.setOrderStatus(order.getOrderStatus().getValue());
        po.setProductQuantity(order.getProductQuantity());
        po.setProductAmount(order.getProductAmount());
        po.setFreightAmount(order.getFreightAmount());
        po.setPayAmount(order.getPayAmount());
        po.setChangeAmount(order.getChangeAmount());
        po.setTradeType(order.getTradeType().getCode());
        po.setPayTime(order.getPayTime());
        po.setPayStatus(order.getPayStatus().getValue());
        po.setConsignTime(order.getConsignTime());
        po.setConsignStatus(order.getConsignStatus().getValue());
        po.setEndTime(order.getEndTime());
        po.setCloseTime(order.getCloseTime());
        po.setBuyerId(order.getBuyer().getBuyerId());
        po.setBuyerName(order.getBuyer().getBuyerName());
        po.setBuyerOrder(order.getBuyer().getBuyerOrder());
        po.setBuyerAccount(order.getBuyer().getBuyerAccount());
        po.setReceiverContact(order.getReceiver().getReceiverContact());
        po.setReceiverProvince(order.getReceiver().getReceiverProvince());
        po.setReceiverCity(order.getReceiver().getReceiverCity());
        po.setReceiverDistrict(order.getReceiver().getReceiverDistrict());
        po.setReceiverAddressDetail(order.getReceiver().getReceiverAddressDetail());
        po.setReceiverMobile(order.getReceiver().getReceiverMobile());
        po.setRemark(order.getRemark());
        po.setTradeNo(order.getTradeNo());
        return po;
    }
}
