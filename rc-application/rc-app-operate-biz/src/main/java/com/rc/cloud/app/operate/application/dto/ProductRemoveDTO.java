package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.app.operate.domain.common.ProductRemoveTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ProductRemoveDTO {

    public ProductRemoveDTO(){
        removeType=ProductRemoveTypeEnum.SOFT_DELETE;
    }
    private List<String> productIds;

    private ProductRemoveTypeEnum removeType;

}
