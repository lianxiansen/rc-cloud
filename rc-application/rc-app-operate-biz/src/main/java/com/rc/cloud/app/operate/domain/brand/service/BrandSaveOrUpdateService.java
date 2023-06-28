package com.rc.cloud.app.operate.domain.brand.service;


import com.rc.cloud.app.operate.domain.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.brand.valobj.Name;
import com.rc.cloud.app.operate.domain.brand.valobj.Sort;
import com.rc.cloud.app.operate.domain.brand.valobj.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandSaveOrUpdateService {



    @Autowired
    private BrandRepository brandRepository;


    public void execute(BrandId brandId, Name brandName, Type brandType , Sort sort) {
        BrandEntity brandEntity= brandRepository.getBrand(brandId);
        brandEntity.setName(brandName);
        brandEntity.setType(brandType);
        brandEntity.setSort(sort);
        brandRepository.saveBrand(brandEntity);
    }
}
