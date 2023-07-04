package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
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

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        ProductCategory parentCategory = null;
        if (null != productCategory.getParentId()) {
            parentCategory = productCategoryRepository.findById(productCategory.getParentId());
            throw new DomainException("父级商品分类无效：" + productCategory.getParentId().id());
        }
        productCategory.inherit(parentCategory);
        return productCategory;
    }


    public void reInherit(ProductCategory sub, ProductCategory parent) {
        List<ProductCategory> allList = productCategoryRepository.findAll();
        sub.reInherit(parent);
        reInheritCascade(allList, sub);

    }

    private void reInheritCascade(List<ProductCategory> allList, ProductCategory parent) {
        List<ProductCategory> subList = findSubList(allList, parent);
        if (CollectionUtils.isAnyEmpty(subList)) {
            return;
        }
        subList.forEach(item -> {
            item.reInherit(parent);
            productCategoryRepository.save(item);
            List<ProductCategory> itemSubList = findSubList(allList, item);
            reInheritCascade(allList, item);
        });

    }

    public List<ProductCategory> findSubList(List<ProductCategory> allList, ProductCategory parent) {
        AssertUtils.notNull(allList, "列表不为空");
        List<ProductCategory> list = new ArrayList<>();
        allList.forEach(item -> {
            if (parent.getId().equals(item.getParentId())) {
                list.add(item);
            }
        });
        return list;
    }


}