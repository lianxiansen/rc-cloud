package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupRepository;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupItemId;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductGroupItemMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ProductGroupRepositoryImpl implements ProductGroupRepository {

    @Autowired
    private ProductGroupMapper ProductGroupMapper;

    @Autowired
    private ProductGroupItemMapper productGroupItemMapper;


    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public boolean save(ProductGroup ProductGroupEntity) {
        return false;
    }

    @Override
    public ProductGroup findById(ProductGroupId ProductGroupId) {
        return null;
    }

    @Override
    public ProductGroupId nextId() {
        return new ProductGroupId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ProductGroupItemId nextItemId() {
        return new ProductGroupItemId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public boolean removeById(ProductGroupId ProductGroupId) {
        return ProductGroupMapper.deleteById(ProductGroupId.id().toString()) > 0;
    }

    @Override
    public boolean itemExist(ProductGroupId productGroupId, ProductId productId) {
        return false;
    }

    @Override
    public List<ProductGroup> selectList(ProductId productId) {
        return null;
    }
}
