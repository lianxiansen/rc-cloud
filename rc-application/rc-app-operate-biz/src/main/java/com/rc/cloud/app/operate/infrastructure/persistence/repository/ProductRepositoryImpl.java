package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductImageConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.*;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ProductRepositoryImpl
 * @Author: liandy
 * @Date: 2023/6/23 16:01
 * @Description: TODO
 */
@Repository
public class ProductRepositoryImpl implements  ProductRepository {

    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;



    public ProductRepositoryImpl() {
    }

    @Override
    public ProductId nextId() {
        return new ProductId(remoteIdGeneratorService.uidGenerator());
    }

    @Override
    public String nextProductImageId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    @Override
    public String nextProductAttributeId() {
        return remoteIdGeneratorService.uidGenerator();
    }

    @Override
    public List<ProductImage> getProductImageByProductId(ProductId productId) {
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductImagePO>();
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId.id());
        return ProductImageConvert.INSTANCE.convertList(this.productImageMapper.selectList(wrapper));
    }

    @Override
    public int removeProductImageByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId);
        return productImageMapper.delete(wrapper);
    }

    @Override
    public int insertProductImage(ProductImage productImage) {
        ProductImagePO productImagePO = ProductImageConvert.INSTANCE.convert(productImage);
        return this.productImageMapper.insert(productImagePO);
    }

    @Override
    public int batchSaveProductImage(List<ProductImage> productImageList) {
        productImageList.forEach(
                x-> insertProductImage(x)
        );
        return 1;
    }

    @Override
    public ProductAttribute getProductAttributeByProductId(ProductId productId) {

        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductAttributePO>();
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId.id());
        return ProductAttributeConvert.INSTANCE.convert(this.productAttributeMapper.selectOne(wrapper));

    }

    @Override
    public int removeProductAttributeByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId);
        return this.productAttributeMapper.delete(wrapper);
    }

    @Override
    public int insertProductAttribute(ProductAttribute productAttribute) {
        ProductAttributePO productAttributePO = ProductAttributeConvert.INSTANCE.convert(productAttribute);
        return this.productAttributeMapper.insert(productAttributePO);
    }

    @Override
    public int insertProduct(Product product) {
        if(exist(product.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductPO productPO = ProductConvert.INSTANCE.convert(product);
        return this.productMapper.insert(productPO);
    }

    @Override
    public int updateProduct(Product product) {
        if(!exist(product.getId())){
            throw new IllegalArgumentException("该商品不存在");
        }
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, product.getId().id());
        ProductPO productPO = ProductConvert.INSTANCE.convert(product);
        return this.productMapper.update(productPO,wrapper);
    }

    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return ProductConvert.INSTANCE.convert(this.productMapper.selectOne(wrapper));
    }

    @Override
    public boolean exist(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return this.productMapper.exists(wrapper);
    }

    @Override
    public PageResult<Product> getProductPageList(ProductListQueryDTO query) {
        PageResult<Product> productPageResult=new PageResult<>();
        PageResult<ProductPO> productDOPageResult = productMapper.selectPage(query);
        productPageResult.setTotal(productDOPageResult.getTotal());
        List<Product> productList= ProductConvert.INSTANCE.convertList(productDOPageResult.getList());
        productPageResult.setList(productList);
        return productPageResult;
    }

}




