package com.rc.cloud.app.marketing.application.pricecomponent;

import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.domain.entity.price.PriceStep;
import com.rc.cloud.app.marketing.domain.entity.price.enums.PriceTypeEnum;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 优惠券抵扣计算组件
 */
@Component("couponCmp")
public class CouponCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);

        /**这里Mock下根据couponId取到的优惠卷面值为15元**/
        Long couponId = context.getCouponId();
        BigDecimal couponPrice = new BigDecimal(15);

        BigDecimal prePrice = context.getLastestPriceStep().getCurrPrice();
        BigDecimal currPrice = prePrice.subtract(couponPrice);

        context.addPriceStep(new PriceStep(PriceTypeEnum.COUPON_DISCOUNT,
                couponId.toString(),
                prePrice,
                currPrice.subtract(prePrice),
                currPrice,
                PriceTypeEnum.COUPON_DISCOUNT.getName()));
    }

    @Override
    public boolean isAccess() {
        PriceContext context = this.getContextBean(PriceContext.class);
        if (context.getCouponId() != null) {
            return true;
        } else {
            return false;
        }
    }
}
