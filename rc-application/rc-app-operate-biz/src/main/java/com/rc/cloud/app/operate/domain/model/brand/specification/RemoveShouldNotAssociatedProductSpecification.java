package com.rc.cloud.app.operate.domain.model.brand.specification;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.common.core.domain.AbstractSpecification;

/**
 * @ClassName: RemoveShouldNotAssociatedProductSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
public class RemoveShouldNotAssociatedProductSpecification extends AbstractSpecification {
    private ProductRepository productRepository;
    public RemoveShouldNotAssociatedProductSpecification(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @Override
    public boolean isSatisfiedBy(Object o) {
        BrandId brandId = (BrandId) o;
        return !productRepository.existsByBrandId(brandId);
    }
}
