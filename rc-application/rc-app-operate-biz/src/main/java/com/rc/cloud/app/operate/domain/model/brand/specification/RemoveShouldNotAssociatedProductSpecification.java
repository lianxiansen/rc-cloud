package com.rc.cloud.app.operate.domain.model.brand.specification;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: RemoveShouldNotAssociatedProductSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
@Component
public class RemoveShouldNotAssociatedProductSpecification extends AbstractSpecification {
    @Resource
    private ProductRepository productRepository;

    @Override
    public boolean isSatisfiedBy(Object o) {
        BrandId brandId = (BrandId) o;
        return productRepository.existsByBrandId(brandId);
    }
}
