package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductSkuImageConvert;
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


    @Override
    public ProductSkuId nextId() {
        return new ProductSkuId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public String nextProductSkuImageId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    @Override
    public String nextProductSkuAttributeId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    public int removeProductSkuImageByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuImagePO::getProductSkuId, productSkuId);
        return this.productSkuImageMapper.delete(wrapper);
    }


    @Override
    public int batchSaveProductSkuImage(ProductSku productSku) {
        List<ProductSkuImage> productSkuImageList =productSku.getSkuImageList();
        if(productSkuImageList!=null && productSkuImageList.size()>0){
            productSkuImageList.forEach(
                    x-> {
                        ProductSkuImagePO po = ProductSkuImageConvert.INSTANCE.convert(x);
                        po.setTenantId(productSku.getTenantId().id());
                        po.setProductSkuId(productSku.getId().id());
                        this.productSkuImageMapper.insert(po);
                    }
            );
        }
        return 1;
    }


    @Override
    public int removeProductSkuAttributeByProductSkuId(ProductSkuId productSkuId) {
        LambdaQueryWrapperX<ProductSkuAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuAttributePO::getProductSkuId, productSkuId);
        return this.productSkuAttributeMapper.delete(wrapper);
    }

    @Override
    public int insertProductSkuAttribute(ProductSku productSku) {
        ProductSkuAttributePO productSkuAttributePO = ProductSkuAttributeConvert.convert(productSku.getProductSkuAttributeEntity());
        productSkuAttributePO.setProductSkuId(productSku.getId().id());
        productSkuAttributePO.setTenantId(productSku.getTenantId().id());
        return this.productSkuAttributeMapper.insert(productSkuAttributePO);
    }


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
        return ProductSkuConvert.convert(ProductSkuPO);
    }



    @Override
    public int insertProductSku(ProductSku productSku) {

        if(exist(productSku.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductSkuPO productSkuPO = ProductSkuConvert.convert(productSku);
        return productSkuMapper.insert(productSkuPO);
    }

    @Override
    public int updateProductSku(ProductSku productSku) {
        if(!exist(productSku.getId())){
            throw new IllegalArgumentException("该商品不存在");
        }
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getId, productSku.getId().id());
        ProductSkuPO ProductSkuPO = ProductSkuConvert.convert(productSku);
        return this.productSkuMapper.update(ProductSkuPO,wrapper);
    }


    @Override
    public List<ProductSku> getProductSkuListByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductSkuPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductSkuPO::getProductId, productId.id());
        List<ProductSkuPO> productSkuPOList = this.productSkuMapper.selectList(wrapper);
        List<ProductSku> resList = ProductSkuConvert.convertList(productSkuPOList);
        return resList;
    }



}
