package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailRepository;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductDetailMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    ProductDetailMapper productDetailMapper;
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    @Override
    public void saveProductDetail(ProductDetail productDetail) {
        productDetailMapper.insert(ProductDetailConvert.convert(productDetail));
    }
}
