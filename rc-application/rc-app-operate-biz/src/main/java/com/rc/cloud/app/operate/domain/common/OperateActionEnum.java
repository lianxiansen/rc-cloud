package com.rc.cloud.app.operate.domain.common;

public enum OperateActionEnum {


    ADD(0, "ADD"),
    EDIT(1,"EDIT");

    public final Integer value;
    public final String name;

    OperateActionEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
