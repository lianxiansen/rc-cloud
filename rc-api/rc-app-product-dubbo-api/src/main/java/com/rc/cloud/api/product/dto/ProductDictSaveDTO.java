package com.rc.cloud.api.product.dto;

import lombok.Data;

@Data
public class ProductDictSaveDTO {

    private String id;

    private String key;

    private String value;

    private Integer sort;

}
