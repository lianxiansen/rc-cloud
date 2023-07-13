package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.valobj.EnName;

/**
 * @ClassName: ProductCategoryFactory
 * @Author: liandy
 * @Date: 2023/7/12 17:06
 * @Description: TODO
 */
public class ProductCategoryBuildFactory {
    public ProductCategorybuilder create() {
        return new ProductCategorybuilder();
    }

    public class ProductCategorybuilder{
        private ProductCategory productCategory;
        public ProductCategorybuilder setEnName(EnName enName){
            productCategory.setEnName(enName);
            return this;
        }

        public ProductCategory build(){
            ProductCategory productCategory=  new ProductCategory(null,null,null);
            //TODO 触发
            return productCategory;
        }
    }


}
