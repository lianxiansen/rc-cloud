package com.rc.cloud.app.marketing.domain.entity.price;

import cn.hutool.core.collection.ListUtil;
import com.rc.cloud.app.marketing.domain.entity.price.enums.CategoryEnum;
import com.rc.cloud.app.marketing.domain.entity.price.enums.OrderChannelEnum;
import com.rc.cloud.app.marketing.domain.entity.price.enums.PromotionTypeEnum;
import com.rc.cloud.app.marketing.domain.entity.price.enums.SkuSourceEnum;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-26 9:27
 * @description 计算价格
 */
@Service
public class PriceServiceImpl implements PriceService {
    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public PriceContext calPrice(PriceCalParam req) {
        LiteflowResponse response = flowExecutor.execute2Resp("cartChain", req, PriceContext.class);
        return response.getContextBean(PriceContext.class);
    }

    private PriceCalParam mockReq() {
        PriceCalParam req = new PriceCalParam();
        req.setOrderNo("SO2020070611120001");
        req.setOversea(false);
        req.setMemberCode("M21152");
        req.setOrderChannel(OrderChannelEnum.APP);
        req.setCouponId(80081L);
        List<ProductPack> productPackList = new ArrayList<>();
        req.setProductPackList(productPackList);

        ProductPack productPack = new ProductPack();
        productPack.setProductId(5001L);
        productPack.setProductCode("PD5001XC");
        productPack.setSkuId("67001441");
        productPack.setSkuCode("SKU5001XC001");
        productPack.setSkuName("夏季运动女式短裙M");
        productPack.setSkuSource(SkuSourceEnum.RAW);
        productPack.setCategory(CategoryEnum.CLOTHES);
        productPack.setSalePrice(new BigDecimal("139.00"));
        productPack.setCount(2);
        productPack.setPromotionList(ListUtil.toList(
                new PromotionInfo(1001L, "PM1001", "夏季满减活动", PromotionTypeEnum.FULL_CUT),
                new PromotionInfo(1002L, "PM1002", "夏季满折活动", PromotionTypeEnum.FULL_DISCOUNT)));
        productPackList.add(productPack);

        productPack = new ProductPack();
        productPack.setProductId(6001L);
        productPack.setProductCode("PD6001XC");
        productPack.setSkuId("67002334");
        productPack.setSkuCode("SKU6001XC001");
        productPack.setSkuName("男士迷彩短袜均码");
        productPack.setSkuSource(SkuSourceEnum.RAW);
        productPack.setCategory(CategoryEnum.CLOTHES);
        productPack.setSalePrice(new BigDecimal("59.00"));
        productPack.setCount(3);
        productPack.setPromotionList(ListUtil.toList(
                new PromotionInfo(1001L, "PM1001", "夏季满减活动", PromotionTypeEnum.FULL_CUT)));
        productPackList.add(productPack);

        productPack = new ProductPack();
        productPack.setProductId(8001L);
        productPack.setProductCode("PD8001XC");
        productPack.setSkuId("87002001");
        productPack.setSkuCode("SKU8001XC001");
        productPack.setSkuName("纯棉毛巾");
        productPack.setSkuSource(SkuSourceEnum.RAW);
        productPack.setCategory(CategoryEnum.DAILY_USE);
        productPack.setSalePrice(new BigDecimal("28.00"));
        productPack.setCount(5);
        productPack.setPromotionList(ListUtil.toList(
                new PromotionInfo(1002L, "PM1002", "夏季满折活动", PromotionTypeEnum.FULL_DISCOUNT)));
        productPackList.add(productPack);

        productPack = new ProductPack();
        productPack.setProductId(9001L);
        productPack.setProductCode("PD9001XC");
        productPack.setSkuId("97552001");
        productPack.setSkuCode("SKU9001XC001");
        productPack.setSkuName("杀菌护手凝胶");
        productPack.setSkuSource(SkuSourceEnum.RAW);
        productPack.setCategory(CategoryEnum.DAILY_USE);
        productPack.setSalePrice(new BigDecimal("30"));
        productPack.setCount(2);
        productPack.setPromotionList(ListUtil.toList(
                new PromotionInfo(1003L, "PM1003", "618抢购活动", PromotionTypeEnum.RUSH_BUY)));
        productPackList.add(productPack);

        return req;
    }

}