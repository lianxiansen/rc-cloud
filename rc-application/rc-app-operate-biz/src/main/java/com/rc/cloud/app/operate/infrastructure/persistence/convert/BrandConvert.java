package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandPO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.ArrayList;
import java.util.List;

public  class BrandConvert {

    public static BrandPO convert2BrandPO(Brand brandEntity){
        BrandPO brandPO =new BrandPO();
        brandPO.setId(brandEntity.getId().id());
        brandPO.setName(brandEntity.getName());
        brandPO.setType(brandEntity.getType());
        brandPO.setSortId(brandEntity.getSort());
        brandPO.setEnabledFlag(brandEntity.isEnable());
        return brandPO;
    }

    public static Brand  convert2Brand( BrandPO brandPO){
        Brand brand=new Brand(new BrandId(brandPO.getId()), brandPO.getName());
        brand.setType(brandPO.getType());
        brand.setSort(brandPO.getSortId());
        if(brandPO.isEnabledFlag()){
            brand.enable();
        }else {
            brand.disable();
        }
        return brand;
    }


    public static PageResult<Brand> convert2BrandPageResult(PageResult<BrandPO> brandDOPageResult){
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
