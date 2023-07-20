package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.common.valobj.CreateTime;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.BrandPO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.ArrayList;
import java.util.List;

public class BrandConvert {

    public static BrandPO convert2BrandPO(Brand brand) {
        BrandPO brandPO = new BrandPO();
        brandPO.setId(brand.getId().id());
        brandPO.setName(brand.getName());
        brandPO.setType(brand.getType());
        brandPO.setSort(brand.getSort());
        brandPO.setEnabledFlag(brand.isEnabled());
        brandPO.setLogo(brand.getLogo());
        return brandPO;
    }

    public static Brand convert2Brand(BrandPO brandPO) {
        Brand brand = new Brand(new BrandId(brandPO.getId()), brandPO.getName());
        brand.setType(brandPO.getType());
        brand.setSort(brandPO.getSort());
        if (brandPO.isEnabledFlag()) {
            brand.enable();
        } else {
            brand.disable();
        }
        brand.setCreateTime(new CreateTime(brandPO.getCreateTime()));
        brand.setLogo(brandPO.getLogo());
        return brand;
    }


    public static PageResult<Brand> convert2BrandPageResult(PageResult<BrandPO> brandDOPageResult) {
        List<Brand> brandList = new ArrayList<>();
        brandDOPageResult.getList().forEach(item -> {
            Brand brand = BrandConvert.convert2Brand(item);
            brandList.add(brand);
        });
        PageResult<Brand> brandPageResult = new PageResult<>();
        brandPageResult.setTotal(brandDOPageResult.getTotal());
        brandPageResult.setList(brandList);
        return brandPageResult;
    }
}
