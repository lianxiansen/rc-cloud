package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductDOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuDOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributeDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImageDO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductSkuRepositoryImpl implements ProductSkuRepository{

    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Autowired
    ProductSkuMapper productSkuMapper;

    @Autowired
    ProductSkuImageMapper productSkuImageMapper;

    @Autowired
    ProductSkuAttributeMapper productSkuAttributeMapper;


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

    public void updateProductSkuImageEntity( ProductSkuImageDO productSkuImageDO){
        LambdaQueryWrapperX<ProductSkuImageDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImageDO::getId, productSkuImageDO.getId());
        productSkuImageMapper.update(productSkuImageDO,wrapper);
    }

    public void updateProductSkuAttributeEntity(ProductSkuAttributeDO productSkuAttributeDO){
        LambdaQueryWrapperX<ProductSkuAttributeDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributeDO::getProductSkuId, productSkuAttributeDO.getProductSkuId());
        productSkuAttributeMapper.update(productSkuAttributeDO,wrapper);
    }



    @Override
    public void insertProductSku(ProductSku productSkuEntity) {

        if(exist(productSkuEntity.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductSkuDO productSkuDO= ProductSkuDOConvert.convert2ProductSkuDO(productSkuEntity);
        productSkuMapper.insert(productSkuDO);

        if(productSkuEntity.getSkuImageList()!=null){
            List<ProductSkuImageDO> productSkuImageDOS = ProductSkuDOConvert.convert2ProductSkuImageDO(productSkuEntity.getId().id()
                    , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuImageList());
            for (ProductSkuImageDO productSkuImageDO : productSkuImageDOS) {
                this.productSkuImageMapper.insert(productSkuImageDO);
            }
        }

        ProductSkuAttributeDO productSkuAttributeDO = ProductSkuDOConvert
                .convert2ProductSkuAttributeDO(productSkuEntity.getId().id()
                , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuAttributes());
        this.productSkuAttributeMapper.insert(productSkuAttributeDO);
    }

    @Override
    public void updateProductSku(ProductSku productSkuEntity) {

        LambdaQueryWrapperX<ProductSkuDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuDO::getId, productSkuEntity.getId().id());
        ProductSkuDO ProductSkuDO = ProductSkuDOConvert.convert2ProductSkuDO(productSkuEntity);
        this.productSkuMapper.update(ProductSkuDO,wrapper);

        if(productSkuEntity.getSkuImageList()!=null){
            List<ProductSkuImageDO> productSkuImageDOS = ProductSkuDOConvert
                    .convert2ProductSkuImageDO(productSkuEntity.getId().id()
                    , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuImageList());
            for (ProductSkuImageDO productSkuImageDO : productSkuImageDOS) {
                updateProductSkuImageEntity(productSkuImageDO);
            }
        }
        ProductSkuAttributeDO ProductSkuAttributeDO = ProductSkuDOConvert.convert2ProductSkuAttributeDO(
                productSkuEntity.getId().id()
                , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuAttributes());
        updateProductSkuAttributeEntity(ProductSkuAttributeDO);
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
        productSku.setLimitBuy(new LimitBuy(productSkuDO.getLimitBuy()));
        productSku.setOutId(new OutId(productSkuDO.getOutId()));
        productSku.setSupplyPrice(new SupplyPrice(productSkuDO.getSupplyPrice()));
        productSku.setWeight(new Weight(productSkuDO.getWeight()));
        return productSku;
    }
}
