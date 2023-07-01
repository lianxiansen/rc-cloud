package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.data.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.product.ProductAggregation;
import com.rc.cloud.app.operate.domain.product.ProductRepository;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductPOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

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
    public ProductImageId nextProductImageId(){
        return new ProductImageId(remoteIdGeneratorService.uidGenerator());
    }


    public  PageResult<ProductDO> getProductPageList(ProductListQueryDTO productListQueryDTO){
        return productMapper.selectPage(productListQueryDTO);
    }
}




