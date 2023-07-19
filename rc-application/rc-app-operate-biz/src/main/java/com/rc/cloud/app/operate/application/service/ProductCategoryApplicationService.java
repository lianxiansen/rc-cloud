package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryService;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: ProductCategoryService
 * @Author: liandy
 * @Date: 2023/6/23 14:43
 * @Description: TODO
 */
@Service
public class ProductCategoryApplicationService {
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private IdRepository idRepository;


    public ProductCategoryBO create(ProductCategoryCreateDTO productCreateCategoryDTO) {
        if(StringUtils.isEmpty(productCreateCategoryDTO.getName())){
            throw new ServiceException(ProductCategoryErrorCodeConstants.NAME_NOT_EMPTY);
        }
        TenantId tenantId = new TenantId(TenantContext.getTenantId());
        ChName name = new ChName(productCreateCategoryDTO.getName());
        ProductCategory productCategory = new ProductCategory(new ProductCategoryId(idRepository.nextId()), tenantId, name);
        productCategory.setEnName(new EnName(productCreateCategoryDTO.getEnglishName()));
        productCategory.setIcon(new Icon(productCreateCategoryDTO.getIcon()));
        if(ObjectUtils.isNotNull(productCreateCategoryDTO.getEnabled())){
            productCategory.setEnabled(new Enabled(productCreateCategoryDTO.getEnabled()));
        }
        productCategory.setPage(new Page(productCreateCategoryDTO.getProductCategoryPageImage(), productCreateCategoryDTO.getProductListPageImage()));
        if(ObjectUtils.isNotNull(productCreateCategoryDTO.getSortId())){
            productCategory.setSort(new Sort(productCreateCategoryDTO.getSortId()));
        }
        if(!StringUtils.isEmpty(productCreateCategoryDTO.getParentId())){
            ProductCategoryId parentId= new ProductCategoryId(productCreateCategoryDTO.getParentId());
            productCategory.setParentId(parentId);
        }
        productCategoryService.save(productCategory);
        return ProductCategoryBO.convert(productCategory);
    }


    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryBO update(ProductCategoryUpdateDTO productCategoryUpdateDTO) {
        if(StringUtils.isEmpty(productCategoryUpdateDTO.getId())){
            throw new ServiceException(ProductCategoryErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductCategory productCategory = productCategoryService.findById(new ProductCategoryId(productCategoryUpdateDTO.getId()));
        if(Objects.isNull(productCategory)){
            throw new ServiceException(ProductCategoryErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        if(Objects.nonNull(productCategoryUpdateDTO.getParentId())){
            if(StringUtils.isEmpty(productCategoryUpdateDTO.getParentId())){
                productCategory.setParentId(null);
            }else{
                productCategory.setParentId(new ProductCategoryId(productCategoryUpdateDTO.getParentId()));
            }
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getName())) {
            productCategory.setChName(new ChName(productCategoryUpdateDTO.getName()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getEnglishName())) {
            productCategory.setEnName(new EnName(productCategoryUpdateDTO.getEnglishName()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getIcon())) {
            productCategory.setIcon(new Icon(productCategoryUpdateDTO.getIcon()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getProductCategoryPageImage())) {
            productCategory.setPage(new Page(productCategoryUpdateDTO.getProductCategoryPageImage(), productCategory.getPage().getListImage()));
        }
        if (StringUtils.isNotEmpty(productCategoryUpdateDTO.getProductListPageImage())) {
            productCategory.setPage(new Page(productCategory.getPage().getCategoryImage(), productCategoryUpdateDTO.getProductListPageImage()));
        }
        if (Objects.nonNull(productCategoryUpdateDTO.getSortId())) {
            productCategory.setSort(new Sort(productCategoryUpdateDTO.getSortId()));
        }
        if (Objects.nonNull(productCategoryUpdateDTO.getEnabled())) {
            if(productCategoryUpdateDTO.getEnabled().booleanValue()){
                productCategory.setEnabled(new Enabled(true));
            }else{
                productCategory.setEnabled(new Enabled(false));
            }
        }
        productCategoryService.update(productCategory);
        return ProductCategoryBO.convert(productCategory);
    }

    public boolean remove(String id){
        if(StringUtils.isEmpty(id)){
            throw new ServiceException(ProductCategoryErrorCodeConstants.ID_NOT_EMPTY);
        }
        return productCategoryService.remove(new ProductCategoryId(id));
    }
    public List<ProductCategoryBO> findAll() {
        List<ProductCategoryBO> boList=new ArrayList<>();
        List<ProductCategory> productCategoryList=productCategoryService.findAll();
        return ProductCategoryBO.convertBatch(productCategoryList);
    }
    public  ProductCategoryBO findById(String id) {
        return ProductCategoryBO.convert( productCategoryService.findById(new ProductCategoryId(id)));
    }

}
