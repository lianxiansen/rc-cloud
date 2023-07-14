package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.pojo.PageResult;

public interface BrandRepository {
    boolean save(Brand brandEntity);

    Brand findById(BrandId brandId);


    boolean removeById(BrandId brandId);

    PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name);
}
