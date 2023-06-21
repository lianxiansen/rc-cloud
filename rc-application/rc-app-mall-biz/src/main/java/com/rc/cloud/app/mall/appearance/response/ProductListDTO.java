package com.rc.cloud.app.mall.appearance.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductListDTO {

    private Integer page_count;

    private Long total_count;

    private List<ProductListItemDTO> list;
}
