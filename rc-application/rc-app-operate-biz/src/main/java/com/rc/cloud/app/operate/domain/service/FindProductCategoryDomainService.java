package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

/**
 * @ClassName: CreateProductCategoryDomainService
 * @Author: liandy
 * @Date: 2023/7/1 08:33
 * @Description: TODO
 */
@Service
public class FindProductCategoryDomainService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryAggregation execute(ProductCategoryId productCategoryId){
        notNull(productCategoryId, "父级商品分类唯一标识不为空");
        return productCategoryRepository.findById(productCategoryId);
    }
}
