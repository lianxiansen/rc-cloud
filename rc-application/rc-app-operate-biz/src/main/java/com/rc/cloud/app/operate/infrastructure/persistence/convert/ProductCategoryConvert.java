package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryPO;

import java.util.Objects;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductCategoryConvert {

    public static ProductCategoryPO convert2ProductCategoryDO(ProductCategory source) {
        if(Objects.isNull(source)){
            return null;
        }
        ProductCategoryPO target=new ProductCategoryPO();
        target.setId(source.getId().id());
        target.setProductCategoryPageImage(source.getPage().getCategoryImage());
        target.setProductListPageImage(source.getPage().getListImage());
        target.setLayer(source.getLayer().getValue());
        target.setEnabledFlag(source.getEnabled().value());
        target.setIcon(source.getIcon().getPictureUrl());
        target.setName(source.getChName().value());
        target.setEnglishName(source.getEnName().value());
        target.setParentId(source.getParentId()==null?null:source.getParentId().id());
        target.setSortId(source.getSort().getValue());
        target.setTenantId(source.getTenantId().id());
        return target;
    }

    public static  ProductCategory convert2ProductCategory(ProductCategoryPO productCategoryPO){
        if(Objects.isNull(productCategoryPO)){
            return null;
        }
        ProductCategoryId id = new ProductCategoryId(productCategoryPO.getId());
        TenantId tenantId = new TenantId(productCategoryPO.getTenantId());
        ChName name = new ChName(productCategoryPO.getName());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(productCategoryPO.getEnglishName()));
        productCategory.setIcon(new Icon(productCategoryPO.getIcon()));
        productCategory.setEnabled(new Enabled(productCategoryPO.getEnabledFlag()));
        productCategory.setPage(new Page(productCategoryPO.getProductCategoryPageImage(), productCategoryPO.getProductListPageImage()));
        productCategory.setSort(new Sort(productCategoryPO.getSortId()));
        productCategory.setLayer(new Layer(productCategoryPO.getLayer()));
        if(null!= productCategoryPO.getParentId()){
            productCategory.setParentId(new ProductCategoryId(productCategoryPO.getParentId()));
        }
        return productCategory;
    }

}
