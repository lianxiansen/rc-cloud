package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandDomainService;
import com.rc.cloud.app.operate.domain.model.brand.BrandRepository;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BrandApplicationService {
    @Autowired
    private BrandDomainService brandDomainService;
    @Autowired
    private BrandRepository brandRepository;
    @Resource
    private IdRepository idRepository;

    public BrandBO createBrand(BrandCreateDTO createBrandDTO) {
        AssertUtils.notNull(createBrandDTO, "createBrandDTO must be not null");
        Brand brand = new Brand(new BrandId(idRepository.nextId()), createBrandDTO.getName());
        if (ObjectUtils.isNotNull(createBrandDTO.getEnabled())) {
            if (createBrandDTO.getEnabled().booleanValue()) {
                brand.enable();
            } else {
                brand.disable();
            }
        }
        if (ObjectUtils.isNotNull(createBrandDTO.getSortId())) {
            brand.setSort(createBrandDTO.getSortId().intValue());
        }
        if (StringUtils.isNotEmpty(createBrandDTO.getType())) {
            brand.setType(createBrandDTO.getType());
        }
        brandRepository.save(brand);
        return BrandBO.convert(brand);
    }

    public boolean changeState(String id) {
        AssertUtils.notEmpty(id, "id must be not empty");
        Brand brand = brandRepository.findById(new BrandId(id));
        if (ObjectUtils.isNull(brand)) {
            throw new ApplicationException("品牌唯一标识无效");
        }
        if (brand.isEnable()) {
            brand.disable();
        } else {
            brand.enable();
        }
        return brandRepository.save(brand);
    }


    public BrandBO updateBrand(BrandUpdateDTO updateBrandDTO) {
        AssertUtils.notNull(updateBrandDTO, "updateBrandDTO must be not null");
        if (ObjectUtils.isNull(updateBrandDTO.getId())) {
            throw new ApplicationException("品牌唯一标识不为空");
        }
        Brand brand = brandRepository.findById(new BrandId(updateBrandDTO.getId()));
        if (ObjectUtils.isNull(brand)) {
            throw new ApplicationException("品牌唯一标识无效");
        }
        if (StringUtils.isNotEmpty(updateBrandDTO.getName())) {
            brand.setName(updateBrandDTO.getName());
        }
        if (ObjectUtils.isNotNull(updateBrandDTO.getEnabled())) {
            if (updateBrandDTO.getEnabled().booleanValue()) {
                brand.enable();
            } else {
                brand.disable();
            }
        }
        if (ObjectUtils.isNotNull(updateBrandDTO.getSortId())) {
            brand.setSort(updateBrandDTO.getSortId().intValue());
        }
        if (StringUtils.isNotEmpty(updateBrandDTO.getType())) {
            brand.setType(updateBrandDTO.getType());
        }
        brandRepository.save(brand);
        return BrandBO.convert(brand);
    }

    public boolean remove(String id) {
        AssertUtils.notEmpty(id, "id must be not empty");
        return brandDomainService.removeBrand(new BrandId(id));
    }

    public PageResult<BrandBO> selectPageResult(BrandQueryPageDTO queryBrandDTO) {
        AssertUtils.notNull(queryBrandDTO, "queryBrandDTO must be not null");
        PageResult<Brand> brandPageResult = brandRepository.selectPageResult(queryBrandDTO.getPageNo(), queryBrandDTO.getPageSize(), queryBrandDTO.getName());
        return BrandBO.convertBatch(brandPageResult);
    }
}


