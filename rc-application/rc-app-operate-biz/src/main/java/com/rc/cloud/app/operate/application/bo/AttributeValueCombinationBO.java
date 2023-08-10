package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class AttributeValueCombinationBO {

    private String attribute;

    private String attributeValue;

    private  int sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeValueCombinationBO that = (AttributeValueCombinationBO) o;
        return Objects.equals(getAttribute(), that.getAttribute()) && Objects.equals(getAttributeValue(), that.getAttributeValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttribute(), getAttributeValue());
    }
}
