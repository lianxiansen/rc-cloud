package com.rc.cloud.app.operate.domain.service;

import com.rc.cloud.app.operate.domain.common.DomainException;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupDomainService {

    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private ProductRepository productRepository;


    public ProductGroup createProductGroup(String name,Product product){
        AssertUtils.notEmpty(name,"name must be not empty");
        AssertUtils.notNull(product,"product must be not null");

        ProductGroup productGroup = new ProductGroup(productGroupRepository.nextId(),name,product.getId());
        productGroupRepository.save(productGroup);
        return productGroup;
    }

    public boolean appendGroupItem(ProductGroup productGroup, Product product) {
        AssertUtils.notNull(productGroup,"productGroup must be not null");
        AssertUtils.notNull(product,"product must be not null");
        if(productGroupRepository.itemExist(productGroup.getId(),product.getId())){
            throw new DomainException("组合产品重复");
        }
        ProductGroupItem item=new ProductGroupItem(productGroupRepository.nextItemId(),productGroup.getId(),product.getId());
        return productGroup.appendItem(item);
    }


}
