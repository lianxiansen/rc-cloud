package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.*;
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
    private ProductAttributeMapper productAttributeMapper;
    @Autowired
    private ProductDictMapper productDictMapper;

    @Autowired
    public ProductSkuImageMapper productSkuImageMapper;

    @Autowired
    private ProductSkuAttributeMapper productSkuAttributeMapper;


    public ProductRepositoryImpl() {
    }

    private boolean judgeAttributeChange(Product product){
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttribute ori = getProductAttributeByProductId(product.getId());
        String attr1 = JSON.toJSONString(productAttribute);
        String attr2 = JSON.toJSONString(ori.getAttributes());
        if(attr1.equals(attr2)){
            return false;
        }else{
            return true;
        }
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
    public int removeProductImageByUrlAndSortId(String url ,int sortId) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getUrl, url);
        wrapper.eq(ProductImagePO::getSortId, sortId);
        return productImageMapper.delete(wrapper);
    }


    public int updateProductImageByProductId(Product product){
        List<ProductImage> newList = product.getProductImages();
        List<ProductImage> oriList = getProductImageByProductId(product.getId());
        List<ProductImage> addList = CollectionUtil.subtractToList(newList, oriList);
        List<ProductImage> removeList = CollectionUtil.subtractToList(oriList, newList);
        removeList.forEach(x->
                removeProductImageByUrlAndSortId(x.getUrl(),x.getSort())
                );
        batchSaveProductImage(addList,product.getId().id(),product.getTenantId().id());
        return 1;
    }

    @Override
    public int batchSaveProductImage(List<ProductImage> productImageList,String productId,String tenantId) {
        if(productImageList!=null && productImageList.size()>0){
            productImageList.forEach(
                    x-> {
                        ProductImagePO productImagePO = ProductImageConvert.INSTANCE.convert(x);
                        productImagePO.setTenantId(tenantId);
                        productImagePO.setProductId(productId);
                        this.productImageMapper.insert(productImagePO);
                    }
            );
        }
        return 1;
    }

    @Override
    public ProductAttribute getProductAttributeByProductId(ProductId productId) {

        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductAttributePO>();
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId.id());
        return ProductAttributeConvert.convert(this.productAttributeMapper.selectOne(wrapper));

    }

    @Override
    public int removeProductAttributeByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId);
        return this.productAttributeMapper.delete(wrapper);
    }

    @Override
    public int insertProductAttribute(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert.convert(productAttribute);
        productAttributePO.setProductId(product.getId().id());
        productAttributePO.setTenantId(product.getTenantId().id());
        return this.productAttributeMapper.insert(productAttributePO);
    }

    public int updateProductAttribute(Product product){
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert.convert(productAttribute);
        productAttributePO.setProductId(product.getId().id());
        productAttributePO.setTenantId(product.getTenantId().id());
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, product.getId().id());
        return this.productAttributeMapper.update(productAttributePO,wrapper);
    }

    @Override
    public int insertProduct(Product product) {
        ProductPO productPO = ProductConvert.convert(product);
        this.productMapper.insert(productPO);
        if(product.getProductImages()!=null && product.getProductImages().size()>0){
            batchSaveProductImage(product.getProductImages(),product.getId().id(),product.getTenantId().id());
        }
        if(product.getProductAttribute()!=null){
            insertProductAttribute(product);
        }
        return 1;
    }

    @Override
    public int updateProduct(Product product) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, product.getId().id());
        ProductPO productPO = ProductConvert.convert(product);
        this.productMapper.update(productPO,wrapper);
        updateProductImageByProductId(product);
        updateProductAttribute(product);
        return 1;
    }

    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        Product product = ProductConvert.convert(this.productMapper.selectOne(wrapper));
        List<ProductImage> productImageList = getProductImageByProductId(productId);
        product.setProductImages(productImageList);
        ProductAttribute productAttribute = getProductAttributeByProductId(productId);
        product.setProductAttribute(productAttribute);
        return product;
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
        List<Product> productList= ProductConvert.convertList(productDOPageResult.getList());
        for (Product product : productList) {
            List<ProductImage> productImageList = getProductImageByProductId(product.getId());
            product.setProductImages(productImageList);
            ProductAttribute productAttribute = getProductAttributeByProductId(product.getId());
            product.setProductAttribute(productAttribute);
        }
        productPageResult.setList(productList);
        return productPageResult;
    }

//    @Override
//    public List<ProductImage> getProductImageByProductId(ProductId productId){
//        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductImagePO>();
//        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
//        wrapper.eq(ProductImagePO::getProductId, productId.id());
//        return convert2ProductImage(this.productImageMapper.selectList(wrapper));
//    }

//    @Override
//    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
//        return false;
//    }


//    @Override
//    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
//        return false;
//    }

    @Override
    public boolean existsByBrandId(BrandId brandId) {
        return false;
    }


    @Override
    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
        return false;
    }




}



