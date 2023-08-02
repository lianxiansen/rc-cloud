package com.rc.cloud.api.product.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AttributeValueCombinationBO {

    private String attribute;

    private String attributeValue;

    private  int sort;

}
