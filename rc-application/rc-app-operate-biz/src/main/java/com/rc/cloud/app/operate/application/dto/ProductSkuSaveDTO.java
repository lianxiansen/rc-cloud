package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductSkuSaveDTO {

    private String id;

    private String productId;

    private String tenantId;

    private String skuCode;

    private String price;

    private String supplyPrice;

    private String weight;

    private Boolean enabledFlag;

    private List<ProductSkuImageSaveDTO> albums;


    private String attributeId;

    /**
     * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
     */
    private List<ProductSkuAttributeSaveDTO> attributes;

    private Integer inventory;

    private Integer sort;


}
