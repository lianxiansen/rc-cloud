package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictRepository;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductDictConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductDictMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ProductDictRepositoryImpl implements ProductDictRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    private ProductDictMapper productDictMapper;

    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    @Override
    public List<ProductDict> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictPO>();
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return ProductDictConvert.INSTANCE.convertList(this.productDictMapper.selectList(wrapper));
    }

    public int removeProductDictByProductId(String productId){
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId);
        return productDictMapper.delete(wrapper);
    }


}
