package com.rc.cloud.app.marketing.domain.common;

public enum ProductImageTypeEnum {

    MasterImage(0, "主图"),
    SizeImage(1, "尺寸图片");

    public final Integer value;
    public final String name;

    ProductImageTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
