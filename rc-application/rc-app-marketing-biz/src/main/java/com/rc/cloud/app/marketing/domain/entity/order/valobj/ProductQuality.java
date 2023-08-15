package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import com.rc.cloud.common.core.util.AssertUtils;

import java.util.Objects;

/**
 * @ClassName Quality
 * @Author liandy
 * @Date 2023/8/14 09:54
 * @Description 数量
 * @Version 1.0
 */
public class ProductQuality {
    private int value;

    public ProductQuality(int value) {
        setValue(value);
    }

    private void setValue(int value) {
        AssertUtils.assertArgumentMinimum(value, 0, "数量必须大于0");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductQuality quality = (ProductQuality) o;
        return getValue() == quality.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "Quality{" +
                "value=" + value +
                '}';
    }
}
