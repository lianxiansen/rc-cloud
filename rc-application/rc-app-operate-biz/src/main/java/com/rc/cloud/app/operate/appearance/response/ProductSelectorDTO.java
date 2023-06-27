package com.rc.cloud.app.operate.appearance.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductSelectorDTO {

    private Integer page_count;

    private Long total_count;

    private List<ProductSelectorProductDTO> list;
}
