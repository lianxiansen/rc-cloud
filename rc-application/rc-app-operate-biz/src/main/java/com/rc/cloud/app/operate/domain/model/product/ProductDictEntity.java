package com.rc.cloud.app.operate.domain.model.product;

import com.baomidou.mybatisplus.annotation.TableField;

public class ProductDictEntity {

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
}
