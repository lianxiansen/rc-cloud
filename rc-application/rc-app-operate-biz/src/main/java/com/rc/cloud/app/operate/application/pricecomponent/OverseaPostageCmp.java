package com.rc.cloud.app.operate.application.pricecomponent;

import com.rc.cloud.app.operate.domain.model.price.PriceContext;
import com.rc.cloud.app.operate.domain.model.price.PriceStep;
import com.rc.cloud.app.operate.domain.model.price.enums.PriceTypeEnum;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 境外购运费计算组件
 */
@Component("overseaPostageCmp")
public class OverseaPostageCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);

        /**这里Mock境外购运费的策略是：不管多少钱，都要加上15元运费**/
        BigDecimal postage = new BigDecimal(15);
        BigDecimal prePrice = context.getLastestPriceStep().getCurrPrice();
        BigDecimal currPrice = prePrice.add(postage);

        context.addPriceStep(new PriceStep(PriceTypeEnum.OVERSEAS_POSTAGE,
                null,
                prePrice,
                currPrice.subtract(prePrice),
                currPrice,
                PriceTypeEnum.OVERSEAS_POSTAGE.getName()));
    }
}
