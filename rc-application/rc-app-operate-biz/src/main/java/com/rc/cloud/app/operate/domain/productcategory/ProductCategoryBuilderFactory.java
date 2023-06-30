package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

/**
 * @ClassName: ProductCategoryFactory
 * @Author: liandy
 * @Date: 2023/6/30 13:41
 * @Description: 产品类目工厂
 */
@Component
public class ProductCategoryBuilderFactory {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    public ProductCategoryBuilder create(ProductCategoryId id, TenantId tenantId, Name name){
        return new ProductCategoryBuilder(id,tenantId,name);
    }

    /**
     * 产品类目构造器
     */
    public class ProductCategoryBuilder {
        private ProductCategoryId id;
        private TenantId tenantId;
        private Name name;
        private Icon icon;
        private Page page;
        private Layer layer;
        private ProductCategoryAggregation parentCategory;
        private Enabled enabled;
        private Sort sort;
        private ProductCategoryBuilder(ProductCategoryId id,TenantId tenantId,Name name){
            this.id = id;
            this.tenantId=tenantId;
            this.name=name;
        }
        public ProductCategoryBuilder icon(Icon icon){
            this.icon=icon;
            return this;
        }
        public ProductCategoryBuilder page(Page page){
            this.page = page;
            return this;
        }

        public ProductCategoryBuilder parentId(ProductCategoryId parentId){
            if (null != parentId) {
                parentCategory = productCategoryRepository.findById(new ProductCategoryId(parentId.id()));
                notNull(parentCategory, "父级商品分类id无效");
            }
            return this;
        }
        public ProductCategoryBuilder enabled( Enabled enabled){
            this.enabled=enabled;
            return this;
        }

        public ProductCategoryBuilder sort(Sort sort){
            this.sort = sort;
            return this;
        }
        public ProductCategoryAggregation build(){
            ProductCategoryAggregation productCategoryAggregation=new ProductCategoryAggregation(this.id,this.tenantId,this.name);
            productCategoryAggregation.setIcon(this.icon);
            productCategoryAggregation.setPage(this.page);
            productCategoryAggregation.setSort(this.sort);
            productCategoryAggregation.setEnabled(this.enabled);
            productCategoryAggregation.extendsFromParent(this.parentCategory);
            return productCategoryAggregation;
        }
    }


}
