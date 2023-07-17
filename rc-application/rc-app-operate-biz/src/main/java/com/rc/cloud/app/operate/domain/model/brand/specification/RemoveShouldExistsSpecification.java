package com.rc.cloud.app.operate.domain.model.brand.specification;

import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import com.rc.cloud.common.core.util.object.ObjectUtils;

/**
 * @ClassName: RemoveShouldExistsSpecification
 * @Author: liandy
 * @Date: 2023/7/12 08:37
 * @Description: TODO
 */
public class RemoveShouldExistsSpecification extends AbstractSpecification {
    private BrandRepository brandRepository;
    public RemoveShouldExistsSpecification(BrandRepository brandRepository){
        this.brandRepository=brandRepository;
    }

    @Override
    public boolean isSatisfiedBy(Object o) {
        BrandId brandId= (BrandId) o;
        if(ObjectUtils.isNull(brandRepository.findById(brandId))){
            return false;
        };
        return true;
    }
}
