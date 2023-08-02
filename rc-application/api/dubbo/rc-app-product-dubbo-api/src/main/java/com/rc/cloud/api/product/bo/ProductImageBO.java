package com.rc.cloud.api.product.bo;

import com.rc.cloud.api.product.ProductImageTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductImageBO {

    private String url;

    private int sort;

    private ProductImageTypeEnum type;

}
