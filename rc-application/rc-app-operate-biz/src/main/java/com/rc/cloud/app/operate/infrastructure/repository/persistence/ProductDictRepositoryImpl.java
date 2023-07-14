package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictRepository;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductDictConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductDictMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDictPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDictRepositoryImpl implements ProductDictRepository {

    private ProductDictMapper productDictMapper;


    @Override
    public List<ProductDict> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictPO>();
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return ProductDictConvert.convertList(this.productDictMapper.selectList(wrapper));
    }

    @Override
    public int removeProductDictByProductId(String productId){
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId);
        return productDictMapper.delete(wrapper);
    }

    @Override
    public void saveProductDict(List<ProductDict> productDictList) {
        productDictMapper.insertBatch(ProductDictConvert.convertPOList(productDictList));
    }
}
