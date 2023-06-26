package com.rc.cloud.app.product.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.product.domain.product.ProductEntity;
import com.rc.cloud.app.product.domain.product.ProductRepository;
import com.rc.cloud.app.product.domain.product.valobj.Id;
import com.rc.cloud.app.product.domain.product.valobj.ImageId;
import com.rc.cloud.app.product.infrastructure.persistence.convert.ProductConvert;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.product.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.product.infrastructure.persistence.po.Product;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Repository
public class
ProductRepositoryImpl extends ServiceImpl<ProductMapper, Product>  implements ProductRepository, IService<Product> {
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
    public ProductEntity getProduct(Id productId) {
        return null;
    }
    @Override
    public Id nextProductId(){
        return new Id(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public ImageId nextProductImageId(){
        return new ImageId(remoteIdGeneratorService.uidGenerator());
    }
}
@Repository
class ProductImageRepositoryImpl  extends ServiceImpl<ProductImageMapper, ProductImage> implements IService<ProductImage> {

}
