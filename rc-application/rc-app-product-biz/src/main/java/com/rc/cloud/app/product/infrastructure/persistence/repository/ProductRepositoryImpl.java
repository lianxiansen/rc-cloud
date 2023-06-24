package com.rc.cloud.app.product.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.product.domain.product.ProductEntry;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.ProductId;
import com.rc.cloud.app.product.domain.product.valobj.ProductImageId;
import com.rc.cloud.app.product.infrastructure.persistence.convert.ProductConvert;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Service
public class ProductRepositoryImpl implements ProductRepository {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Override
    public void saveProductEntry(ProductEntry productEntry) {

    }

    @Override
    public ProductEntry getProduct(ProductId productId) {
        return new ProductConvert().convertToProductEntry(productMapper.selectById(productId.id()));
    }
    @Override
    public ProductId nextProductId(){
        return new ProductId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ProductImageId nextProductImageId(){
        return new ProductImageId(remoteIdGeneratorService.uidGenerator());
    }
}
