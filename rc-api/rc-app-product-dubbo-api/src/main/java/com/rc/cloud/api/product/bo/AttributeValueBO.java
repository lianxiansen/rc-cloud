package com.rc.cloud.api.product.bo;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AttributeValueBO {

    private String attributeValue;

    private Integer sort;
}
