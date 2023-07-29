package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictRepository;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductDictConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductDictMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductDictPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Author: chenjianxiang
 * @Date: 2023/7/28
 * @Description: ProductDictPO 插入与修改不需要关心id，id需要自动生成，列表返回时候需要带上id
 */
@Repository
public class ProductDictRepositoryImpl implements ProductDictRepository {


    @Autowired
    private ProductDictMapper productDictMapper;


    @Override
    public List<ProductDict> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictPO>();
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return ProductDictConvert.convertDomainList(this.productDictMapper.selectList(wrapper));
    }

    @Override
    public int removeProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return productDictMapper.delete(wrapper);
    }

    @Override
    public int removeProductDictByProductIdAndKey(ProductId productId, String key) {
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        wrapper.eq(ProductDictPO::getDictKey, key);
        return productDictMapper.delete(wrapper);
    }

    @Override
    public void insertProductDict(ProductDict productDict) {
        productDictMapper.insert(ProductDictConvert.convertProductDictPO(productDict));
    }

    @Override
    public void updateProductDict(ProductDict productDict) {
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productDict.getProductId());
        wrapper.eq(ProductDictPO::getDictKey, productDict.getKey());
        productDictMapper.update(ProductDictConvert.convertProductDictPO(productDict)
        ,wrapper);

    }
}
