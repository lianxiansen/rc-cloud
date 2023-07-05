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

    public void createProductCategory(ProductCategoryCreateDTO productCategoryDTO) {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(productCategoryDTO.getTenantId());
        ChName name = new ChName(productCategoryDTO.getName());
        ProductCategory productCategory = new ProductCategory(id, tenantId, name);
        productCategory.setEnName(new EnName(productCategoryDTO.getEnglishName()));
        productCategory.setIcon(new Icon(productCategoryDTO.getIcon()));
        productCategory.setEnabled(new Enabled(productCategoryDTO.getEnabledFlag()));
        productCategory.setPage(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategoryDTO.getProductListPageImage()));
        productCategory.setSort(new Sort(productCategoryDTO.getSortId()));
        if (null != productCategoryDTO.getParentId()) {
            productCategory.setParentId(new ProductCategoryId(productCategoryDTO.getParentId()));
        }
        productCategoryDomainServce.initialize(productCategory);
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateProductCategory(ProductCategoryUpdateDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getId()));
        notNull(productCategory, "产品分类唯一标识无效");
        if (null != productCategoryDTO.getParentId()) {
            ProductCategory parent = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getParentId()));
            productCategoryDomainServce.reInherit(productCategory, parent);
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getName())) {
            productCategory.setChName(new ChName(productCategoryDTO.getName()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getEnglishName())) {
            productCategory.setEnName(new EnName(productCategoryDTO.getEnglishName()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getIcon())) {
            productCategory.setIcon(new Icon(productCategoryDTO.getIcon()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getProductCategoryPageImage())) {
            productCategory.setPage(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategory.getPage().getListImage()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getProductListPageImage())) {
            productCategory.setPage(new Page(productCategory.getPage().getCategoryImage(), productCategoryDTO.getProductListPageImage()));
        }
        if (ObjectUtils.isNull(productCategoryDTO.getSortId())) {
            productCategory.setSort(new Sort(productCategoryDTO.getSortId()));
        }
        productCategory.refresh();
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
