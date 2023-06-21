package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import java.util.List;

@Data
public class GoodsItemSpecificationSaveDTO {

    private Integer specId;

    private String specName;

    private Integer specSortId;

    private List<GoodsItemSpecificationValueSaveDTO> specValues;
}
