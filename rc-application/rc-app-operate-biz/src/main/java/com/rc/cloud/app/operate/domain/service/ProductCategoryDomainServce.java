package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRepository;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Enabled;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private ProductCategoryRepository productCategoryRepository;

    @Resource
    private ProductRepository productRepository;




    public void reInherit(ProductCategory sub, ProductCategory parent) {
        AssertUtils.notNull(sub, "sub must not be null");
        AssertUtils.notNull(parent, "parent must not be null");
        List<ProductCategory> allList = productCategoryRepository.findAll();
        sub.reInherit(parent);
        reInheritCascade(allList, sub);
    }


    public boolean remove(ProductCategory productCategory){
        AssertUtils.notNull(productCategory, "productCategory must not be null");
        if(productRepository.existsByProductCategoryId(productCategory.getId())){
            throw new DomainException("已关联产品,删除失败");
        }
        if(productCategoryRepository.existsChild(productCategory.getId())){
            throw new DomainException("已关联子分类，删除失败");
        }
        return productCategoryRepository.remove(productCategory);
    }

    private void reInheritCascade(List<ProductCategory> allList, ProductCategory parent) {
        List<ProductCategory> subList = findSubList(allList, parent);
        if (CollectionUtils.isAnyEmpty(subList)) {
            return;
        }
        subList.forEach(item -> {
            item.reInherit(parent);
            item.publishSaveEvent();
            List<ProductCategory> itemSubList = findSubList(allList, item);
            reInheritCascade(allList, item);
        });

    }

    public List<ProductCategory> findSubList(List<ProductCategory> allList, ProductCategory parent) {
        AssertUtils.notNull(allList, "allList must not be null");
        AssertUtils.notNull(parent, "parent must not be null");
        List<ProductCategory> list = new ArrayList<>();
        allList.forEach(item -> {
            if (parent.getId().equals(item.getParentId())) {
                list.add(item);
            }
        });
        return list;
    }
    public void enable(ProductCategoryId productCategoryId) {
        AssertUtils.notNull(productCategoryId,"productCategoryId must be not null");
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId);
        productCategory.setEnabled(new Enabled(true));
        productCategoryRepository.save(productCategory);
    }
    public void disable(ProductCategoryId productCategoryId) {
        AssertUtils.notNull(productCategoryId,"productCategoryId must be not null");
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId);
        productCategory.setEnabled(new Enabled(false));
        productCategoryRepository.save(productCategory);
    }

}
