package com.rc.cloud.app.mall.infrastructure.persistence.convert;

import com.rc.cloud.app.mall.domain.category.entity.ProductCategoryEntry;
import com.rc.cloud.app.mall.domain.category.valobj.ProductCategoryId;
import com.rc.cloud.app.mall.domain.product.entity.ProductEntry;
import com.rc.cloud.app.mall.domain.product.valobj.ProductId;
import com.rc.cloud.app.mall.domain.product.valobj.ProductType;
import com.rc.cloud.app.mall.infrastructure.persistence.po.Product;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ProductCategory;

/**
 * @ClassName: ProductCategoryConvert
 * @Author: liandy
 * @Date: 2023/6/23 14:16
 * @Description: TODO
 */
public class ProductConvert {
    public ProductConvert(){
    }

    public ProductEntry convertToProductEntry(Product product){
        return new ProductEntry(new ProductId( product.getId()),new ProductType(product.getProductType()));
    }

    public Product convertToProduct(ProductEntry productEntry){
        return null;
    }
}
