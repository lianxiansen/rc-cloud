package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.common.core.util.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class ProductGroupItemCreateDTO {
    @NotNull(message = "产品组合唯一标识")
    private String productGroupId;
    @NotNull(message = "产品唯一标识")
    private String productId;
    public boolean sameValueAs(ProductGroupItem productGroupItem) {
        boolean sameValue=true;
        if(!Objects.isNull(productGroupId)){
            sameValue=sameValue&&productGroupId.equals(productGroupItem.getProductGroupId().id());
        }
        if (StringUtils.isNotEmpty(productId)) {
            sameValue=sameValue&&productId.equals(productGroupItem.getProductId().id());
        }
        return sameValue;
    }

}
