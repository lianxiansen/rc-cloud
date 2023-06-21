package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsSkuSaveDTO {

    private Integer id;

    private Integer wareHouseItemID;

    private String specificationCombinationID;

    private String specificationCombinationName;

    private List<GoodsSkuSpecKVPSaveDTO> fromAlibabaSpecKeyValuePairs;

    private String fromAlibabaSkuId;

    private String fromAlibabaSpecId;

    private String cargoNumber;

    private String cargoCode;

    private BigDecimal price;

    private BigDecimal supplyPrice;

    private BigDecimal weight;

    private BigDecimal partnerPrice;

    private Integer inventory;

    private String imageUrl;
}
