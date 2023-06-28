package com.rc.cloud.app.operate.domain.category.service;

import com.rc.cloud.app.operate.domain.category.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.category.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.category.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductCategoryExistService
 * @Author: liandy
 * @Date: 2023/6/27 13:32
 * @Description: TODO
 */
@Service
public class ContainsProductCategoryDomainService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public boolean execute(ProductCategoryId productCategoryId) {
        ProductCategoryAggregation productCategory= productCategoryRepository.findById(productCategoryId);
        if(null!=productCategory){
            return true;
        }
        return false;
    }

}
