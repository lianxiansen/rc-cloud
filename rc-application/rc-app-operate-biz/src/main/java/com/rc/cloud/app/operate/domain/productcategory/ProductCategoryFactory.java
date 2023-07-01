package com.rc.cloud.app.operate.domain.productcategory;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.service.FindProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ProductCategoryFactory
 * @Author: liandy
 * @Date: 2023/6/30 13:41
 * @Description: 产品类目工厂
 */
@Component
public class ProductCategoryFactory {

    @Autowired
    private FindProductCategoryDomainService findProductCategoryDomainService;


    public ProductCategoryBuilder builder(ProductCategoryId id, TenantId tenantId, Name name) {
        return new ProductCategoryBuilder(id, tenantId, name);
    }
    public ProductCategoryReBuilder reBuilder(ProductCategoryId id, TenantId tenantId, Name name) {
        return new ProductCategoryReBuilder(id, tenantId, name);
    }

    /**
     * 产品类目构造器
     */
    public class ProductCategoryBuilder {

        private ProductCategoryAggregation productCategoryAggregation;
        private ProductCategoryBuilder(ProductCategoryId id, TenantId tenantId, Name name) {
            productCategoryAggregation=new ProductCategoryAggregation(id,tenantId,name);
        }

        public ProductCategoryBuilder icon(Icon icon) {
            productCategoryAggregation.setIcon(icon);
            return this;
        }

        public ProductCategoryBuilder page(Page page) {
            productCategoryAggregation.setPage(page);
            return this;
        }

        public ProductCategoryBuilder parentId(ProductCategoryId parentId) {
            productCategoryAggregation.setParentId(parentId);
            return this;
        }

        public ProductCategoryBuilder enabled(Enabled enabled) {
            productCategoryAggregation.setEnabled(enabled);
            return this;
        }

        public ProductCategoryBuilder sort(Sort sort) {
            productCategoryAggregation.setSort(sort);
            return this;
        }

        public ProductCategoryAggregation build() {
            ProductCategoryAggregation parentCategory=null;
            if (null != productCategoryAggregation.getParentId()) {
                parentCategory = findProductCategoryDomainService.findById(productCategoryAggregation.getParentId());
                throw new DomainException("父级商品分类无效："+productCategoryAggregation.getParentId().id());
            }
            productCategoryAggregation.extendsFromParent(productCategoryAggregation);
            return productCategoryAggregation;
        }
    }

    /**
     * 产品类目重构器：用于从数据库重建，假设数据库中的数据时干净的，因此不需要对数据进行校验。
     */
    public class ProductCategoryReBuilder {
        private ProductCategoryAggregation productCategoryAggregation;

        private ProductCategoryReBuilder(ProductCategoryId id, TenantId tenantId, Name name) {
            productCategoryAggregation=new ProductCategoryAggregation(id,tenantId,name);
        }

        public ProductCategoryReBuilder icon(Icon icon) {
            productCategoryAggregation.setIcon(icon);
            return this;
        }

        public ProductCategoryReBuilder page(Page page) {
            productCategoryAggregation.setPage(page);
            return this;
        }

        public ProductCategoryReBuilder parentId(ProductCategoryId parentId) {
            productCategoryAggregation.setParentId(parentId);
            return this;
        }

        public ProductCategoryReBuilder enabled(Enabled enabled) {
            productCategoryAggregation.setEnabled(enabled);
            return this;
        }

        public ProductCategoryReBuilder sort(Sort sort) {
            productCategoryAggregation.setSort(sort);
            return this;
        }
        public ProductCategoryAggregation rebuild() {
            return productCategoryAggregation;
        }
    }

}
