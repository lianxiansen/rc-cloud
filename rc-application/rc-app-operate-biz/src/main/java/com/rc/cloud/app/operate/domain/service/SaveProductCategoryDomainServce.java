package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SaveProductCategoryDomainServce
 * @Author: liandy
 * @Date: 2023/7/1 13:29
 * @Description: TODO
 */
@Service
public class SaveProductCategoryDomainServce {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void execute(ProductCategoryAggregation productCategoryAggregation){
        ProductCategoryAggregation parentCategory=null;
        if (null != productCategoryAggregation.getParentId()) {
            parentCategory = productCategoryRepository.findById(productCategoryAggregation.getParentId());
            throw new DomainException("父级商品分类无效："+productCategoryAggregation.getParentId().id());
        }
        productCategoryAggregation.extendsFromParent(productCategoryAggregation);
        productCategoryRepository.save(productCategoryAggregation);
    }
}
