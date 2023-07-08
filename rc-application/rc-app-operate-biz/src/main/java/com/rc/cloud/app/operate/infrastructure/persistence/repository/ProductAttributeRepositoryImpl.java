package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.ProductAttributeRepository;
import com.rc.cloud.app.operate.domain.model.product.ProductDictRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProductAttributeRepositoryImpl implements ProductAttributeRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}