package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductImageBO {

    private String url;

    private int sort;

}
