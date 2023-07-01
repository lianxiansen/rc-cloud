package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.data.ProductCategoryDTO;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryFactory;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.productcategory.valobj.*;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private ProductCategoryFactory productCategoryBuilderFactory;



    public void createProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryId id = productCategoryRepository.nextId();
        TenantId tenantId = new TenantId(productCategoryDTO.getTenantId());
        Name name = new Name(productCategoryDTO.getName());
        ProductCategoryFactory.ProductCategoryBuilder builder=productCategoryBuilderFactory.builder(id,tenantId,name);
        builder.icon(new Icon(productCategoryDTO.getIcon()));
        builder.enabled(new Enabled(productCategoryDTO.getEnabledFlag()));
        builder.page(new Page(productCategoryDTO.getProductCategoryPageImage(), productCategoryDTO.getProductListPageImage()));
        builder.sort(new Sort(productCategoryDTO.getSortId()));
        if(null!=productCategoryDTO.getParentId()){
            builder.parentId(new ProductCategoryId(productCategoryDTO.getParentId()));
        }
        ProductCategoryAggregation productCategoryAggregation= builder.build();
        productCategoryRepository.save(productCategoryAggregation);
    }


    public void updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategoryAggregation productCategoryAggregation=productCategoryRepository.findById(new ProductCategoryId(productCategoryDTO.getId()));
        //TODO
    }

    public List<ProductCategoryAggregation> findAll(){
        return productCategoryRepository.findAll();
    }
}
