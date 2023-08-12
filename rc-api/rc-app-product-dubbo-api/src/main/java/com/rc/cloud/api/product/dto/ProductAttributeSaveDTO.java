package com.rc.cloud.api.product.dto;

import lombok.Data;

/**
 * "attributes":[
 *     {"name":"颜色","value":"红","sort":9},
 *     {"name":"颜色","value":"黄","sort":9},
 *     {"name":"颜色","value":"蓝","sort":9},
 *     {"name":"尺寸","value":"X","sort":9},
 *     {"name":"尺寸","value":"XL","sort":9}
 * ]
 */
@Data
public class ProductAttributeSaveDTO {

    private String name;

    private String value;

    private Integer sort;

}
