package com.rc.cloud.app.operate.application.pricecomponent;

import cn.hutool.core.collection.ListUtil;
import com.rc.cloud.app.operate.domain.model.price.PriceContext;
import com.rc.cloud.app.operate.domain.model.price.ProductPack;
import com.rc.cloud.app.operate.domain.model.price.PromotionInfo;
import com.rc.cloud.app.operate.domain.model.price.PromotionPack;
import com.yomahub.liteflow.core.NodeComponent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 把商品包的优惠信息转换成以优惠信息为主要维度的对象，以便于后面优惠信息的计算
 */
@Component("promotionConvertCmp")
public class PromotionConvertCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        PriceContext context = this.getContextBean(PriceContext.class);
        List<PromotionPack> promotionPackList = new ArrayList<>();

        PromotionPack promotionPack = null;
        for (ProductPack pack : context.getProductPackList()) {
            if (CollectionUtils.isEmpty(pack.getPromotionList())) {
                continue;
            }
            for (PromotionInfo promotion : pack.getPromotionList()) {
                promotionPack = new PromotionPack();
                promotionPack.setId(promotion.getId());
                if (promotionPackList.contains(promotionPack)) {
                    promotionPack = promotionPackList.get(promotionPackList.indexOf(promotionPack));
                    if (promotionPack.getRelatedProductPackList().contains(pack)) {
                        continue;
                    } else {
                        promotionPack.getRelatedProductPackList().add(pack);
                    }
                } else {
                    BeanUtils.copyProperties(promotion, promotionPack);
                    promotionPack.setRelatedProductPackList(ListUtil.toList(pack));
                    promotionPackList.add(promotionPack);
                }
            }
        }
        context.setPromotionPackList(promotionPackList);
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
