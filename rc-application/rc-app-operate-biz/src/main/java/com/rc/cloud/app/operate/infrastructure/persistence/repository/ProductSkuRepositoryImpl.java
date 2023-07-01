package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.brand.BrandEntity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuEntity;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuFactory;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuConvert;
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

    public boolean exist(ProductSkuId productSkuId){
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getId, productSkuId.id());
        return this.productSkuMapper.exists(wrapper);
    }

    public ProductSkuEntity findById(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getId, productSkuId.id());
        ProductSkuDO ProductSkuDO = this.productSkuMapper.selectOne(wrapper);
        return convert2ProductSkuEntity(ProductSkuDO);
    }

    @Override
    public void saveProductSku(ProductSkuEntity productSkuEntity) {

        ProductSkuDO productSkuDO= ProductSkuConvert.convert2ProductSkuDO(productSkuEntity);
        if(exist(productSkuEntity.getId())){
            LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
            wrapper.eq(ProductSkuDO::getId, productSkuEntity.getId());
            productSkuMapper.update(productSkuDO,wrapper);
        }else{
            productSkuMapper.insert(productSkuDO);
        }
    }

    @Override
    public List<ProductSkuEntity> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getProductId, productId.id());
        List<ProductSkuDO> productSkuDOList = this.productSkuMapper.selectList(wrapper);

        List<ProductSkuEntity> resList =new ArrayList<>();
        for (ProductSkuDO productSkuDO : productSkuDOList) {
            ProductSkuEntity productSkuEntity = convert2ProductSkuEntity(productSkuDO);
            resList.add(productSkuEntity);
        }
        return resList;
    }


    private ProductSkuEntity convert2ProductSkuEntity(ProductSkuDO productSkuDO ){
        ProductId productId=new ProductId(productSkuDO.getProductId());
        ProductSkuId id = nextId();
        TenantId tenantId = new TenantId(productSkuDO.getTenantId());
        Price price=new Price();
        price.setValue(productSkuDO.getPrice());
        ProductSkuFactory.ProductSkuReBuilder builder=productSkuFactory.reBuilder(id,productId,tenantId, price);
        //秒杀信息
        SeckillSku seckillSku=new SeckillSku();
        seckillSku.setSeckillInventory(new Inventory(productSkuDO.getSeckillInventory()));
        seckillSku.setSeckillPrice(new Price(productSkuDO.getPrice()));
        seckillSku.setSeckillTotalInventory(new TotalInventory(productSkuDO.getSeckillTotalInventory()));
        seckillSku.setSeckillLimitBuy(new LimitBuy(productSkuDO.getSeckillLimitBuy()));
        builder.seckillSku(seckillSku);
        builder.inventory(new Inventory(productSkuDO.getInventory()));
        builder.hasImageFlag(productSkuDO.getHasImageFlag());
        builder.limitBuy(new LimitBuy(productSkuDO.getLimitBuy()));
        builder.skuCode(productSkuDO.getSkuCode());
        builder.outId(new OutId(productSkuDO.getOutId()));
        builder.supplyPrice(new SupplyPrice(productSkuDO.getSupplyPrice()));
        builder.weight(new Weight(productSkuDO.getWeight()));
        return builder.rebuild();
    }
}
