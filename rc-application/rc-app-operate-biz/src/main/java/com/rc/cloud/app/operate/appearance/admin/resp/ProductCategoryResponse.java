package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.common.core.util.date.LocalDateTimeUtils;
import com.rc.cloud.common.core.util.tree.TreeNode;
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
public class ProductCategoryResponse extends TreeNode {
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
    @Schema(description = "创建时间")
    private String createTime;

    public static ProductCategoryResponse from(ProductCategoryBO productCategory){
        ProductCategoryResponse response= ProductCategoryConvert.INSTANCE.convert2ProductCategoryVO(productCategory);
        response.setCreateTime(LocalDateTimeUtils.format(productCategory.getCreateTime()));
        return response;
    }


    public static List<ProductCategoryResponse> from(List<ProductCategoryBO> productCategoryList){
        List<ProductCategoryResponse> list=new ArrayList<ProductCategoryResponse>();
        productCategoryList.forEach(item->{
            list.add(from(item));
        });
        return list;
    }

}
