package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.common.core.domain.AbstractSpecification;
/**
 * @ClassName: RemoveShouldNoProductSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
public class RemoveShouldNotAssociatedProductSpecification extends AbstractSpecification<ProductCategory> {
    private ProductRepository productRepository;
    public RemoveShouldNotAssociatedProductSpecification(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @Override
    public boolean isSatisfiedBy(ProductCategory productCategory) {
        return !productRepository.existsByProductCategoryId(productCategory.getId());
    }



}
