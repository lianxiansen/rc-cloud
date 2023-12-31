package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.application.bo.convert.BrandConvert;
import com.rc.cloud.app.operate.application.dto.BrandCreateDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryDTO;
import com.rc.cloud.app.operate.application.dto.BrandQueryPageDTO;
import com.rc.cloud.app.operate.application.dto.BrandUpdateDTO;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandService;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.infrastructure.constants.BrandErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.pojo.PageParam;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
/**
 * @ClassName BrandApplicationServiceImpl
 * @Author liandy
 * @Date 2023/7/24 9:10
 * @Description  品牌应用服务类
 * @Version 1.0
 */
@Service
public class BrandApplicationServiceImpl implements BrandApplicationService {
    @Autowired
    private BrandService brandService;
    @Autowired
    private IdRepository idRepository;
    @Override
    public BrandBO create(BrandCreateDTO createBrandDTO) {
        if(StringUtils.isEmpty(createBrandDTO.getName())){
            throw new ServiceException(BrandErrorCodeConstants.NAME_NOT_EMPTY);
        }
        Brand brand = buildBrand(createBrandDTO);
        brandService.save(brand);
        return BrandConvert.convert(brand);
    }


    @Override
    public BrandBO update(BrandUpdateDTO updateBrandDTO) {
        if (StringUtils.isEmpty(updateBrandDTO.getId())) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        Brand brand = findBrand(new BrandId(updateBrandDTO.getId()));
        rebuild(updateBrandDTO, brand);
        brandService.save(brand);
        return BrandConvert.convert(brand);
    }

    @Override
    public boolean remove(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        Brand brand = findBrand(new BrandId(id));
        return brandService.remove(brand);
    }

    @Override
    public PageResult<BrandBO> selectPageResult(BrandQueryPageDTO queryBrandDTO) {
        initialize(queryBrandDTO);
        PageResult<Brand> brandPageResult = brandService.selectPageResult(queryBrandDTO.getPageNo(), queryBrandDTO.getPageSize(), queryBrandDTO.getName());
        return BrandConvert.convert2PageResult(brandPageResult);
    }



    @Override
    public BrandBO findById(String id){
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(BrandErrorCodeConstants.ID_NOT_EMPTY);
        }
        return BrandConvert.convert(brandService.findById(new BrandId(id)));
    }

    @Override
    public List<BrandBO> findList(BrandQueryDTO brandQuery) {
        List<Brand> brands=brandService.findList(brandQuery.getName());
        return BrandConvert.convertBatch(brands);
    }

    /**
     * 创建品牌
     * @param createBrandDTO
     * @return
     */
    private Brand buildBrand(BrandCreateDTO createBrandDTO) {
        Brand brand = new Brand(new BrandId(idRepository.nextId()), createBrandDTO.getName());
        if(StringUtils.isNotEmpty(createBrandDTO.getLogo())){
            brand.setLogo(createBrandDTO.getLogo());
        }
        if (Objects.nonNull(createBrandDTO.getEnabled())) {
            if (createBrandDTO.getEnabled().booleanValue()) {
                brand.enable();
            } else {
                brand.disable();
            }
        }

        if (Objects.nonNull(createBrandDTO.getSort())) {
            brand.setSort(createBrandDTO.getSort().intValue());
        }
        if (StringUtils.isNotEmpty(createBrandDTO.getType())) {
            brand.setType(createBrandDTO.getType());
        }
        return brand;
    }

    /**
     * 重新构建品牌
     * @param updateBrandDTO
     * @param brand
     */
    private void rebuild(BrandUpdateDTO updateBrandDTO, Brand brand) {
        if (Objects.nonNull(updateBrandDTO.getName())) {
            if(StringUtils.isEmpty(updateBrandDTO.getName())){
                throw new ServiceException(BrandErrorCodeConstants.NAME_NOT_EMPTY);
            }
            brand.setName(updateBrandDTO.getName());
        }
        if (Objects.nonNull(updateBrandDTO.getLogo())) {
            brand.setLogo(updateBrandDTO.getLogo());
        }
        if (Objects.nonNull(updateBrandDTO.getEnabled())) {
            if (updateBrandDTO.getEnabled().booleanValue()) {
                brand.enable();
            } else {
                brand.disable();
            }
        }
        if (Objects.nonNull(updateBrandDTO.getSort())) {
            brand.setSort(updateBrandDTO.getSort().intValue());
        }
        if (Objects.nonNull(updateBrandDTO.getType())) {
            brand.setType(updateBrandDTO.getType());
        }
    }
    private void initialize(BrandQueryPageDTO queryBrandDTO) {
        if(null== queryBrandDTO.getPageNo()){
            queryBrandDTO.setPageNo(PageParam.PAGE_NO);
        }
        if(null== queryBrandDTO.getPageSize()){
            queryBrandDTO.setPageSize(PageParam.PAGE_SIZE);
        }
        if(queryBrandDTO.getPageSize().intValue()>PageParam.MAX_PAGE_SIZE){
            queryBrandDTO.setPageSize(PageParam.MAX_PAGE_SIZE);
        }
    }

    private Brand findBrand(BrandId brandId) {
        Brand brand = brandService.findById(brandId);
        if (Objects.isNull(brand)) {
            throw new ServiceException(BrandErrorCodeConstants.ID_INVALID);
        }
        return brand;
    }
}


