package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: RemoveShouldNoChildSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
@Component
public class RemoveShouldExistsSpecification extends AbstractSpecification {
    @Resource
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public boolean isSatisfiedBy(Object o) {
        ProductCategoryId productCategoryId= (ProductCategoryId) o;
        if(ObjectUtils.isNull(productCategoryRepository.findById(productCategoryId))){
            return false;
        };
        return true;
    }
}
