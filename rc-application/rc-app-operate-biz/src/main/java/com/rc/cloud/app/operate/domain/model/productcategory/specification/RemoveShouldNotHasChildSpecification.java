package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.common.core.domain.AbstractSpecification;

/**
 * @ClassName: RemoveShouldNoChildSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
public class RemoveShouldNotHasChildSpecification extends AbstractSpecification<ProductCategory> {
    private ProductCategoryRepository productCategoryRepository;
    public RemoveShouldNotHasChildSpecification(ProductCategoryRepository productCategoryRepository){
        this.productCategoryRepository=productCategoryRepository;
    }
    @Override
    public boolean isSatisfiedBy(ProductCategory productCategory) {
        return !productCategoryRepository.existsByParentId(productCategory.getId());
    }
}
