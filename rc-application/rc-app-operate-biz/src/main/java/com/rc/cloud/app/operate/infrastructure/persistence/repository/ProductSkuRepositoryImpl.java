package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
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



    @Override
    public ProductSkuId nextId() {
        return new ProductSkuId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public boolean exist(ProductSkuId productSkuId){
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getId, productSkuId.id());
        return this.productSkuMapper.exists(wrapper);
    }

    @Override
    public ProductSku findById(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getId, productSkuId.id());
        ProductSkuDO ProductSkuDO = this.productSkuMapper.selectOne(wrapper);
        return convert2ProductSkuEntity(ProductSkuDO);
    }

    @Override
    public void saveProductSku(ProductSku productSkuEntity) {

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
    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getProductId, productId.id());
        List<ProductSkuDO> productSkuDOList = this.productSkuMapper.selectList(wrapper);

        List<ProductSku> resList =new ArrayList<>();
        for (ProductSkuDO productSkuDO : productSkuDOList) {
            ProductSku productSkuEntity = convert2ProductSkuEntity(productSkuDO);
            resList.add(productSkuEntity);
        }
        return resList;
    }


    private ProductSku convert2ProductSkuEntity(ProductSkuDO productSkuDO ){
        ProductId productId=new ProductId(productSkuDO.getProductId());
        ProductSkuId id = nextId();
        TenantId tenantId = new TenantId(productSkuDO.getTenantId());
        Price price=new Price();
        price.setValue(productSkuDO.getPrice());
        ProductSku productSku=new ProductSku(id,productId,tenantId, price);
        //秒杀信息
        SeckillSku seckillSku=new SeckillSku();
        seckillSku.setSeckillInventory(new Inventory(productSkuDO.getSeckillInventory()));
        seckillSku.setSeckillPrice(new Price(productSkuDO.getPrice()));
        seckillSku.setSeckillTotalInventory(new TotalInventory(productSkuDO.getSeckillTotalInventory()));
        seckillSku.setSeckillLimitBuy(new LimitBuy(productSkuDO.getSeckillLimitBuy()));
        productSku.setSeckillSku(seckillSku);
        productSku.setInventory(new Inventory(productSkuDO.getInventory()));
        productSku.setHasImageFlag(productSkuDO.getHasImageFlag());
        productSku.setLimitBuy(new LimitBuy(productSkuDO.getLimitBuy()));
        productSku.setSkuCode(productSkuDO.getSkuCode());
        productSku.setOutId(new OutId(productSkuDO.getOutId()));
        productSku.setSupplyPrice(new SupplyPrice(productSkuDO.getSupplyPrice()));
        productSku.setWeight(new Weight(productSkuDO.getWeight()));
        return productSku;
    }
}
