package com.rc.cloud.app.operate.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
