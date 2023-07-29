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


/**
 *
 * @Author: chenjianxiang
 * @Date: 2023/7/28
 * @Description: ProductDetailPO 插入与修改不需要关心id，id需要自动生成，列表返回时候需要带上id
 */
@Repository
public class ProductDetailRepositoryImpl implements ProductDetailRepository {

    @Autowired
    ProductDetailMapper productDetailMapper;


    @Override
    public void insertProductDetail(ProductDetail productDetail) {
        productDetailMapper.insert(ProductDetailConvert.convertProductDetailPO(productDetail));
    }

    @Override
    public void updateProductDetail(ProductDetail productDetail) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productDetail.getProductId());
        productDetailMapper.update(ProductDetailConvert.convertProductDetailPO(productDetail),wrapper);
    }

    @Override
    public ProductDetail findByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productId.id());
        return ProductDetailConvert.convertDomain(this.productDetailMapper.selectOne(wrapper));
    }

    @Override
    public void removeProductDetailByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productId.id());
        productDetailMapper.delete(wrapper);
    }
}
