package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.application.service.ProductCategoryApplicationService;
import com.rc.cloud.app.operate.domain.common.valobj.Enabled;
import com.rc.cloud.app.operate.domain.common.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.*;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.ChName;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.EnName;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Icon;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Page;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ErrorCodeConstants;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName ProductCategoryApplicationService
 * @Author liandy
 * @Date 2023/7/24 9:08
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ProductCategoryApplicationServiceImpl implements ProductCategoryApplicationService {
    @Resource
    private ProductCategoryDomainService productCategoryService;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ProductCategoryBuildFactory productCategoryBuildFactory;
    @Autowired
    private ProductCategoryRebuildFactory productCategoryRebuildFactory;
    @Resource
    private ProductRepository productRepository;

    @Override
    public ProductCategoryBO create(ProductCategoryCreateDTO productCreateCategoryDTO) {
        if (StringUtils.isEmpty(productCreateCategoryDTO.getName())) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.NAME_NOT_EMPTY);
        }
        TenantId tenantId = new TenantId(TenantContext.getTenantId());
        ChName name = new ChName(productCreateCategoryDTO.getName());
        ProductCategoryBuildFactory.ProductCategoryBuilder builder = productCategoryBuildFactory.create(new ProductCategoryId(idRepository.nextId()), tenantId, name);
        builder.enName(new EnName(productCreateCategoryDTO.getEnglishName()));
        builder.icon(new Icon(productCreateCategoryDTO.getIcon()));
        if (Objects.nonNull(productCreateCategoryDTO.getEnabled())) {
            builder.setEnabled(new Enabled(productCreateCategoryDTO.getEnabled()));
        }
        builder.page(new Page(productCreateCategoryDTO.getProductCategoryPageImage(), productCreateCategoryDTO.getProductListPageImage()));
        if (Objects.nonNull(productCreateCategoryDTO.getSort())) {
            builder.sort(new Sort(productCreateCategoryDTO.getSort()));
        }
        if (!StringUtils.isEmpty(productCreateCategoryDTO.getParentId())) {
            ProductCategoryId parentId = new ProductCategoryId(productCreateCategoryDTO.getParentId());
            builder.parentId(parentId);
        }
        ProductCategory productCategory = builder.build();
        productCategoryService.create(productCategory);
        return ProductCategoryBO.convert(productCategory);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryBO update(ProductCategoryUpdateDTO productCategoryUpdateDTO) {
        if (StringUtils.isEmpty(productCategoryUpdateDTO.getId())) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductCategory productCategory = productCategoryService.findById(new ProductCategoryId(productCategoryUpdateDTO.getId()));
        if (Objects.isNull(productCategory)) {
            throw new ServiceException(ErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        ProductCategoryRebuildFactory.ProductCategoryRebuilder rebuilder = productCategoryRebuildFactory.create(productCategory);
        if (Objects.nonNull(productCategoryUpdateDTO.getParentId())) {
            if (StringUtils.isEmpty(productCategoryUpdateDTO.getParentId())) {
                rebuilder.parentId(null);
            } else {
                rebuilder.parentId(new ProductCategoryId(productCategoryUpdateDTO.getParentId()));
            }
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getName())) {
            rebuilder.chName(new ChName(productCategoryUpdateDTO.getName()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getEnglishName())) {
            rebuilder.enName(new EnName(productCategoryUpdateDTO.getEnglishName()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getIcon())) {
            rebuilder.icon(new Icon(productCategoryUpdateDTO.getIcon()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getProductCategoryPageImage())) {
            rebuilder.page(new Page(productCategoryUpdateDTO.getProductCategoryPageImage(), productCategory.getPage().getListImage()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getProductListPageImage())) {
            rebuilder.page(new Page(productCategory.getPage().getCategoryImage(), productCategoryUpdateDTO.getProductListPageImage()));
        }
        if (Objects.nonNull(productCategoryUpdateDTO.getSort())) {
            rebuilder.sort(new Sort(productCategoryUpdateDTO.getSort()));
        }
        if (Objects.nonNull(productCategoryUpdateDTO.getEnabled())) {
            rebuilder.setEnabled(new Enabled(productCategoryUpdateDTO.getEnabled().booleanValue()));
        }
        productCategory = rebuilder.rebuild();
        productCategoryService.update(productCategory);
        return ProductCategoryBO.convert(productCategory);
    }

    @Override
    public boolean remove(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductCategoryId productCategoryId = new ProductCategoryId(id);
        ProductCategory productCategory = productCategoryService.findById(productCategoryId);
        return productCategoryService.remove(productCategory);
    }

    @Override
    public List<ProductCategoryBO> findAll() {
        List<ProductCategoryBO> boList = new ArrayList<>();
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        return ProductCategoryBO.convertBatch(productCategoryList);
    }

    @Override
    public ProductCategoryBO findById(String id) {
        return ProductCategoryBO.convert(productCategoryService.findById(new ProductCategoryId(id)));
    }

}
