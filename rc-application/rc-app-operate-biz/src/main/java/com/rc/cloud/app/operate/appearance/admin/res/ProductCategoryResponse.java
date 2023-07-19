package com.rc.cloud.app.operate.appearance.admin.res;

import com.rc.cloud.app.operate.appearance.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
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
public class ProductCategoryResponse {
    private String Id;
    private String tenantId;

    private String name;

    private String englishName;

    private String icon;

    private String productCategoryPageImage;

    private String productListPageImage;

    private String parentId;

    private boolean enabled;

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
