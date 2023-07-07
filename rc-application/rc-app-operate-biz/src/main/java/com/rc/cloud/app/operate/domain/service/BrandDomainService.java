package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandDomainService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    public void enable(BrandId brandId) {
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brandtEntry = brandRepository.getBrand(brandId);
        brandtEntry.enable();
        brandRepository.saveBrand(brandtEntry);
    }
    public void disable(BrandId brandId) {
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brandtEntry = brandRepository.getBrand(brandId);
        brandtEntry.disable();
        brandRepository.saveBrand(brandtEntry);
    }


    public boolean removeBrand(BrandId brandId){
        AssertUtils.notNull(brandId,"brandId must be not null");
        if(productRepository.existsByBrandId(brandId)){
            throw new DomainException("已关联产品,删除失败");
        }
        return brandRepository.removeById(brandId);
    }

}
