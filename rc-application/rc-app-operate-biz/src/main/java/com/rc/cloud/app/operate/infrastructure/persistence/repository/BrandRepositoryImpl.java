package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.BrandConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.BrandMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandPO;
import com.rc.cloud.common.core.pojo.PageParam;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class BrandRepositoryImpl implements BrandRepository {

    @Autowired
    private BrandMapper brandMapper;
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public boolean save(Brand brand) {
        BrandPO brandPO = BrandConvert.convert2BrandPO(brand);
        return brandMapper.insert(brandPO) > 0;
    }

    @Override
    public Brand findById(BrandId brandId) {
        return null;
    }

    @Override
    public boolean exists(BrandId brandId) {
        return brandMapper.exists(null);
    }

    @Override
    public BrandId nextId() {
        return new BrandId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public boolean removeById(BrandId brandId) {
        return brandMapper.deleteById(brandId.id().toString()) > 0;
    }
    @Override
    public PageResult<Brand> selectPageResult(Integer pageNo, Integer pageSize, String name) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(pageNo);
        pageParam.setPageSize(pageSize);
        LambdaQueryWrapperX<BrandPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(BrandPO::getName, name);
        PageResult<BrandPO> brandDOPageResult = brandMapper.selectPage(pageParam, wrapper);
        return BrandConvert.convert2BrandPageResult(brandDOPageResult);
    }
}
