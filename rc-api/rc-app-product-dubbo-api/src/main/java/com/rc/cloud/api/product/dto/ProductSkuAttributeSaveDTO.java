package com.rc.cloud.api.product.dto;


import lombok.Data;

@Data
public class ProductSkuAttributeSaveDTO {

    private String name;

    private String value;

    private Integer sort;

}
