package com.rc.cloud.app.operate.domain.model.brand.service;

import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BrandDisableService {


    @Autowired
    private BrandRepository brandRepository;

    public void execute(BrandId brandId) {
        BrandEntity brandtEntry = brandRepository.getBrand(brandId);
        brandtEntry.disable();
        brandRepository.saveBrand(brandtEntry);
    }


}
