package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.rc.cloud.app.operate.domain.model.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.BrandConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BrandRepositoryImpl implements BrandRepository{

    @Autowired
    private  BrandMapper brandMapper;
    @Override
    public void saveBrand(BrandEntity brandEntity) {
        BrandDO brandDO= BrandConvert.convert2BrandPO(brandEntity);
        brandMapper.insert(brandDO);
    }

    @Override
    public BrandEntity getBrand(BrandId brandId) {
        return null;
    }

    @Override
    public BrandId nextBrandId() {
        return null;
    }
}
