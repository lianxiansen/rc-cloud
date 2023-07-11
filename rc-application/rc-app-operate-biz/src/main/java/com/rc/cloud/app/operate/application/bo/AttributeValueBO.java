package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AttributeValueBO {

    private String attributeValue;

    private Integer sort;
}
