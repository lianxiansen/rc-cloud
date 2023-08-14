package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenjianxiang
 * @Date: 2023/7/1 16:01
 * @Description: 包含商品操作、商品属性操作
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private ProductMapper productMapper;


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

    private ProductAttribute getProductAttributeByProductId(ProductId productId) {

        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId.id());
        return ProductAttributeConvert.convertDomain(this.productAttributeMapper.selectOne(wrapper));

    }


    private int insertProductAttribute(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert
                .convertProductAttributePO(productAttribute);
        return this.productAttributeMapper.insert(productAttributePO);
    }

    private int updateProductAttributeByProductId(Product product) {
        ProductAttribute productAttribute = product.getProductAttribute();
        ProductAttributePO productAttributePO = ProductAttributeConvert.convertProductAttributePO(productAttribute);
        productAttributePO.setProductId(product.getId().id());
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, product.getId().id());
        return this.productAttributeMapper.update(productAttributePO, wrapper);
    }

    @Override
    public int insertProduct(Product product) {
        ProductPO productPO = ProductConvert.convertProductPO(product);

        this.productMapper.insert(productPO);

        if (product.getProductAttribute() != null) {
            insertProductAttribute(product);
        }
        return 1;
    }

    /**
     * 更新商品
     * @param product
     * @return
     */
    @Override
    public int updateProduct(Product product) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, product.getId().id());
        ProductPO productPO = ProductConvert.convertProductPO(product);
        this.productMapper.update(productPO, wrapper);
        updateProductAttributeByProductId(product);
        return 1;
    }


    /**
     * 获取商品
     * @param productId
     * @return
     */
    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        ProductPO po = this.productMapper.selectOne(wrapper);
        Product product = convertPO2Product(po);
        if (product != null) return product;
        return null;
    }



    @Override
    public boolean exist(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return this.productMapper.exists(wrapper);
    }

    /**
     * 分页获取商品
     * @param query
     * @return
     */
    @Override
    public PageResult<Product> getProductPageList(ProductListQueryDTO query) {PageResult<Product> productPageResult = new PageResult<>();
        PageResult<ProductPO> productDOPageResult = productMapper.selectPage(query);
        productPageResult.setTotal(productDOPageResult.getTotal());
        List<Product> productList = convertPO2ProductBatch(productDOPageResult.getList());
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
     * 需要删除商品属性
     * @param productId
     */
    @Override
    public void deleteProduct(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        this.productMapper.delete(wrapper);
        deleteProductAttributeByProductId(productId);
    }



    private void deleteProductAttributeByProductId(ProductId productId) {
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId.id());
        this.productAttributeMapper.delete(wrapper);
    }

    @Override
    public List<Product> findByIdBatch(List<ProductId> productIds) {
       List<String> ids= productIds.stream().map(item->item.id()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(productIds)){
            List<ProductPO> pos=this.productMapper.selectBatchIds(ids);
            List<Product> productList = convertPO2ProductBatch(pos);
            return productList;
        }
        return null;
    }

    /**
     * 批量将产品持久对象转为领域对象
     * @param pos
     * @return
     */
    private List<Product> convertPO2ProductBatch(List<ProductPO> pos) {
        List<Product> productList = ProductConvert.convertDomainList(pos);
        for (Product product : productList) {
            ProductAttribute productAttribute = getProductAttributeByProductId(product.getId());
            product.setProductAttribute(productAttribute);
        }
        return productList;
    }

    /**
     * 将产品持久对象转为领域对象
     * @param po
     * @return
     */
    private Product convertPO2Product(ProductPO po) {
        Product product = ProductConvert.convertDomain(po);
        if(product!=null){
            ProductAttribute productAttribute = getProductAttributeByProductId(new ProductId(po.getId()));
            product.setProductAttribute(productAttribute);
            return product;
        }
        return null;
    }

}




