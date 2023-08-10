package com.rc.cloud.app.operate.application.service.impl;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupItemBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.application.service.ProductGroupApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupService;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductGroupErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductGroupApplicationServiceImpl implements ProductGroupApplicationService {
    @Resource
    private ProductGroupService productGroupService;
    @Resource
    private ProductGroupRepository productGroupRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;
    @Resource
    private ProductService productService;

    @Override
    public ProductGroupBO create(ProductGroupCreateDTO productGroupCreateDTO) {
        validateProductGroupCreateDTO(productGroupCreateDTO);
        productExists(new ProductId(productGroupCreateDTO.getProductId()));
        ProductGroup productGroup = new ProductGroup(new ProductGroupId(idRepository.nextId()), productGroupCreateDTO.getName(), new ProductId(productGroupCreateDTO.getProductId()));
        productGroupService.create(productGroup);
        return ProductGroupConvert.convert2ProductGroupBO(productGroup);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean release(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.ID_NOT_EMPTY);
        }
        ProductGroup productGroup = findProductGroup(new ProductGroupId(id));
        return productGroupService.release(productGroup);
    }


    @Override
    public ProductGroupItemBO createItem(ProductGroupItemCreateDTO productGroupItemCreateDTO) {
        validateProductGroupItemCreateDTO(productGroupItemCreateDTO);
        Product product = findProduct(new ProductId(productGroupItemCreateDTO.getProductId()));
        ProductGroup productGroup = findProductGroup(new ProductGroupId(productGroupItemCreateDTO.getProductGroupId()));
        ProductGroupItem item = new ProductGroupItem(new ProductGroupItemId(idRepository.nextId()), productGroup.getId(), new ProductId(productGroupItemCreateDTO.getProductId()));
        ProductGroupItem productGroupItem = productGroupService.createItem(productGroup, item);
        ProductGroupItemBO bo = ProductGroupConvert.convert2productGroupItemBO(productGroupItem, product);
        return bo;
    }


    @Override
    public List<ProductGroupBO> findList(String productId) {
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

    private void productExists(ProductId productId) {
        Product product = productRepository.findById(productId);
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
    }

    private void validateProductGroupCreateDTO(ProductGroupCreateDTO productGroupCreateDTO) {
        if (StringUtils.isEmpty(productGroupCreateDTO.getProductId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(productGroupCreateDTO.getName())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_GROUP_NAME_NOT_EMPTY);
        }
    }

    private ProductGroup findProductGroup(ProductGroupId productGroupId) {
        ProductGroup productGroup = productGroupService.findById(productGroupId);
        if (Objects.isNull(productGroup)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.ID_INVALID);
        }
        return productGroup;
    }

    private Product findProduct(ProductId productId) {
        Product product = productService.findProductById(productId);
        if (Objects.isNull(product)) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_IN_GROUP_NOT_EXISTS);
        }
        return product;
    }

    private void validateProductGroupItemCreateDTO(ProductGroupItemCreateDTO productGroupItemCreateDTO) {
        if (StringUtils.isEmpty(productGroupItemCreateDTO.getProductGroupId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_GROUP_NOT_EXISTS);
        }
        if (StringUtils.isEmpty(productGroupItemCreateDTO.getProductId())) {
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_IN_GROUP_NOT_EMPTY);
        }
    }
}


