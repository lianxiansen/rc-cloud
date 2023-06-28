package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.operate.domain.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.brand.valobj.BrandId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.BrandDOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import org.springframework.stereotype.Repository;


@Repository
public class BrandRepositoryImpl extends ServiceImpl<BrandMapper, BrandDO> implements BrandRepository, IService<BrandDO> {
    @Override
    public void saveBrand(BrandEntity brandEntity) {
        BrandDO brandDO=BrandDOConvert.toBrandPO(brandEntity);
        this.save(brandDO);
        //this.baseMapper.insert(brandDO);
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
