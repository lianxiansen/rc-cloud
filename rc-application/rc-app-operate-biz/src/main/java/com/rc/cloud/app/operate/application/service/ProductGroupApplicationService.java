package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.application.dto.ProductGroupCreateDTO;
import com.rc.cloud.app.operate.application.dto.ProductGroupItemCreateDTO;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupService;
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
import java.util.List;

@Service
public class ProductGroupApplicationService {
    @Autowired
    private ProductGroupService productGroupService;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Resource
    private IdRepository idRepository;
    public ProductGroupBO create(ProductGroupCreateDTO productGroupCreateDTO) {
        if(StringUtils.isEmpty(productGroupCreateDTO.getProductId())){
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        if(StringUtils.isEmpty(productGroupCreateDTO.getName())){
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_GROUP_NAME_NOT_EMPTY);
        }
        ProductGroup productGroup = productGroupService.create(productGroupCreateDTO.getName(), new TenantId(TenantContext.getTenantId()), new ProductId(productGroupCreateDTO.getProductId()));
        return ProductGroupConvert.convert2ProductGroupBO(productGroup);
    }

    public boolean release(String id) {
        if(StringUtils.isEmpty(id)){
            throw new ServiceException(ProductGroupErrorCodeConstants.ID_NOT_EMPTY);
        }
        return productGroupService.release(new ProductGroupId(id));
    }

    public boolean appendGroupItem(ProductGroupItemCreateDTO productGroupItemCreateDTO) {
        if(StringUtils.isEmpty(productGroupItemCreateDTO.getProductGroupId())){
            throw new ServiceException(ProductGroupErrorCodeConstants.OBJECT_NOT_EXISTS);
        }
        if(StringUtils.isEmpty(productGroupItemCreateDTO.getProductId())){
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_IN_GROUP_NOT_EMPTY);
        }
        return  productGroupService.appendGroupItem(new ProductGroupId(productGroupItemCreateDTO.getProductGroupId()), new ProductId(productGroupItemCreateDTO.getProductId()));
    }


    public List<ProductGroupBO> selectList(String productId){
        if(StringUtils.isEmpty(productId)){
            throw new ServiceException(ProductGroupErrorCodeConstants.PRODUCT_ID_NOT_EMPTY);
        }
        List<ProductGroup> groupList=productGroupRepository.selectListBy(new ProductId(productId));
        return ProductGroupConvert.convert2ProductGroupBOBatch(groupList);
    }

}

