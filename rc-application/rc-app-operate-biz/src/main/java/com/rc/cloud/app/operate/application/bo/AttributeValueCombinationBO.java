package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AttributeValueCombinationBO {

    private String attribute;

    private String attributeValue;

    private  int sort;

}
