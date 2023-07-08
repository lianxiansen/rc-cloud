package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.ProductAttributeRepository;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttributeRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProductSkuAttributeRepositoryImpl implements ProductSkuAttributeRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}
