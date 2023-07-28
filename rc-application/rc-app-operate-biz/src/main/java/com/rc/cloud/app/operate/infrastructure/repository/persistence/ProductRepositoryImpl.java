package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductImageConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductImagePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
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
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;


    public ProductRepositoryImpl() {
    }

    private boolean judgeAttributeChange(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttribute ori = getProductAttributeByProductId(product.getId());
        String attr1 = JSON.toJSONString(productAttribute);
        String attr2 = JSON.toJSONString(ori.getAttributes());
        if (attr1.equals(attr2)) {
            return false;
        } else {
            return true;
        }
    }



    private List<ProductImage> getProductImageByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId.id());
        return ProductImageConvert.convertList(this.productImageMapper.selectList(wrapper));
    }




    private int removeProductImageByUrlAndSortAndType(String url, int sort, int type) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getUrl, url);
        wrapper.eq(ProductImagePO::getSort, sort);
        wrapper.eq(ProductImagePO::getImage_type, type);
        return productImageMapper.delete(wrapper);
    }


    /**
     * 更新商品图片
     * @param product
     * @return
     */
    public int updateProductImageByProduct(Product product) {
        List<ProductImage> newList = new ArrayList<>();
        if(product.getSizeImages()!=null){
            newList.addAll(product.getSizeImages());
        }
        if(product.getMasterImages()!=null){
            newList.addAll(product.getMasterImages());
        }
        List<ProductImage> oriList = getProductImageByProductId(product.getId());
        List<ProductImage> addList = CollectionUtil.subtractToList(newList, oriList);
        List<ProductImage> removeList = CollectionUtil.subtractToList(oriList, newList);
        removeList.forEach(x ->
                removeProductImageByUrlAndSortAndType(x.getUrl().getValue(), x.getSort().getValue(), x.getType().value)
        );
        batchInsertProductImage(addList, product.getId(), product.getTenantId());
        return 1;
    }



    private int batchInsertProductImage(List<ProductImage> productImageList, ProductId productId, TenantId tenantId) {
        if (productImageList != null && productImageList.size() > 0) {
            productImageList.forEach(
                    x -> {
                        ProductImagePO productImagePO = ProductImageConvert.convert(x);
                        productImagePO.setTenantId(tenantId.id());
                        productImagePO.setProductId(productId.id());
                        productImagePO.setImage_type(x.getType().value);
                        this.productImageMapper.insert(productImagePO);
                    }
            );
        }
        return 1;
    }

    private ProductAttribute getProductAttributeByProductId(ProductId productId) {

        LambdaQueryWrapperX wrapperX = new LambdaQueryWrapperX<ProductAttributePO>();
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId.id());
        return ProductAttributeConvert.convert(this.productAttributeMapper.selectOne(wrapper));

    }


    private int insertProductAttribute(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert.convert(productAttribute);
        productAttributePO.setProductId(product.getId().id());
        productAttributePO.setTenantId(product.getTenantId().id());
        return this.productAttributeMapper.insert(productAttributePO);
    }

    private int updateProductAttributeByProductId(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert.convert(productAttribute);
        productAttributePO.setProductId(product.getId().id());
        productAttributePO.setTenantId(product.getTenantId().id());
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, product.getId().id());
        return this.productAttributeMapper.update(productAttributePO, wrapper);
    }

    @Override
    public int insertProduct(Product product) {
        ProductPO productPO = ProductConvert.convert(product);
        this.productMapper.insert(productPO);
        if (product.getSizeImages() != null && product.getSizeImages().size() > 0) {
            batchInsertProductImage(product.getSizeImages(), product.getId(), product.getTenantId());
        }
        if (product.getMasterImages() != null && product.getMasterImages().size() > 0) {
            batchInsertProductImage(product.getMasterImages(), product.getId(), product.getTenantId());
        }
        if (product.getProductAttribute() != null) {
            insertProductAttribute(product);
        }
        return 1;
    }

    /**
     * 更新商品
     * 同时更新商品图片以及商品属性
     * @param product
     * @return
     */
    @Override
    public int updateProduct(Product product) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, product.getId().id());
        ProductPO productPO = ProductConvert.convert(product);
        this.productMapper.update(productPO, wrapper);
        updateProductImageByProduct(product);
        updateProductAttributeByProductId(product);
        return 1;
    }


    /**
     * 获取商品
     * 同时需要查询商品图片，商品属性
     * @param productId
     * @return
     */
    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        Product product = ProductConvert.convert(this.productMapper.selectOne(wrapper));
        List<ProductImage> productImageList = getProductImageByProductId(productId);
        product.addProductImageList(productImageList);
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

    /**
     * 分页获取商品
     * 同时需要查询商品图片，商品属性
     * @param query
     * @return
     */
    @Override
    public PageResult<Product> getProductPageList(ProductListQueryDTO query) {
        PageResult<Product> productPageResult = new PageResult<>();
        PageResult<ProductPO> productDOPageResult = productMapper.selectPage(query);
        productPageResult.setTotal(productDOPageResult.getTotal());
        List<Product> productList = ProductConvert.convertList(productDOPageResult.getList());
        for (Product product : productList) {
            List<ProductImage> productImageList = getProductImageByProductId(product.getId());
            product.addProductImageList(productImageList);
            ProductAttribute productAttribute = getProductAttributeByProductId(product.getId());
            product.setProductAttribute(productAttribute);
        }
        productPageResult.setList(productList);
        return productPageResult;
    }

    @Override
    public boolean existsByBrandId(BrandId brandId) {
        return false;
    }


    @Override
    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
        return false;
    }

    @Override
    public List<Product> selectBatchIds(List<ProductId> productIds) {
        List<Product> products = new ArrayList<>();
        productIds.forEach(
                x-> products.add(findById(x))
        );
        return products;
    }

    /**
     * 硬核删除商品
     * 需要删除商品图片
     * 需要删除商品属性
     * @param productId
     */
    @Override
    public void deleteProduct(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        this.productMapper.delete(wrapper);
        deleteProductImageByProductId(productId);
        deleteProductAttributeByProductId(productId);
    }


    private void deleteProductAttributeByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId);
        this.productAttributeMapper.delete(wrapper);
    }


    private void deleteProductImageByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId);
        productImageMapper.delete(wrapper);
    }



}




