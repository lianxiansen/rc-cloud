package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.dto.ProductCategoryDTO;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.service.ProductCategoryDomainServce;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ProductCategoryRepository productCategoryRepository;



    @Autowired
    private ProductCategoryDomainServce productCategoryDomainServce;

    public void createProductCategory(ProductCategoryDTO productCategoryDTO) {
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
        ProductCategory productCategoryAggregation = productCategoryDomainServce.createProductCategory(productCategory);
        productCategoryRepository.save(productCategoryAggregation);
    }


    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategoryAggregation = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getId()));
        notNull(productCategoryAggregation, "Id无效");
        if (null != productCategoryDTO.getParentId()) {
            ProductCategory parent = productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getParentId()));
            productCategoryDomainServce.reInherit(productCategoryAggregation,parent);
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getName())) {
            productCategoryAggregation.setChName(new ChName(productCategoryDTO.getName()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getEnglishName())) {
            productCategoryAggregation.setEnName(new EnName(productCategoryDTO.getEnglishName()));
        }
        if (StringUtils.isNotEmpty(productCategoryDTO.getIcon())) {
            productCategoryAggregation.setIcon(new Icon(productCategoryDTO.getIcon()));
        }
        productCategoryRepository.save(productCategoryAggregation);
    }

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }
}
