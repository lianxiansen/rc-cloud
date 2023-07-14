package com.rc.cloud.app.operate.domain.model.productgroup;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductGroupErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;

    public ProductGroup create(String name, TenantId tenantId, ProductId productId) {
        AssertUtils.notEmpty(name, "name must be not empty");
        AssertUtils.notNull(tenantId, "tenantId must be not null");
        AssertUtils.notNull(productId, "product must be not null");
        Product product = productRepository.findById(productId);
        if (ObjectUtils.isNull(product)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), name, tenantId, productId);
        productGroupRepository.save(productGroup);
        return productGroup;
    }
    public boolean appendGroupItem(ProductGroupId productGroupId, ProductId productId) {
        ProductGroup productGroup = productGroupRepository.findById(productGroupId);
        if (ObjectUtils.isNull(productGroup)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        Product product = productRepository.findById(productId);
        if (ObjectUtils.isNull(product)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_IN_GROUP_NOT_EXISTS);
        }
        ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroup.getId(), product.getId());
        return productGroup.appendItem(item);
    }


    public ProductGroup findById(ProductGroupId productGroupId) {
        return productGroupRepository.findById(productGroupId);
    }

    public boolean release(ProductGroupId productGroupId){
        ProductGroup productGroup = this.findById(productGroupId);
        if (ObjectUtils.isNull(productGroup)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        return productGroupRepository.removeById(productGroupId);
    }


}
