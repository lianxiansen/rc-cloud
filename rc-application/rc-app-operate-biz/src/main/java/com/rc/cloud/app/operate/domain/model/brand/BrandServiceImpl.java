package com.rc.cloud.app.operate.domain.model.brand;

import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand findById(BrandId brandId){
        AssertUtils.notNull(brandId,"brandId must be not null");
        Brand brand = brandRepository.findById(brandId);
        return brand;
    }
    @Override
    public void enable(Brand brand) {
        AssertUtils.notNull(brand,"brand must be not null");
        brand.enable();
        brandRepository.save(brand);
    }
    @Override
    public void disable(Brand brand) {
        AssertUtils.notNull(brand,"brand must be not null");
        brand.disable();
        brandRepository.save(brand);
    }

    @Override
    public boolean save(Brand brand){
        AssertUtils.notNull(brand,"brand must be not null");
        return brandRepository.save(brand);
    }


    @Override
    public boolean remove(Brand brand){
        AssertUtils.notNull(brand,"brand must be not null");
        return brandRepository.removeById(brand.getId());
    }

    @Override
    public PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name) {
        AssertUtils.notNull(pageNo,"pageNo must be not null");
        AssertUtils.notNull(pageNo,"pageSize must be not null");
        return brandRepository.selectPageResult(pageNo,pageSize,name);
    }
}
