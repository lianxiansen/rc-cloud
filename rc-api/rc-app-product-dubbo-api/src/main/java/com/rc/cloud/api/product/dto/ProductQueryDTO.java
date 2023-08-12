package com.rc.cloud.api.product.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ProductQueryDTO {


    private String productId;

    private boolean needProductMasterImage;

    private boolean needProductSizeImage;

    private boolean needProductDetail;

    private boolean needProductDict;

    private boolean needProductSku;

}
