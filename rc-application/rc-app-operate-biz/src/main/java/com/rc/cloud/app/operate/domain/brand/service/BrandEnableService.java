package com.rc.cloud.app.operate.domain.brand.service;

import com.rc.cloud.app.operate.domain.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.brand.valobj.BrandId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandEnableService {

    @Autowired
    private BrandRepository brandRepository;

    public void execute(BrandId brandId) {
        BrandEntity brandtEntry = brandRepository.getBrand(brandId);
        brandtEntry.enable();
        brandRepository.saveBrand(brandtEntry);
    }


}
