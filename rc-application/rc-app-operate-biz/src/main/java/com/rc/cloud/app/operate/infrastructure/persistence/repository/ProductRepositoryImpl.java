package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.product.ProductEntity;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.identifier.ImageId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.Product;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Repository
public class ProductRepositoryImpl extends ServiceImpl<ProductMapper, Product>  implements ProductRepository, IService<Product> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Autowired
    private ProductImageRepositoryImpl productImageRepository;
    @Override
    public void saveProductEntry(ProductEntity productEntry) {
        Product product=  new ProductConvert().convertToProduct(productEntry);
        productMapper.insert(product);
        productImageRepository.saveBatch(null);
    }

    @Override
    public ProductEntity findById(ProductId productId) {
        return null;
    }
    @Override
    public ProductId nextProductId(){
        return new ProductId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ImageId nextProductImageId(){
        return new ImageId(remoteIdGeneratorService.uidGenerator());
    }
}

@Repository
class ProductImageRepositoryImpl  extends ServiceImpl<ProductImageMapper, ProductImage> implements IService<ProductImage> {

}
