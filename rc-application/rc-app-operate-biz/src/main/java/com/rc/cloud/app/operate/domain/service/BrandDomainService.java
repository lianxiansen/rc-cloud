package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
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


    public boolean removeBrand(BrandId brandId){
        AssertUtils.notNull(brandId,"brandId must be not null");
        if(productRepository.existsByBrandId(brandId)){
            throw new DomainException("已关联产品,删除失败");
        }
        Brand brand = brandRepository.findById(brandId);
        if (ObjectUtils.isNull(brand)) {
            throw new DomainException("品牌唯一标识无效");
        }
        return brandRepository.removeById(brandId);
    }
}
