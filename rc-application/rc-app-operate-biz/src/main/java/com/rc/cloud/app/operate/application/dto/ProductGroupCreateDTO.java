package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.common.core.util.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ProductGroupCreateDTO {
    @NotNull(message = "产品唯一标识不能为空")
    private String productId;
    @NotNull(message = "组合名称不能为空")
    private String name;

    public boolean sameValueAs(ProductGroup productGroup) {
        boolean sameValue=true;
        if (StringUtils.isNotEmpty(productId)) {
            sameValue=sameValue&&productId.equals(productGroup.getProductId().id());
        }
        if (StringUtils.isNotEmpty(name)) {
            sameValue=sameValue&&name.equals(productGroup.getName());
        }
        return sameValue;
    }

    public boolean sameValueAs(ProductGroupBO productGroup) {
        boolean sameValue=true;
        if (StringUtils.isNotEmpty(productId)) {
            sameValue=sameValue&&productId.equals(productGroup.getProductId());
        }
        if (StringUtils.isNotEmpty(name)) {
            sameValue=sameValue&&name.equals(productGroup.getName());
        }
        return sameValue;
    }
}
