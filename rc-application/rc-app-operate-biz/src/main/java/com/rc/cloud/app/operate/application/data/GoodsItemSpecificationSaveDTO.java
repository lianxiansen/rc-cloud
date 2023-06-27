package com.rc.cloud.app.operate.application.data;

import lombok.Data;

import java.util.List;

@Data
public class GoodsItemSpecificationSaveDTO {

    private Integer specId;

    private String specName;

    private Integer specSortId;

    private List<GoodsItemSpecificationValueSaveDTO> specValues;
}
