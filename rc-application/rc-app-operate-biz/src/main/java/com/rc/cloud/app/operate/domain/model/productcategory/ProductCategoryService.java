package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.RemoveShouldNotHasChildSpecification;
import com.rc.cloud.app.operate.infrastructure.constants.ErrorCodeConstants;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: SaveProductCategoryDomainServce
 * @Author: liandy
 * @Date: 2023/7/1 13:29
 * @Description: TODO
 */
@Service
public class ProductCategoryService {
    @Resource
    private ProductCategoryRepository productCategoryRepository;

    
    public ProductCategory create(ProductCategory productCategory) {
        if (Objects.nonNull(productCategory.getParentId())) {
            ProductCategory parentCategory = productCategoryRepository.findById(productCategory.getParentId());
            if (Objects.isNull(parentCategory)) {
                throw new ServiceException(ProductCategoryErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            productCategory.inheritFrom(parentCategory);
        }
        if (!productCategoryRepository.save(productCategory)) {
            throw new ServiceException(ErrorCodeConstants.SYSTEM_EXCEPTION);
        }
        return productCategory;
    }
    
    public boolean update(ProductCategory productCategory) {
        List<ProductCategory> allList = productCategoryRepository.findAll();
        if (Objects.nonNull(productCategory.getParentId())) {
            ProductCategory parent = productCategoryRepository.findById(productCategory.getParentId());
            if (Objects.isNull(parent)) {
                throw new ServiceException(ProductCategoryErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            productCategory.reInheritFrom(parent);

        } else {
            productCategory.root();
        }
        reInheritCascade(allList, productCategory);
        return productCategoryRepository.save(productCategory);
    }
    
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    
    public boolean remove(ProductCategory productCategory) {
        AssertUtils.notNull(productCategory, "productCategory must not be null");
        if(Objects.isNull(productCategory)){
            throw new ServiceException(ErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        if (!new RemoveShouldNotHasChildSpecification(productCategoryRepository).isSatisfiedBy(productCategory)) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.REMOVE_SHOULD_NOT_HAS_CHILD);
        }
        return productCategoryRepository.removeById(productCategory.getId());
    }

    /**
     * 从产品分类列表中找出子分类并重新继承父产品分类，继承之后子分类将根据父分类产品属性更新自身属性
     * @param allList
     * @param parent
     */
    public void reInheritCascade(List<ProductCategory> allList, ProductCategory parent) {
        List<ProductCategory> subList = findSubList(allList, parent);
        if (CollectionUtils.isAnyEmpty(subList)) {
            return;
        }
        subList.forEach(item -> {
            item.reInheritFrom(parent);
            productCategoryRepository.save(item);
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
    
    public void enable(ProductCategory productCategory) {
        AssertUtils.notNull(productCategory, "productCategory must be not null");
        productCategory.enable();
        productCategoryRepository.save(productCategory);
    }
    
    public void disable(ProductCategory productCategory) {
        AssertUtils.notNull(productCategory, "productCategory must be not null");
        productCategory.enable();
        productCategoryRepository.save(productCategory);
    }
    
    public ProductCategory findById(ProductCategoryId productCategoryId) {
        AssertUtils.notNull(productCategoryId, "productCategoryId must be not null");
        return productCategoryRepository.findById(productCategoryId);
    }

    public boolean existWithCategoryName(String firstCategory,String secondCategory,String thirdCategory) {
        boolean f1=true,f2=true,f3=true;
        if(StringUtils.isNotEmpty(firstCategory)){
             f1=productCategoryRepository.existsByName(firstCategory);
        }
        if(StringUtils.isNotEmpty(secondCategory)){
            f2=productCategoryRepository.existsByName(secondCategory);
        }
        if(StringUtils.isNotEmpty(thirdCategory)){
            f3=productCategoryRepository.existsByName(thirdCategory);
        }
        return f1 && f2 &&f3;
    }
}
