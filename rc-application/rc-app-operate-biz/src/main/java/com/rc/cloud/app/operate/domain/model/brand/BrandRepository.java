package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.common.core.pojo.PageResult;

public interface BrandRepository {
    boolean save(Brand brandEntity);

    Brand findById(BrandId brandId);

    boolean exists(BrandId brandId);

    BrandId nextId();

    boolean removeById(BrandId brandId);

    PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name);
}
