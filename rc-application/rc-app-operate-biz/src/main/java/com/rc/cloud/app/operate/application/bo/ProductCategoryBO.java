package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductCategoryUpdateRequest
 * @Author: liandy
 * @Date: 2023/7/3 09:26
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class ProductCategoryBO {
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

    public static List<ProductCategoryBO> convertBatch(List<ProductCategory> productCategoryList) {
        List<ProductCategoryBO> boList=new ArrayList<>();
        productCategoryList.forEach(item->{
            boList.add(ProductCategoryBO.convert(item));
        });
        return boList;
    }
    public static ProductCategoryBO convert(ProductCategory productCategory) {
        ProductCategoryBO bo=new ProductCategoryBO();
        bo= new ProductCategoryBO()
                .setId(productCategory.getId().id())
                .setName(productCategory.getChName().value())
                .setEnglishName(productCategory.getEnName().value())
                .setIcon(productCategory.getIcon().getPictureUrl())
                .setEnabled(productCategory.getEnabled().value())
                .setProductCategoryPageImage(productCategory.getPage().getCategoryImage())
                .setProductListPageImage(productCategory.getPage().getListImage())
                .setTenantId(productCategory.getTenantId().id())
                .setSort(productCategory.getSort().getValue());
        if(ObjectUtils.isNotNull(productCategory.getParentId())){
            bo.setParentId(productCategory.getParentId().id());
        }
        return bo;
    }

}
