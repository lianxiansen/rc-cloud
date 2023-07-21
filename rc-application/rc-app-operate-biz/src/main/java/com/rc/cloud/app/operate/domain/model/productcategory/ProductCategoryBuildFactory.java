package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.time.LocalDateTime;

/**
 * @ClassName: ProductCategoryFactory
 * @Author: liandy
 * @Date: 2023/7/12 17:06
 * @Description: TODO
 */
public class ProductCategoryBuildFactory {
    public static ProductCategoryBuilder create(ProductCategoryId id, TenantId tenantId, ChName name) {
        return new ProductCategoryBuilder(id,tenantId,name);
    }

    public static class ProductCategoryBuilder {
        private ProductCategory productCategory;
        public ProductCategoryBuilder(ProductCategoryId id, TenantId tenantId, ChName name){
            productCategory=new ProductCategory(id,tenantId,name);
        }
        public ProductCategoryBuilder enName(EnName enName){
            productCategory.setEnName(enName);
            return this;
        }

        public ProductCategoryBuilder icon(Icon icon){
            productCategory.setIcon(icon);
            return this;
        }
        public ProductCategoryBuilder page(Page page){
            productCategory.setPage(page);
            return this;
        }

        public ProductCategoryBuilder layer(Layer layer){
            productCategory.setLayer(layer);
            return this;
        }
        public ProductCategoryBuilder parentId(ProductCategoryId parentId){
            productCategory.setParentId(parentId);
            return this;
        }

        public ProductCategoryBuilder setEnabled(Enabled enabled) {
            if(enabled.isTrue()){
                productCategory.enable();
            }else{
                productCategory.disable();
            }
            return this;
        }
        public ProductCategoryBuilder sort(Sort sort){
            productCategory.setSort(sort);
            return this;
        }
        ProductCategory build(){
            productCategory.setCreateTime(new CreateTime(LocalDateTime.now()));
            return productCategory;
        }
    }


}
