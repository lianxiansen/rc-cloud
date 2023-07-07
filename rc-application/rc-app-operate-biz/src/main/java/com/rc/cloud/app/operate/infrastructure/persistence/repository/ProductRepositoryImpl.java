package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDictEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductImageEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductDOConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.*;
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
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;


    public ProductRepositoryImpl() {
    }

    public void removeProductImageEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductImageDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImageDO::getProductId, productId);
        productImageMapper.delete(wrapper);
    }


    public void removeProductDictEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductDictDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictDO::getProductId, productId);
        productDictMapper.delete(wrapper);
    }

    public void updateProductImageEntity( ProductImageDO productImageDO){
        LambdaQueryWrapperX<ProductImageDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImageDO::getId, productImageDO.getId());
        productImageMapper.update(productImageDO,wrapper);
    }


    public void updateProductDictEntity(ProductDictDO productDictDO){
        LambdaQueryWrapperX<ProductDictDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictDO::getId, productDictDO.getId());
        productDictMapper.update(productDictDO,wrapper);
    }


    public void removeProductAttributeEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductAttributeDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributeDO::getProductId, productId);
        productAttributeMapper.delete(wrapper);
    }

    public void updateProductAttributeEntity(ProductAttributeDO productAttributeDO){
        LambdaQueryWrapperX<ProductAttributeDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributeDO::getProductId, productAttributeDO.getProductId());
        productAttributeMapper.update(productAttributeDO,wrapper);
    }

    public void updateProductDetailEntity(ProductDetailDO productDetailDO){
        LambdaQueryWrapperX<ProductDetailDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailDO::getProductId, productDetailDO.getProductId());
        productDetailMapper.update(productDetailDO,wrapper);
    }


    @Override
    public void insertProductEntity(Product productEntity) {
        if(exist(productEntity.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductDO productDO = ProductDOConvert.convert2ProductDO(productEntity);
        this.productMapper.insert(productDO);
        if(productEntity.getProductImages()!=null){
            List<ProductImageDO> productImageDOS = ProductDOConvert.convert2ProductImageDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductImages());
            for (ProductImageDO productImageDO : productImageDOS) {
                this.productImageMapper.insert(productImageDO);
            }
        }
        if(productEntity.getProductDicts()!=null){
            List<ProductDictDO> productDictDOList = ProductDOConvert.convert2ProductDictDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductDicts());
            for (ProductDictDO productDictDO : productDictDOList) {
                this.productDictMapper.insert(productDictDO);
            }
        }
        ProductAttributeDO productAttributeDO = ProductDOConvert.convert2ProductAttributeDO(productEntity.getId().id()
                , productEntity.getTenantId().id(), productEntity.getAttributes());
        this.productAttributeMapper.insert(productAttributeDO);

        if(productEntity.getDetail()!=null){
            //商品详情
            ProductDetailDO productDetailDO = ProductDOConvert.convert2ProductDetailDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getDetail().getValue());
            this.productDetailMapper.insert(productDetailDO);
        }
    }

    @Override
    public void updateProductEntity(Product productEntity) {
        LambdaQueryWrapperX<ProductDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDO::getId, productEntity.getId().id());
        ProductDO productDO = ProductDOConvert.convert2ProductDO(productEntity);
        this.productMapper.update(productDO,wrapper);
        if(productEntity.getProductImages()!=null){
            List<ProductImageDO> productImageDOS = ProductDOConvert.convert2ProductImageDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductImages());
            for (ProductImageDO productImageDO : productImageDOS) {
               updateProductImageEntity(productImageDO);
            }
        }
        if(productEntity.getProductDicts()!=null){
            removeProductDictEntityByProductId(productEntity.getId().id());
            List<ProductDictDO> productDictDOList = ProductDOConvert.convert2ProductDictDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductDicts());
            for (ProductDictDO productDictDO : productDictDOList) {
               updateProductDictEntity(productDictDO);
            }
        }
        //商品属性
        ProductAttributeDO productAttributeDO = ProductDOConvert.convert2ProductAttributeDO(
                productEntity.getId().id()
                , productEntity.getTenantId().id(), productEntity.getAttributes());
        updateProductAttributeEntity(productAttributeDO);
        if(productEntity.getDetail()!=null){
            //商品详情
            ProductDetailDO productDetailDO = ProductDOConvert.convert2ProductDetailDO(
                    productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getDetail().getValue());
            updateProductDetailEntity(productDetailDO);
        }

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
    public  PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO){

        PageResult<Product> productPageResult=new PageResult<>();

        PageResult<ProductDO> productDOPageResult = productMapper.selectPage(productListQueryDTO);
        productPageResult.setTotal(productDOPageResult.getTotal());
        List<ProductDO> list = productDOPageResult.getList();
        List<Product> productList=new ArrayList<>();
        for (ProductDO productDO : list) {
            Product product= convert2Product(productDO);
            productList.add(product);
        }
        productPageResult.setList(productList);
        return productPageResult;
    }

    @Override
    public List<ProductDictEntity> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictDO>();
        LambdaQueryWrapperX<ProductDictDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictDO::getProductId, productId.id());
        return convert2ProductDict(this.productDictMapper.selectList(wrapper));
    }

    private List<ProductDictEntity> convert2ProductDict(List<ProductDictDO> productDictDOList){
        List<ProductDictEntity> arr=new ArrayList<>();
        for (ProductDictDO productDictDO : productDictDOList) {

            ProductDictEntity productDictEntity=new ProductDictEntity();
            productDictEntity.setKey(productDictDO.getKey());
            productDictEntity.setValue(productDictDO.getValue());
            productDictEntity.setSort(productDictDO.getSortId());
            arr.add(productDictEntity);
        }
        return arr;

    }

    @Override
    public List<ProductImageEntity> getProductImageByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductImageDO>();
        LambdaQueryWrapperX<ProductImageDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImageDO::getProductId, productId.id());
        return convert2ProductImage(this.productImageMapper.selectList(wrapper));
    }

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


    private List<ProductImageEntity> convert2ProductImage(List<ProductImageDO> productImageDOList){
        List<ProductImageEntity> urls=new ArrayList<>();
        for (ProductImageDO productImage : productImageDOList) {

            ProductImageEntity productImageEntity=new ProductImageEntity();
            productImageEntity.setUrl(productImage.getUrl());
            productImageEntity.setSort(productImage.getSortId());
            urls.add(productImageEntity);
        }
        return urls;
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
        //设置相册
        List<ProductImageEntity> urls = getProductImageByProductId(productId);
        product.setProductImages(urls);

        product.setId(productId);

        Remark remark = new Remark(productDO.getRemark());
        Tag tag = new Tag(productDO.getTag());
        BrandId brandId = new BrandId(productDO.getBrandId());
        CategoryName firstCategory = new CategoryName(productDO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productDO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productDO.getThirdCategory());
        CustomClassificationId customClassificationId = new CustomClassificationId(productDO.getCustomClassificationId());
        Newest newest = new Newest(productDO.getNewFlag());
        Explosives explosives = null;
        if(productDO.getExplosivesFlag()){
            explosives= new Explosives(productDO.getExplosivesFlag(),productDO.getExplosivesImage());
        }

        Recommend recommend = new Recommend(productDO.getRecommendFlag());
        Open open = new Open(productDO.getPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(productDO.getOnshelfStatus());
        Enable enable = new Enable(productDO.getEnabledFlag());
        Video video = new Video(productDO.getVideoUrl(),productDO.getVideoImg()
                ,productDO.getInstallVideoUrl(),productDO.getInstallVideoImg());

        product.setRemark(remark);
        product.setTag(tag);
        product.setBrandId(brandId);
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        product.setCustomClassificationId(customClassificationId);
        product.setNewest(newest);
        product.setExplosives(explosives);
        product.setRecommend(recommend);
        product.setOpen(open);
        product.setOnshelfStatus(onshelfStatus);
        product.setEnable(enable);
        product.setVideo(video);

        //设置字典
        product.setProductDict(getProductDictByProductId(productId));
        return product;
    }


    @Override
    public boolean existsByProductCategoryId(ProductCategoryId productCategoryId) {
        return false;
    }
}




