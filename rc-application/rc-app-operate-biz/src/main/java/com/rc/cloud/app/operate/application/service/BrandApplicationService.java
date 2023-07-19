package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandService;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.constants.BrandErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageParam;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BrandApplicationService {
    @Autowired
    private BrandService brandService;
    @Autowired
    private IdRepository idRepository;

    public BrandBO create(BrandCreateDTO createBrandDTO) {
        if(StringUtils.isEmpty(createBrandDTO.getName())){
            throw new ServiceException(BrandErrorCodeConstants.NAME_NOT_EMPTY);
        }
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
        brandService.save(brand);
        return BrandBO.convert(brand);
    }


    public BrandBO update(BrandUpdateDTO updateBrandDTO) {
        if (StringUtils.isEmpty(updateBrandDTO.getId())) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        Brand brand = brandService.findById(new BrandId(updateBrandDTO.getId()));
        if (Objects.isNull(brand)) {
            throw new ServiceException(BrandErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        if (Objects.nonNull(updateBrandDTO.getName())) {
            if(StringUtils.isEmpty(updateBrandDTO.getName())){
                throw new ServiceException(BrandErrorCodeConstants.NAME_NOT_EMPTY);
            }
            brand.setName(updateBrandDTO.getName());
        }
        if (Objects.nonNull(updateBrandDTO.getEnabled())) {
            if (updateBrandDTO.getEnabled().booleanValue()) {
                brand.enable();
            } else {
                brand.disable();
            }
        }
        if (Objects.nonNull(updateBrandDTO.getSortId())) {
            brand.setSort(updateBrandDTO.getSortId().intValue());
        }
        if (Objects.nonNull(updateBrandDTO.getType())) {
            brand.setType(updateBrandDTO.getType());
        }
        brandService.save(brand);
        return BrandBO.convert(brand);
    }


    public boolean remove(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        return brandService.remove(new BrandId(id));
    }

    public PageResult<BrandBO> selectPageResult(BrandQueryPageDTO queryBrandDTO) {
        if(null==queryBrandDTO.getPageNo()){
            queryBrandDTO.setPageNo(PageParam.PAGE_NO);
        }
        if(null==queryBrandDTO.getPageSize()){
            queryBrandDTO.setPageSize(PageParam.PAGE_SIZE);
        }
        if(queryBrandDTO.getPageSize().intValue()>PageParam.MAX_PAGE_SIZE){
            queryBrandDTO.setPageSize(PageParam.MAX_PAGE_SIZE);
        }
        PageResult<Brand> brandPageResult = brandService.selectPageResult(queryBrandDTO.getPageNo(), queryBrandDTO.getPageSize(), queryBrandDTO.getName());
        return BrandBO.convertBatch(brandPageResult);
    }

    public BrandBO findById(String id){
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        return BrandBO.convert(brandService.findById(new BrandId(id)));
    }
}


