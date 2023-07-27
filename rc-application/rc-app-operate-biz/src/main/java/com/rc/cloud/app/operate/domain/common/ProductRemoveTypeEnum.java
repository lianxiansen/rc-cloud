package com.rc.cloud.app.operate.domain.common;

public enum ProductRemoveTypeEnum {

    DELETE(0, "删除"),
    SOFT_DELETE(1,"软删除");

    public final Integer value;
    public final String name;

    ProductRemoveTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
