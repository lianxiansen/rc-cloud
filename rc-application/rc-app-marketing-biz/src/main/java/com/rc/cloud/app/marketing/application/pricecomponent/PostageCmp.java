package com.rc.cloud.app.marketing.application.pricecomponent;

import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.domain.entity.price.PriceStep;
import com.rc.cloud.app.marketing.domain.entity.price.enums.PriceTypeEnum;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 国内运费计算组件
 */
@Component("postageCmp")
public class PostageCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);

        /**这里Mock运费的策略是：满99免运费，不满99需要10块钱运费**/
        BigDecimal triggerPrice = new BigDecimal(99);
        BigDecimal postage = new BigDecimal(10);
        //先把运费加上去
        BigDecimal prePrice = context.getLastestPriceStep().getCurrPrice();
        BigDecimal currPrice = prePrice.add(postage);

        context.addPriceStep(new PriceStep(PriceTypeEnum.POSTAGE,
                null,
                prePrice,
                currPrice.subtract(prePrice),
                currPrice,
                PriceTypeEnum.POSTAGE.getName()));

        //判断运费是否满99了，满了99就去掉运费
        if(prePrice.compareTo(triggerPrice) >= 0){
            prePrice = context.getLastestPriceStep().getCurrPrice();
            currPrice = currPrice.subtract(postage);

            context.addPriceStep(new PriceStep(PriceTypeEnum.POSTAGE_FREE,
                    null,
                    prePrice,
                    currPrice.subtract(prePrice),
                    currPrice,
                    PriceTypeEnum.POSTAGE_FREE.getName()));
        }
    }
}
