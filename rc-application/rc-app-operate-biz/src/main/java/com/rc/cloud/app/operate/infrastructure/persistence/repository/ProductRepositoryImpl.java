package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.identifier.ImageId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductPOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Repository
public class ProductRepositoryImpl extends ServiceImpl<ProductMapper, ProductDO>  implements ProductRepository, IService<ProductDO> {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Autowired
    private ProductImageRepositoryImpl productImageRepository;
    @Override
    public void saveProductEntry(ProductAggregation productEntry) {
        ProductDO product=  new ProductPOConvert().convertToProduct(productEntry);
        productMapper.insert(product);
        productImageRepository.saveBatch(null);
    }

    @Override
    public ProductAggregation findById(ProductId productId) {
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
class ProductImageRepositoryImpl  extends ServiceImpl<ProductImageMapper, ProductImageDO> implements IService<ProductImageDO> {

}
