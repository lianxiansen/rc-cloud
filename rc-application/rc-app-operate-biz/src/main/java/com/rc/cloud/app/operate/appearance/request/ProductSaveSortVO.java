package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductSaveSortVO {

    @Valid
    @NotNull(message = "请设置要保存排序的商品")
    @Size(min = 1, message = "请设置要保存排序的商品")
    private List<ProductSaveSortProductSortVO> list;
}
