package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.rc.cloud.app.operate.domain.model.product.valobj.AttributeValue;
import lombok.Data;
import lombok.experimental.Accessors;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.SortedSet;

@Data
@Accessors(chain = true)
public class AttributeBO {

    private String attribute;

    private List<AttributeValueBO> values;

    private int sort;


}
