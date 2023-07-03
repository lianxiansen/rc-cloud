package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryAggregation;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SaveProductCategoryDomainServce
 * @Author: liandy
 * @Date: 2023/7/1 13:29
 * @Description: TODO
 */
@Service
public class ProductCategoryDomainServce {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryAggregation createProductCategory(ProductCategoryFactory.ProductCategoryBuilder builder) {
        ProductCategoryAggregation productCategoryAggregation = builder.build();
        ProductCategoryAggregation parentCategory = null;
        if (null != productCategoryAggregation.getParentId()) {
            parentCategory = productCategoryRepository.findById(productCategoryAggregation.getParentId());
            throw new DomainException("父级商品分类无效：" + productCategoryAggregation.getParentId().id());
        }
        productCategoryAggregation.inherit(parentCategory);
        return productCategoryAggregation;
    }


    public void reInherit(ProductCategoryAggregation sub, ProductCategoryAggregation parent) {
        List<ProductCategoryAggregation> allList = productCategoryRepository.findAll();
        sub.reInherit(parent);
        reInheritCascade(allList, sub);

    }

    private void reInheritCascade(List<ProductCategoryAggregation> allList, ProductCategoryAggregation parent) {
        List<ProductCategoryAggregation> subList = findSubList(allList, parent);
        if (CollectionUtils.isAnyEmpty(subList)) {
            return;
        }
        subList.forEach(item -> {
            item.reInherit(parent);
            productCategoryRepository.save(item);
            List<ProductCategoryAggregation> itemSubList = findSubList(allList, item);
            reInheritCascade(allList, item);
        });

    }

    public List<ProductCategoryAggregation> findSubList(List<ProductCategoryAggregation> allList, ProductCategoryAggregation parent) {
        AssertUtils.notNull(allList, "列表不为空");
        List<ProductCategoryAggregation> list = new ArrayList<>();
        allList.forEach(item -> {
            if (parent.getId().equals(item.getParentId())) {
                list.add(item);
            }
        });
        return list;
    }


}
