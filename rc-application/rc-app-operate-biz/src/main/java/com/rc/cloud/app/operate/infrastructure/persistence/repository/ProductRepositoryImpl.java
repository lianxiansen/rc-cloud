package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.product.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductDictMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Repository
public class ProductRepositoryImpl implements  ProductRepository {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductDictMapper productDictMapper;

    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Autowired
    private ProductImageRepositoryImpl productImageRepository;
    @Override
    public void saveProductEntry(Product productEntry) {

    }

    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDO::getId, productId.id());
        return convert2Product(this.productMapper.selectOne(wrapper)) ;
    }

    public boolean exist(ProductId productId) {
        LambdaQueryWrapperX<ProductDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDO::getId, productId.id());
        return this.productMapper.exists(wrapper);
    }

    @Override
    public ProductId nextProductId(){
        return new ProductId(remoteIdGeneratorService.uidGenerator());
    }
    @Override
    public ProductImageId nextProductImageId(){
        return new ProductImageId(remoteIdGeneratorService.uidGenerator());
    }


    @Override
    public  PageResult<ProductDO> getProductPageList(ProductListQueryDTO productListQueryDTO){
        return productMapper.selectPage(productListQueryDTO);
    }

    @Override
    public List<ProductDictDO> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictDO>();
        LambdaQueryWrapperX<ProductDictDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictDO::getProductId, productId.id());
        return this.productDictMapper.selectList(wrapper);
    }


    @Override
    public List<ProductImageDO> getProductImageByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductImageDO>();
        LambdaQueryWrapperX<ProductImageDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImageDO::getProductId, productId.id());
        return this.productImageMapper.selectList(wrapper);
    }

    /**
     * ProductDO 转领域模型
     * @param productDO
     * @return
     */
    private Product convert2Product(ProductDO productDO){
        ProductId productId=new ProductId(productDO.getId());
        TenantId tenantId = new TenantId(productDO.getTenantId());

        Product product=new Product(productId,tenantId,new Name(productDO.getName()));

        List<ProductImageDO> productImageByProductIds = getProductImageByProductId(productId);
        List<String> urls=new ArrayList<>();
        for (ProductImageDO productImageByProductId : productImageByProductIds) {
            urls.add(productImageByProductId.getUrl());
        }
        //商品图片
        product.setProductImage(urls);


        return product;
    }




}




