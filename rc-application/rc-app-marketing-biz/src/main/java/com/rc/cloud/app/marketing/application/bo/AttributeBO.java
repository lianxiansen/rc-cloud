package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AttributeBO {

    private String attribute;

    private List<AttributeValueBO> values;

    private int sort;


}
