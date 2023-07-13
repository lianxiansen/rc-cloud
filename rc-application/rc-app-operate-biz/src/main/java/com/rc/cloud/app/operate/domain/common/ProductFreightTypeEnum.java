package com.rc.cloud.app.operate.domain.common;

public enum ProductFreightTypeEnum  {


    Fixed(0, "固定"),
    Template(1,"运费模板");

    public final Integer value;
    public final String name;

    ProductFreightTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
