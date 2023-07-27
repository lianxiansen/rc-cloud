package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailRepository;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductDetailMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDetailPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDictPO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    @Autowired
    ProductDetailMapper productDetailMapper;


    @Override
    public void saveProductDetail(ProductDetail productDetail) {
        productDetailMapper.insert(ProductDetailConvert.convert(productDetail));
    }

    @Override
    public ProductDetail findById(ProductId productId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getId, productId.id());
        return ProductDetailConvert.convert(this.productDetailMapper.selectOne(wrapper));
    }

    @Override
    public void removeProductDetailByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productId);
        productDetailMapper.delete(wrapper);
    }
}
