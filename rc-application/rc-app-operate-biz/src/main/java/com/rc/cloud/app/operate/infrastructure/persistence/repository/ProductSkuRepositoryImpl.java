package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.common.IdRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuDOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductSkuMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuPO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuImagePO;
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
    @Resource
    private IdRepository idRepository;



    @Override
    public boolean exist(ProductSkuId productSkuId){
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuId.id());
        return this.productSkuMapper.exists(wrapper);
    }

    @Override
    public ProductSku findById(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuId.id());
        ProductSkuPO ProductSkuPO = this.productSkuMapper.selectOne(wrapper);
        return convert2ProductSkuEntity(ProductSkuPO);
    }

    public void updateProductSkuImageEntity( ProductSkuImagePO productSkuImagePO){
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getId, productSkuImagePO.getId());
        productSkuImageMapper.update(productSkuImagePO,wrapper);
    }

    public void updateProductSkuAttributeEntity(ProductSkuAttributePO productSkuAttributePO){
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSkuAttributePO.getProductSkuId());
        productSkuAttributeMapper.update(productSkuAttributePO,wrapper);
    }



    @Override
    public void insertProductSku(ProductSku productSkuEntity) {

        if(exist(productSkuEntity.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductSkuPO productSkuPO = ProductSkuDOConvert.convert2ProductSkuDO(productSkuEntity);
        productSkuMapper.insert(productSkuPO);

        if(productSkuEntity.getSkuImageList()!=null){
            List<ProductSkuImagePO> productSkuImagePOS = ProductSkuDOConvert.convert2ProductSkuImageDO(productSkuEntity.getId().id()
                    , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuImageList());
            for (ProductSkuImagePO productSkuImagePO : productSkuImagePOS) {
                this.productSkuImageMapper.insert(productSkuImagePO);
            }
        }

        ProductSkuAttributePO productSkuAttributePO = ProductSkuDOConvert
                .convert2ProductSkuAttributeDO(productSkuEntity.getId().id()
                , productSkuEntity.getTenantId().id(), productSkuEntity.getProductSkuAttributeEntity());
        this.productSkuAttributeMapper.insert(productSkuAttributePO);
    }

    @Override
    public void updateProductSku(ProductSku productSkuEntity) {

        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSkuEntity.getId().id());
        ProductSkuPO ProductSkuPO = ProductSkuDOConvert.convert2ProductSkuDO(productSkuEntity);
        this.productSkuMapper.update(ProductSkuPO,wrapper);

        if(productSkuEntity.getSkuImageList()!=null){
            List<ProductSkuImagePO> productSkuImagePOS = ProductSkuDOConvert
                    .convert2ProductSkuImageDO(productSkuEntity.getId().id()
                    , productSkuEntity.getTenantId().id(), productSkuEntity.getSkuImageList());
            for (ProductSkuImagePO productSkuImagePO : productSkuImagePOS) {
                updateProductSkuImageEntity(productSkuImagePO);
            }
        }
        ProductSkuAttributePO ProductSkuAttributePO = ProductSkuDOConvert.convert2ProductSkuAttributeDO(
                productSkuEntity.getId().id()
                , productSkuEntity.getTenantId().id(), productSkuEntity.getProductSkuAttributeEntity());
        updateProductSkuAttributeEntity(ProductSkuAttributePO);
    }


    @Override
    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getProductId, productId.id());
        List<ProductSkuPO> productSkuPOList = this.productSkuMapper.selectList(wrapper);

        List<ProductSku> resList =new ArrayList<>();
        for (ProductSkuPO productSkuPO : productSkuPOList) {
            ProductSku productSkuEntity = convert2ProductSkuEntity(productSkuPO);
            resList.add(productSkuEntity);
        }
        return resList;
    }


    private ProductSku convert2ProductSkuEntity(ProductSkuPO productSkuPO){
        ProductId productId=new ProductId(productSkuPO.getProductId());
        ProductSkuId id =new ProductSkuId(idRepository.nextId());
        TenantId tenantId = new TenantId(productSkuPO.getTenantId());
        Price price=new Price();
        price.setValue(productSkuPO.getPrice());
        ProductSku productSku=new ProductSku(id,productId,tenantId, price);
        //秒杀信息
        SeckillSku seckillSku=new SeckillSku();
        seckillSku.setSeckillInventory(new Inventory(productSkuPO.getSeckillInventory()));
        seckillSku.setSeckillPrice(new Price(productSkuPO.getPrice()));
        seckillSku.setSeckillTotalInventory(new TotalInventory(productSkuPO.getSeckillTotalInventory()));
        seckillSku.setSeckillLimitBuy(new LimitBuy(productSkuPO.getSeckillLimitBuy()));
        productSku.setSeckillSku(seckillSku);
        productSku.setInventory(new Inventory(productSkuPO.getInventory()));
        productSku.setLimitBuy(new LimitBuy(productSkuPO.getLimitBuy()));
        productSku.setOutId(new OutId(productSkuPO.getOutId()));
        productSku.setSupplyPrice(new SupplyPrice(productSkuPO.getSupplyPrice()));
        productSku.setWeight(new Weight(productSkuPO.getWeight()));
        return productSku;
    }
}
