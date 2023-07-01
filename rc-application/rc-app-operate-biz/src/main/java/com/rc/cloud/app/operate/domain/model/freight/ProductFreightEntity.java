package com.rc.cloud.app.operate.domain.model.freight;

import java.math.BigDecimal;

public class ProductFreightEntity {



    /**
     * 运费类型
     * 1.统一运费
     * 2.运费模板
     */
    private String freightType;

    /**
     * 商品使用运费模板
     */
    private String freightTemplateId;


    /**
     * 运费模板
     *
     */
    //private ProductFreightTemplate productFreightTemplate;
    /**
     * 运费价格
     */
    private BigDecimal freightPrice;


//    public ProductFreight(BigDecimal freightPrice) {
//        Validate.isTrue(freightPrice.compareTo(BigDecimal.valueOf(0)) < 0,"请输入正确的统一运费！");
//        this.freightType = ProductFreightTypeEnum.Fixed.value.toString();
//        this.freightPrice = freightPrice;
//    }
//
//
//    public ProductFreight(String freightTemplateId) {
//        Validate.notEmpty(freightTemplateId,"运费模板不能为空！");
//        this.freightType = ProductFreightTypeEnum.Template.value.toString();
//        this.freightTemplateId = freightTemplateId;
//    }


}
