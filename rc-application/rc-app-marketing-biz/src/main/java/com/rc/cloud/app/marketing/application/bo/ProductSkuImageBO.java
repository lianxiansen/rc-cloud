package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductSkuImageBO {

    private String url;

    private int sort;

}
