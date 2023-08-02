package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AttributeValueCombinationBO {

    private String attribute;

    private String attributeValue;

    private  int sort;

}
