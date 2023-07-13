package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.valobj.EnName;

/**
 * @ClassName: ProductCategoryFactory
 * @Author: liandy
 * @Date: 2023/7/12 17:06
 * @Description: TODO
 */
public class ProductCategoryRebuildFactory {
    public ProductCategoryRebuilder create() {
        return new ProductCategoryRebuilder();
    }

    public class ProductCategoryRebuilder{
        private ProductCategory productCategory;

        public ProductCategoryRebuildFactory.ProductCategoryRebuilder setEnName(EnName enName){
            productCategory.setEnName(enName);
            return this;
        }
        public ProductCategory rebuld(){
            ProductCategory productCategory=new ProductCategory(null,null,null);
            return productCategory;
        }
    }


}
