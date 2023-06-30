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
 * @Description: TODO
 */
@Component
public class ProductCategoryBuilderFactory {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public ProductCategoryBuilder create(ProductCategoryId id, TenantId tenantId, Name name){
        return new ProductCategoryBuilder();
    }

    public class ProductCategoryBuilder {
        private ProductCategoryId id;
        private TenantId tenantId;
        private Name name;
        private Icon icon;
        private Page page;
        private Layer layer;
        private ProductCategoryId parentId;
        private ProductCategoryAggregation parentCategory;
        private Enabled enabled;
        private Sort sort;

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
                this.parentId = parentId;
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
            productCategoryAggregation.extendsFromParent(parentCategory);
            return productCategoryAggregation;
        }
    }


}
