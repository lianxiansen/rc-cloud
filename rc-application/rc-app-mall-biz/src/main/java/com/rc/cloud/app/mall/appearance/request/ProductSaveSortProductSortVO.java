package com.rc.cloud.app.mall.appearance.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductSaveSortProductSortVO {

    @Min(value = 1, message = "pid不支持(范围：不小于1)")
    private int pid;

    @NotNull(message = "请设置排序数字")
    private Integer sort;
}
