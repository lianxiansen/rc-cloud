package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: RemoveShouldNoProductSpecification
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
        ProductCategoryId productCategoryId = (ProductCategoryId) o;
        return !productRepository.existsByProductCategoryId(productCategoryId);
    }
}
