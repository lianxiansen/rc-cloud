package com.rc.cloud.app.mall.appearance.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductEditInfoSpecDTO {

    private Integer spec_id;

    private String spec_name;

    private List<ProductEditInfoSpecValueDTO> spec_values;
}
