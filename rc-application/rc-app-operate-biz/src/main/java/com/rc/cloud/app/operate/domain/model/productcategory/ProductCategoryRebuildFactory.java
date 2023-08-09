package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.common.core.annotation.Factory;

/**
 * @ClassName: ProductCategoryRebuildFactory
 * @Author: liandy
 * @Date: 2023/7/12 17:06
 * @Description: 产品分类重建工厂
 */
@Factory
public class ProductCategoryRebuildFactory {
    public ProductCategoryRebuilder create(ProductCategoryId id, ChName name, CreateTime createTime) {
        return new ProductCategoryRebuilder(id, name, createTime);
    }

    public ProductCategoryRebuilder create(ProductCategory productCategory) {
        return new ProductCategoryRebuilder(productCategory);
    }

    public class ProductCategoryRebuilder {
        private ProductCategory productCategory;

        public ProductCategoryRebuilder(ProductCategory productCategory) {
            this.productCategory = productCategory;
        }

        public ProductCategoryRebuilder(ProductCategoryId id, ChName name, CreateTime createTime) {
            productCategory = new ProductCategory(id, name);
            productCategory.setCreateTime(createTime);
        }

        public ProductCategoryRebuilder chName(ChName enName) {
            productCategory.setChName(enName);
            return this;
        }

        public ProductCategoryRebuilder enName(EnName enName) {
            productCategory.setEnName(enName);
            return this;
        }

        public ProductCategoryRebuilder icon(Icon icon) {
            productCategory.setIcon(icon);
            return this;
        }

        public ProductCategoryRebuilder page(Page page) {
            productCategory.setPage(page);
            return this;
        }

        public ProductCategoryRebuilder layer(Layer layer) {
            productCategory.setLayer(layer);
            return this;
        }

        public ProductCategoryRebuilder parentId(ProductCategoryId parentId) {
            productCategory.setParentId(parentId);
            return this;
        }


        public ProductCategoryRebuilder sort(Sort sort) {
            productCategory.setSort(sort);
            return this;
        }

        public ProductCategoryRebuilder setEnabled(Enabled enabled) {
            if (enabled.isTrue()) {
                productCategory.enable();
            } else {
                productCategory.disable();
            }
            return this;
        }


        public ProductCategory rebuild() {
            return productCategory;
        }


    }


}
