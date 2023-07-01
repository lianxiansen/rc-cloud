package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.productsku.ProductSkuEntity;
import com.rc.cloud.app.operate.domain.productsku.ProductSkuFactory;
import com.rc.cloud.app.operate.domain.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.productsku.valobj.Price;
import com.rc.cloud.app.operate.domain.productsku.valobj.ProductId;
import com.rc.cloud.app.operate.domain.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.domain.productsku.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductSkuRepositoryImpl implements ProductSkuRepository{

    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Autowired
    ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductSkuFactory productSkuFactory;

    @Override
    public ProductSkuId nextId() {
        return new ProductSkuId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getProductId, productId.id());
        List<ProductSkuDO> productSkuDOList = this.productSkuMapper.selectList(wrapper);

        List<ProductSkuEntity> resList =new ArrayList<>();
        for (ProductSkuDO productSkuDO : productSkuDOList) {
            ProductSkuId id = nextId();
            TenantId tenantId = new TenantId(productSkuDO.getTenantId());
            Price price=new Price();
            price.setValue(productSkuDO.getPrice());
            ProductSkuFactory.ProductSkuBuilder builder=productSkuFactory.builder(id,productId,tenantId, price);


            resList.add(builder.build());
        }
        return resList;
    }
}
