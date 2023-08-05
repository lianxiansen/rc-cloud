package com.rc.cloud.app.operate.appearance.admin.req;

import com.rc.cloud.app.operate.application.dto.ProductSkuAttributeSaveDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
public class ProductSkuSaveRequest {

    @Schema(description = "skuid")
    private String id;

    @Schema(description = "skucode")
    private String skuCode;

    @Schema(description = "价格")
    @DecimalMin(value = "0.01", message = "请输入sku价格(范围：不小于0.01)，单位为元")
    private String price;

    @Schema(description = "供应价")
    private String supplyPrice;

    @Schema(description = "重量")
    @DecimalMin(value = "0.01", message = "sku重量必须填写(范围：不小于0.01，单位为g)")
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
    @Min(value = 0, message = "库存(范围：不小于0)")
    private Integer inventory;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值最小为0")
    @Max(value = 99, message = "排序值最大为99")
    private Integer sort;

    @Schema(description = "装箱数")
    @Min(value = 1, message = "装箱数(范围：不小于1)")
    private Integer packingNumber;

    @Schema(description = "箱规-长")
    @Min(value = 0, message = "箱规长(范围：不小于0)")
    private Integer cartonSizeLength;

    @Schema(description = "箱规-宽")
    @Min(value = 0, message = "箱规宽(范围：不小于0)")
    private Integer cartonSizeWidth;

    @Schema(description = "箱规-高")
    @Min(value = 0, message = "箱规高(范围：不小于0)")
    private Integer cartonSizeHeight;




}
