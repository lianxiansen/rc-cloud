package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductNoDeliveryTemplateDTO {

    private Integer id;

    private String name;

    private BigDecimal price;

    private String productCategoryIds;

    private String productCategoryNames;

    private String masterImage;

    private Integer noDeliveryTemplateId;
}
