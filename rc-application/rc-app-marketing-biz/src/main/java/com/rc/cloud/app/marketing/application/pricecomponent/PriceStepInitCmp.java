package com.rc.cloud.app.marketing.application.pricecomponent;

import com.rc.cloud.app.marketing.domain.entity.price.PriceContext;
import com.rc.cloud.app.marketing.domain.entity.price.PriceStep;
import com.rc.cloud.app.marketing.domain.entity.price.ProductPack;
import com.rc.cloud.app.marketing.domain.entity.price.enums.PriceTypeEnum;
import com.yomahub.liteflow.core.NodeComponent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 价格步骤初始化器(把原价初始化进去)
 */
@Component("priceStepInitCmp")
public class PriceStepInitCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);

        //初始化价格步骤
        List<ProductPack> packList = context.getProductPackList();
        BigDecimal totalOriginalPrice = new BigDecimal(0);
        for (ProductPack packItem : packList) {
            totalOriginalPrice = totalOriginalPrice.add(packItem.getSalePrice().multiply(new BigDecimal(packItem.getCount())));
        }
        context.addPriceStep(new PriceStep(PriceTypeEnum.ORIGINAL,
                null,
                null,
                totalOriginalPrice,
                totalOriginalPrice,
                PriceTypeEnum.ORIGINAL.getName()));
        context.setOriginalOrderPrice(totalOriginalPrice);
    }

    @Override
    public boolean isAccess() {
        PriceContext context = this.getContextBean(PriceContext.class);
        if (CollectionUtils.isNotEmpty(context.getProductPackList())) {
            return true;
        } else {
            return false;
        }
    }
}
