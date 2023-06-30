package com.rc.cloud.app.operate.domain.productcategory.service;

import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rc.cloud.common.core.util.AssertUtils.notNull;

/**
 * @ClassName: ExtendsProductCategoryDomainService
 * @Author: liandy
 * @Date: 2023/6/30 08:05
 * @Description: TODO
 */
@Service
public class SaveProductCategoryDomainService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public void execute(ProductCategoryAggregation productCategoryAggregation){
        ProductCategoryAggregation parentCategory = null;
        if (null != productCategoryAggregation.getParentId()) {
            parentCategory = productCategoryRepository.findById(new ProductCategoryId(productCategoryAggregation.getParentId().id()));
            notNull(parentCategory, "父级商品分类id无效");
        }
        productCategoryAggregation.extendsFromParent(parentCategory);
        productCategoryRepository.save(productCategoryAggregation);

    }
}
