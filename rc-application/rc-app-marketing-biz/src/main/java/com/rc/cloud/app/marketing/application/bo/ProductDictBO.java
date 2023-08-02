package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDictBO {


    private String key;

    private String value;

    private int sort;

}
