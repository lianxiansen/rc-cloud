package com.rc.cloud.app.operate.appearance.admin.res;

import com.rc.cloud.app.operate.appearance.admin.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductCategoryUpdateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: TODO
 */
@Data
@Schema(description = "产品分类response")
public class ProductCategoryResponse {
    @Schema(description = "产品分类唯一标识")
    private String Id;
    private String tenantId;
    @Schema(description = "产品分类名称")
    private String name;
    @Schema(description = "产品分类英文名称")
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
    private boolean enabled;
    @Schema(description = "排序")
    private int sort;

    public static ProductCategoryResponse from(ProductCategoryBO productCategory){
        return ProductCategoryConvert.INSTANCE.convert2ProductCategoryVO(productCategory);
    }


    public static List<ProductCategoryResponse> from(List<ProductCategoryBO> productCategoryList){
        List<ProductCategoryResponse> list=new ArrayList<ProductCategoryResponse>();
        productCategoryList.forEach(item->{
            list.add(from(item));
        });
        return list;
    }

}
