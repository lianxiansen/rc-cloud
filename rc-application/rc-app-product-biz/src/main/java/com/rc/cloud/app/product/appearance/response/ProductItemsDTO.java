package com.rc.cloud.app.product.appearance.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductItemsDTO {

    private List<ProductItemsItemInfoDTO> list;
}
