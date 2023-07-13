package com.rc.cloud.app.operate.domain.model.productcategory;

import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.RemoveShouldNotAssociatedProductSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.specification.RemoveShouldNotHasChildSpecification;
import com.rc.cloud.app.operate.domain.model.productcategory.valobj.Enabled;
import com.rc.cloud.app.operate.infrastructure.constants.ProductCategoryErrorCodeConstants;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
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
public class ProductCategoryService {
    @Resource
    private ProductCategoryRepository productCategoryRepository;
    @Resource
    private RemoveShouldNotHasChildSpecification removeShouldNoChildSpecification;
    @Resource
    private RemoveShouldNotAssociatedProductSpecification removeShouldNotAssociatedProductSpecification;

    public boolean save(ProductCategory productCategory){
        if(ObjectUtils.isNotNull(productCategory.getParentId())){
            ProductCategory parentCategory = productCategoryRepository.findById(productCategory.getParentId());
            if(ObjectUtils.isNull(parentCategory)){
                throw new ServiceException(ProductCategoryErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            productCategory.inherit(parentCategory);
        }
        return productCategoryRepository.save(productCategory);
    }
    public boolean update(ProductCategory productCategory){
        List<ProductCategory> allList = productCategoryRepository.findAll();
        if(ObjectUtils.isNotNull(productCategory.getParentId())){
            ProductCategory parent = productCategoryRepository.findById(productCategory.getParentId());
            if(ObjectUtils.isNull(parent)){
                throw new ServiceException(ProductCategoryErrorCodeConstants.PARENT_NOT_EXISTS);
            }
            productCategory.reInherit(parent);

        }else{
            productCategory.root();
        }
        reInheritCascade(allList, productCategory);
        return productCategoryRepository.save(productCategory);
    }



    public boolean remove(ProductCategoryId productCategoryId) {
        AssertUtils.notNull(productCategoryId, "productCategoryId must not be null");
        if (!removeShouldNotAssociatedProductSpecification.isSatisfiedBy(productCategoryId)) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.REMOVE_SHOULD_NOT_ASSOCIATED_PRODUCT);
        }
        if (!removeShouldNoChildSpecification.isSatisfiedBy(productCategoryId)) {
            throw new ServiceException(ProductCategoryErrorCodeConstants.REMOVE_SHOULD_NOT_HAS_CHILD);
        }
        return productCategoryRepository.removeById(productCategoryId);
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

    public void enable(ProductCategory productCategory) {
        AssertUtils.notNull(productCategory, "productCategory must be not null");
        productCategory.setEnabled(new Enabled(true));
        productCategoryRepository.save(productCategory);
    }

    public void disable(ProductCategory productCategory) {
        AssertUtils.notNull(productCategory, "productCategory must be not null");
        productCategory.setEnabled(new Enabled(false));
        productCategoryRepository.save(productCategory);
    }

    public ProductCategory findById(ProductCategoryId productCategoryId){
        AssertUtils.notNull(productCategoryId, "productCategoryId must be not null");
        return productCategoryRepository.findById(productCategoryId);
    }
}
