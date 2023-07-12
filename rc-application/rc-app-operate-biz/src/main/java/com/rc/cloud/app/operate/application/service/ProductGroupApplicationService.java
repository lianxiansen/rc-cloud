package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupDomainService;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroupApplicationService {
    @Autowired
    private ProductGroupDomainService productGroupDomainService;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private ProductRepository productRepository;


    public ProductGroupBO createProductGroup(ProductGroupCreateDTO productGroupCreateDTO) {
        AssertUtils.notNull(productGroupCreateDTO, "productGroupCreateDTO must be not null");
        Product product = productRepository.findById(new ProductId(productGroupCreateDTO.getProductId()));
        ProductGroup productGroup = productGroupDomainService.createProductGroup(productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), product);
        return ProductGroupBO.convert(productGroup);
    }

    public boolean release(String id) {
        AssertUtils.notEmpty(id, "id must be not empty");
        ProductGroup productGroup = productGroupRepository.findById(new ProductGroupId(id));
        if (ObjectUtils.isNull(productGroup)) {
            throw new ApplicationException("id is invalid");
        }
        return productGroupRepository.removeById(new ProductGroupId(id));
    }
    public boolean appendGroupItem(ProductGroupItemCreateDTO productGroupItemCreateDTO) {
        AssertUtils.notNull(productGroupItemCreateDTO, "productGroupItemCreateDTO must be not empty");
        ProductGroup productGroup = productGroupRepository.findById(new ProductGroupId(productGroupItemCreateDTO.getProductGroupId()));
        Product product = productRepository.findById(new ProductId(productGroupItemCreateDTO.getProductId()));
        productGroupDomainService.appendGroupItem(productGroup, product);
        return productGroupRepository.save(productGroup);
    }




    public List<ProductGroupBO> selectList(String productId){
        AssertUtils.notEmpty(productId, "productId must be not empty");
        List<ProductGroup> groupList=productGroupRepository.selectList(new ProductId(productId));
        return ProductGroupBO.convertBatch(groupList);
    }

}


