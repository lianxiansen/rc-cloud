package com.rc.cloud.app.operate.domain.model.productdict;


import com.rc.cloud.app.operate.domain.model.product.identifier.ProductDictId;
import com.rc.cloud.common.core.domain.Entity;

public class ProductDict  extends Entity {


    private ProductDictId id;
    public ProductDict(ProductDictId id) {
            this.id = id;
        }

    private String key;

    private String value;

    private int sort;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public ProductDictId getId() {
        return id;
    }

}
