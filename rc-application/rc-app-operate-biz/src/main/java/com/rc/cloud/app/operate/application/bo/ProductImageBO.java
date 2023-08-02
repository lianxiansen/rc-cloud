package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductImageBO {

    private String id;

    private String url;

    private int sort;

    private ProductImageTypeEnum type;

}
