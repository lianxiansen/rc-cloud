package com.rc.cloud.app.marketing.application.pricecomponent;

import com.rc.cloud.app.marketing.domain.entity.price.PriceCalParam;
import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * Slot初始化组件
 */
@Component("slotInitCmp")
public class SlotInitCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        //把主要参数冗余到slot里
        PriceCalParam req = this.getRequestData();
        PriceContext context = this.getContextBean(PriceContext.class);
        context.setOrderNo(req.getOrderNo());
        context.setOversea(req.isOversea());
        context.setMemberCode(req.getMemberCode());
        context.setOrderChannel(req.getOrderChannel());
        context.setProductPackList(req.getProductPackList());
        context.setCouponId(req.getCouponId());
    }

    @Override
    public boolean isAccess() {
        PriceCalParam req = this.getSlot().getRequestData();
        if (req != null) {
            return true;
        } else {
            return false;
        }
    }
}
