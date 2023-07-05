package com.rc.cloud.app.operate.application.dto;

/**
 * "attributes":[
 *     {"name":"颜色","value":"红","sort":9},
 *     {"name":"颜色","value":"黄","sort":9},
 *     {"name":"颜色","value":"蓝","sort":9},
 *     {"name":"尺寸","value":"X","sort":9},
 *     {"name":"尺寸","value":"XL","sort":9}
 * ]
 */
public class ProductAttributeSaveDTO {

    private String name;

    private String value;

    private int sort;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
