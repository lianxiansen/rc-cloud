package com.rc.cloud.app.marketing.application.bo;

import com.rc.cloud.app.marketing.domain.common.ProductImageTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductImageBO {

    private String url;

    private int sort;

    private ProductImageTypeEnum type;

}
