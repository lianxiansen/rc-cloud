package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductCategoryBO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductCategoryUpdateDTO;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.service.ProductCategoryDomainServce;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

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
    private ProductCategoryDomainServce productCategoryDomainServce;

    public ProductCategory createProductCategory(ProductCategoryCreateDTO productCategoryDTO) {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(productCategoryDTO.getTenantId());
        ChName name = new ChName(productCategoryDTO.getName());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(productCategoryDTO.getEnglishName()));
        productCategory.setIcon(new Icon(productCategoryDTO.getIcon()));
        if(ObjectUtils.isNotNull(productCategoryDTO.getEnabledFlag())){
            productCategory.setEnabled(new Enabled(productCategoryDTO.getEnabledFlag()));
        }
        productCategory.setPage(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategoryDTO.getProductListPageImage()));
        if(ObjectUtils.isNotNull(productCategoryDTO.getSortId())){
            productCategory.setSort(new Sort(productCategoryDTO.getSortId()));
        }
        if(!StringUtils.isEmpty(productCategoryDTO.getParentId())){
            ProductCategoryId parentId= new ProductCategoryId(productCategoryDTO.getParentId());
            ProductCategory parentCategory = productCategoryRepository.findById(parentId);
            if(ObjectUtils.isNull(parentCategory)){
                throw new ApplicationException("父级商品分类无效");
            }
            productCategory.inherit(parentCategory);
        }
        productCategory.publishSaveEvent();
        return productCategory;
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateProductCategory(ProductCategoryUpdateDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getId()));
        notNull(productCategory, "产品分类唯一标识无效");
        if (ObjectUtils.isNotNull(productCategoryDTO.getParentId()) ) {
            if(StringUtils.isEmpty(productCategoryDTO.getParentId())){
                productCategory.root();
            }else{
                ProductCategoryId parentId= new ProductCategoryId(productCategoryDTO.getParentId());
                if(!parentId.equals(productCategory.getParentId())){
                    ProductCategory parent = productCategoryRepository.findById(parentId);
                    productCategoryDomainServce.reInherit(productCategory, parent);
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
        productCategory.publishSaveEvent();
    }

    public void remove(String id){
        ProductCategory productCategory=productCategoryRepository.findById(new ProductCategoryId(id));
        productCategoryDomainServce.remove(productCategory);
    }
    public List<ProductCategoryBO> findAll() {
        List<ProductCategoryBO> boList=new ArrayList<>();
        List<ProductCategory> productCategoryList=productCategoryRepository.findAll();
        return ProductCategoryBO.convertBatch(productCategoryList);
    }
}
