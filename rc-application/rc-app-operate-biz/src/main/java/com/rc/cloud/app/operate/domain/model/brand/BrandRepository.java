package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;

public interface BrandRepository {
    void saveBrand(BrandEntity brandEntity);

    BrandEntity getBrand(BrandId brandId);

    BrandId nextBrandId();
}
