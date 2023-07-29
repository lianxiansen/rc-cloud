package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductGroupDomainServiceImpl implements ProductGroupDomainService {

    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Resource
    private IdRepository idRepository;

    @Override
    public ProductGroup create(ProductGroup productGroup) {
        AssertUtils.notNull(productGroup, "productGroup must be not null");
        productGroupRepository.save(productGroup);
        return productGroup;
    }

    @Override
    public ProductGroupItem createItem(ProductGroup productGroup,ProductGroupItem item) {
        productGroup.createItem(item);
        productGroupRepository.save(productGroup);
        return item;
    }


    @Override
    public ProductGroup findById(ProductGroupId productGroupId) {
        return productGroupRepository.findById(productGroupId);
    }

    @Override
    public boolean release(ProductGroup productGroup) {
        return productGroupRepository.removeById(productGroup.getId());
    }


}
