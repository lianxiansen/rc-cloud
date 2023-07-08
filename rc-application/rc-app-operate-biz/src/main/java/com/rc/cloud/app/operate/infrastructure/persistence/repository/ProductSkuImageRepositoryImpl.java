package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.ProductImageRepository;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImageRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @ClassName: ProductImageRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/29 13:25
 * @Description: TODO
 */
@Repository
public class ProductSkuImageRepositoryImpl implements ProductSkuImageRepository {

    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}
