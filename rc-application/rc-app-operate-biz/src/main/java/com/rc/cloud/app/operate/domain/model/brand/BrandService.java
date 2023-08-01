package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.pojo.PageResult;

public interface BrandService {
    Brand findById(BrandId brandId);

    void enable(Brand brand);

    void disable(Brand brand);

    boolean save(Brand brand);

    boolean remove(Brand brand);

    PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name);
}
