package com.rc.cloud.app.operate.domain.model.brand.specification;

import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RemoveShouldExistsSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
@Component
public class RemoveShouldExistsSpecification extends AbstractSpecification {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean isSatisfiedBy(Object o) {
        BrandId brandId= (BrandId) o;
        if(ObjectUtils.isNull(brandRepository.findById(brandId))){
            return false;
        };
        return true;
    }
}
