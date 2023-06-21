package com.rc.cloud.app.mall.infrastructure.util;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/9
 * @Description: 拷贝bean的策略
 */
public enum CopyBeanStrategyEnum {

    fromModel(1, "字段首字母小写"),
    formVo(2, "字段字母小写且下划线间隔");

    public final Integer value;
    public final String name;

    CopyBeanStrategyEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
