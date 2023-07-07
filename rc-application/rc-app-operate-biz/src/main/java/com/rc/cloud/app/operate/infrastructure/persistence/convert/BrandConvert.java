package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.ArrayList;
import java.util.List;

public  class BrandConvert {

    public static BrandDO convert2BrandPO(Brand brandEntity){
        BrandDO brandDO=new BrandDO();
        brandDO.setId(brandEntity.getId().id());
        brandDO.setName(brandEntity.getName());
        brandDO.setType(brandEntity.getType());
        brandDO.setSortId(brandEntity.getSort());
        brandDO.setEnabledFlag(brandEntity.isEnable());
        return brandDO;
    }

    public static Brand  convert2Brand( BrandDO brandDO){
        Brand brand=new Brand(new BrandId(brandDO.getId()),brandDO.getName());
        brand.setType(brandDO.getType());
        brand.setSort(brandDO.getSortId());
        if(brandDO.isEnabledFlag()){
            brand.enable();
        }else {
            brand.disable();
        }
        return brand;
    }


    public static PageResult<Brand> convert2BrandPageResult(PageResult<BrandDO> brandDOPageResult){
        List<Brand> brandList=new ArrayList<>();
        brandDOPageResult.getList().forEach(item->{
            Brand brand=BrandConvert.convert2Brand(item);
            brandList.add(brand);
        });
        PageResult<Brand> brandPageResult=new PageResult<>();
        brandPageResult.setTotal(brandDOPageResult.getTotal());
        brandPageResult.setList(brandList);
        return brandPageResult;
    }
}
