package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.infrastructure.constants.BrandErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandDomainService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    public Brand findById(BrandId brandId){
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brand = brandRepository.findById(brandId);
        return brand;
    }
    public void enable(BrandId brandId) {
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brandtEntry = brandRepository.findById(brandId);
        brandtEntry.enable();
        brandRepository.save(brandtEntry);
    }
    public void disable(BrandId brandId) {
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brandtEntry = brandRepository.findById(brandId);
        brandtEntry.disable();
        brandRepository.save(brandtEntry);
    }

    public boolean save(Brand brand){
        AssertUtils.notNull(brand,"brand must be not null");
        return brandRepository.save(brand);
    }


    public boolean remove(Brand brand){
        AssertUtils.notNull(brand,"brand must be not null");
        if(productRepository.existsByBrandId(brand.getId())){
            throw new ServiceException(BrandErrorCodeConstants.REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT);
        }
        return brandRepository.removeById(brand.getId());
    }

    public PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name) {
        AssertUtils.notNull(pageNo,"pageNo must be not null");
        AssertUtils.notNull(pageNo,"pageSize must be not null");
        return brandRepository.selectPageResult(pageNo,pageSize,name);
    }
}
