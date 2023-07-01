package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.BrandEntity;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;

public  class BrandConvert {

    public static BrandDO toBrandPO(BrandEntity brandEntity){
        BrandDO brandDO=new BrandDO();
        brandDO.setId(brandEntity.getId()==null? 0 : brandEntity.getId().getValue());
        brandDO.setName(brandEntity.getName()==null? null : brandEntity.getName().getValue());
        brandDO.setType(brandEntity.getType()==null? null : brandEntity.getType().getValue());
        brandDO.setSortId(brandEntity.getSort()==null? 99 : brandEntity.getSort().getValue());
        brandDO.setEnabledFlag(brandEntity.getEnable()==null? null : brandEntity.getEnable().result());
        return brandDO;
    }
}
