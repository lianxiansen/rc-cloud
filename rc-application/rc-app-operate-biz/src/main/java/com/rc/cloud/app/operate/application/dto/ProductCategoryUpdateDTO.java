package com.rc.cloud.app.operate.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: ProductCategoryUpdateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: 产品分类更新request
 */
@Data
@Accessors(chain = true)
@Schema(description = "产品分类更新request")
public class ProductCategoryUpdateDTO {
    @Schema(description = "产品分类唯一标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "72f7ae9e-2ff8-45aa-b61b-59ee900")
    @NotBlank(message = "产品分类唯一标识不为空")
    private String id;
    @Schema(description = "产品分类名称")
    private String name;
    @Schema(description = "产品分类英文名称", example = "fash")
    private String englishName;
    @Schema(description = "图标图片url")
    private String icon;
    @Schema(description = "商品分类页面图片URL")
    private String productCategoryPageImage;
    @Schema(description = "商品列表页面图片URL")
    private String productListPageImage;
    @Schema(description = "父级产品分类唯一标识")
    private String parentId;
    @Schema(description = "状态，是否启用")
    private Boolean enabled;
    @Schema(description = "排序")
    private Integer sort;


}
