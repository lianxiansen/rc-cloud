package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.ProductImageRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @ClassName: ProductImageRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/29 13:25
 * @Description: TODO
 */
@Repository
public class ProductImageRepositoryImpl  implements ProductImageRepository {

    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;


}
