package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailRepository;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductDetailMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDetailPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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

        ProductDetailId id = productDetail.getId();
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId,id.getProductId().id());
        productDetailMapper.update(ProductDetailConvert.convertProductDetailPO(productDetail),wrapper);
    }

    @Override
    public ProductDetail findById(ProductDetailId productDetailId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productDetailId.getProductId().id());
        return ProductDetailConvert.convertDomain(this.productDetailMapper.selectOne(wrapper));
    }

    @Override
    public void removeProductDetail(ProductDetailId productDetailId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productDetailId.getProductId().id());
        productDetailMapper.delete(wrapper);
    }

    @Override
    public boolean exist(ProductDetailId productDetailId) {
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productDetailId.getProductId().id());
        return productDetailMapper.exists(wrapper);
    }
}
