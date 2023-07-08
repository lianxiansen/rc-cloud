package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.ProductDetailRepository;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProductDetailRepositoryImpl implements ProductDetailRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}
