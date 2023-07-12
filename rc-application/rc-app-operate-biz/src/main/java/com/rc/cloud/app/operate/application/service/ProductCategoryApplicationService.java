package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryDomainService;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @ClassName: ProductCategoryService
 * @Author: liandy
 * @Date: 2023/6/23 14:43
 * @Description: TODO
 */
@Service
public class ProductCategoryApplicationService {
    @Resource
    private ProductCategoryRepository productCategoryRepository;
    @Resource
    private ProductCategoryDomainService productCategoryService;
    @Resource
    private IdRepository idRepository;


    public ProductCategoryBO createProductCategory(ProductCategoryCreateDTO productCreateCategoryDTO) {
        AssertUtils.notNull(productCreateCategoryDTO,"productCreateCategoryDTO must be not null");
        TenantId tenantId = new TenantId(productCreateCategoryDTO.getTenantId());
        ChName name = new ChName(productCreateCategoryDTO.getName());
        ProductCategory productCategory = new ProductCategory(new ProductCategoryId(idRepository.nextId()), tenantId, name);
        productCategory.setEnName(new EnName(productCreateCategoryDTO.getEnglishName()));
        productCategory.setIcon(new Icon(productCreateCategoryDTO.getIcon()));
        if(ObjectUtils.isNotNull(productCreateCategoryDTO.getEnabledFlag())){
            productCategory.setEnabled(new Enabled(productCreateCategoryDTO.getEnabledFlag()));
        }
        productCategory.setPage(new Page(productCreateCategoryDTO.getProductCategoryPageImage(), productCreateCategoryDTO.getProductListPageImage()));
        if(ObjectUtils.isNotNull(productCreateCategoryDTO.getSortId())){
            productCategory.setSort(new Sort(productCreateCategoryDTO.getSortId()));
        }
        if(!StringUtils.isEmpty(productCreateCategoryDTO.getParentId())){
            ProductCategoryId parentId= new ProductCategoryId(productCreateCategoryDTO.getParentId());
            ProductCategory parentCategory = productCategoryRepository.findById(parentId);
            if(ObjectUtils.isNull(parentCategory)){
                throw new ApplicationException("父级商品分类无效");
            }
            productCategory.inherit(parentCategory);
        }
        productCategory.publishSaveEvent();
        return ProductCategoryBO.convert(productCategory);
    }


    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryBO updateProductCategory(ProductCategoryUpdateDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getId()));
        if(ObjectUtils.isNull(productCategory)){
            throw new ApplicationException("产品分类不存在");
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getParentId()) ) {
            if(StringUtils.isEmpty(productCategoryDTO.getParentId())){
                productCategory.root();
            }else{
                ProductCategoryId parentId= new ProductCategoryId(productCategoryDTO.getParentId());
                if(!parentId.equals(productCategory.getParentId())){
                    ProductCategory parent = productCategoryRepository.findById(parentId);
                    productCategoryService.reInherit(productCategory, parent);
                }
            }
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getName())) {
            productCategory.setChName(new ChName(productCategoryDTO.getName()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getEnglishName())) {
            productCategory.setEnName(new EnName(productCategoryDTO.getEnglishName()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getIcon())) {
            productCategory.setIcon(new Icon(productCategoryDTO.getIcon()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getProductCategoryPageImage())) {
            productCategory.setPage(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategory.getPage().getListImage()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getProductListPageImage())) {
            productCategory.setPage(new Page(productCategory.getPage().getCategoryImage(), productCategoryDTO.getProductListPageImage()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getSortId())) {
            productCategory.setSort(new Sort(productCategoryDTO.getSortId()));
        }
        if (ObjectUtils.isNotNull(productCategoryDTO.getEnabledFlag())) {
            if(productCategoryDTO.getEnabledFlag().booleanValue()){
                productCategory.setEnabled(new Enabled(true));
            }else{
                productCategory.setEnabled(new Enabled(false));
            }
        }
        productCategory.publishSaveEvent();
        return ProductCategoryBO.convert(productCategory);
    }

    public boolean remove(String id){
        if(StringUtils.isEmpty(id)){
            throw exception(ProductCategoryErrorCodeConstants.NOT_EXISTS);
        }
        return productCategoryService.remove(new ProductCategoryId(id));
    }
    public List<ProductCategoryBO> findAll() {
        List<ProductCategoryBO> boList=new ArrayList<>();
        List<ProductCategory> productCategoryList=productCategoryRepository.findAll();
        return ProductCategoryBO.convertBatch(productCategoryList);
    }

    public void changeState(String id){
        ProductCategory productCategory=productCategoryRepository.findById(new ProductCategoryId(id));
        if(ObjectUtils.isNull(productCategory)){
            throw new ApplicationException("产品分类不存在");
        }
        if(productCategory.getEnabled().isTrue()){
            productCategoryService.disable(productCategory.getId() );
        }
        else{
            productCategoryService.enable(productCategory.getId() );
        }

    }

}
