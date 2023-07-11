package com.rc.cloud.app.operate.appearance.vo;

import com.rc.cloud.app.operate.appearance.assemble.ProductCategoryAssemble;
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
public class ProductCategoryVO {
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

    public static ProductCategoryVO from(ProductCategoryBO productCategory){
        return ProductCategoryAssemble.INSTANCE.convert2ProductCategoryVO(productCategory);
    }


    public static List<ProductCategoryVO> from(List<ProductCategoryBO> productCategoryList){
        List<ProductCategoryVO> list=new ArrayList<ProductCategoryVO>();
        productCategoryList.forEach(item->{
            list.add(from(item));
        });
        return list;
    }

}
