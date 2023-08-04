package com.rc.cloud.app.operate.appearance.admin.req;

import com.rc.cloud.app.operate.application.dto.ProductSkuAttributeSaveDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import java.util.List;

@Data
public class ProductSkuSaveRequest {

    @Schema(description = "skuid")
    private String id;

    @Schema(description = "skucode")
    private String skuCode;

    @Schema(description = "价格")
    @DecimalMin(value = "0.01", message = "supplyPrice不支持(范围：不小于0.01)")
    private String price;

    @Schema(description = "供应价")
    @DecimalMin(value = "0.01", message = "supplyPrice不支持(范围：不小于0.01)")
    private String supplyPrice;

    @Schema(description = "重量")
    @DecimalMin(value = "0.01", message = "supplyPrice不支持(范围：不小于0.01)")
    private String weight;

    @Schema(description = "启用状态")
    private Boolean enabledFlag;

    @Schema(description = "图片")
    private List<ProductSkuImageSaveRequest> albums;

    /**
     * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
     */
    @Schema(description = "规格")
    private List<ProductSkuAttributeSaveRequest> attributes;

    @Schema(description = "库存")
    private Integer inventory;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "装箱数")
    private Integer packingNumber;

    @Schema(description = "箱规-长")
    private Integer cartonSizeLength;

    @Schema(description = "箱规-宽")
    private Integer cartonSizeWidth;

    @Schema(description = "箱规-高")
    private Integer cartonSizeHeight;




}
