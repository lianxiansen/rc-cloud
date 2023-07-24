package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.*;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductGroupErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.util.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductGroupApplicationServiceImpl implements ProductGroupApplicationService {
    @Autowired
    private ProductGroupDomainService productGroupService;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;
    @Autowired
    private ProductDomainService productDomainService;

    @Override
    public ProductGroupBO create(ProductGroupCreateDTO productGroupCreateDTO) {
        if (StringUtils.isEmpty(productGroupCreateDTO.getProductId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(productGroupCreateDTO.getName())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_GROUP_NAME_NOT_EMPTY);
        }
        ProductGroup productGroup = productGroupService.create(productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        return ProductGroupConvert.convert2ProductGroupBO(productGroup);
    }

    @Override
    public boolean release(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.ID_NOT_EMPTY);
        }
        return productGroupService.release(new ProductGroupId(id));
    }

    @Override
    public ProductGroupItemBO createItem(ProductGroupItemCreateDTO productGroupItemCreateDTO) {
        if (StringUtils.isEmpty(productGroupItemCreateDTO.getProductGroupId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_GROUP_NOT_EXISTS);
        }
        if (StringUtils.isEmpty(productGroupItemCreateDTO.getProductId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_IN_GROUP_NOT_EMPTY);
        }
        ProductGroupItem productGroupItem = productGroupService.createItem(new ProductGroupId(productGroupItemCreateDTO.getProductGroupId()), new ProductId(productGroupItemCreateDTO.getProductId()));
        Product product = productRepository.findById(new ProductId(productGroupItem.getProductId().id()));
        ProductGroupItemBO bo = ProductGroupConvert.convert2productGroupItemBO(productGroupItem, product);
        return bo;
    }


    @Override
    public List<ProductGroupBO> findListByProductId(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        List<ProductGroup> groupList = productGroupRepository.findAll(new ProductId(productId));
        List<Product> list = productRepository.selectBatchIds(findProductIds(groupList));
        List<ProductGroupBO> bos = ProductGroupConvert.convert2ProductGroupBOBatch(groupList, list);
        return bos;
    }

    private List<ProductId> findProductIds(List<ProductGroup> productGroups) {
        List<ProductId> productIds = new ArrayList<>();
        productGroups.forEach(group -> {
            productIds.addAll(group.getProductGroupItems().stream().map(e -> e.getProductId()).collect(Collectors.toList()));
        });
        return productIds.stream().distinct().collect(Collectors.toList());
    }


}


