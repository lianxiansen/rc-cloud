package com.rc.cloud.app.operate.domain.common;

public enum ProductDictKeyEnum {


    CaiZhi(0, "材质"),
    TiaoMa(1,"条码"),
    ChengZhong(2,"承重"),
    ChiCun(3,"尺寸");

    public final Integer value;
    public final String name;


    ProductDictKeyEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
