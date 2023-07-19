package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.common.core.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Schema(description = "产品组合项创建request")
public class ProductGroupItemCreateDTO {
    @Schema(description = "产品组合唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "870ef1f5-39d2-4f48-8c67-ae45206")
    @NotBlank(message = "产品组合唯一标识不为空")
    private String productGroupId;
    @Schema(description = "产品唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "产品唯一标识不为空")
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
