package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
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

import javax.annotation.Resource;
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

    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

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



    @Override
    public void removeProductImageEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId);
        productImageMapper.delete(wrapper);
    }


    @Override
    public void removeProductDictEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId);
        productDictMapper.delete(wrapper);
    }

    public void updateProductImageEntity( ProductImagePO productImagePO){
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getId, productImagePO.getId());
        productImageMapper.update(productImagePO,wrapper);
    }


    public void updateProductDictEntity(ProductDictPO productDictPO){
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getId, productDictPO.getId());
        productDictMapper.update(productDictPO,wrapper);
    }


    public void removeProductAttributeEntityByProductId(String productId){
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productId);
        productAttributeMapper.delete(wrapper);
    }

    public void updateProductAttributeEntity(ProductAttributePO productAttributePO){
        LambdaQueryWrapperX<ProductAttributePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductAttributePO::getProductId, productAttributePO.getProductId());
        productAttributeMapper.update(productAttributePO,wrapper);
    }

    public void updateProductDetailEntity(ProductDetailPO productDetailPO){
        LambdaQueryWrapperX<ProductDetailPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDetailPO::getProductId, productDetailPO.getProductId());
        productDetailMapper.update(productDetailPO,wrapper);
    }


    @Override
    public void insertProductEntity(Product productEntity) {
        if(exist(productEntity.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductPO productPO = ProductDOConvert.convert2ProductDO(productEntity);
        this.productMapper.insert(productPO);
        if(productEntity.getProductImages()!=null){
            List<ProductImagePO> productImagePOS = ProductDOConvert.convert2ProductImageDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductImages());
            for (ProductImagePO productImagePO : productImagePOS) {
                this.productImageMapper.insert(productImagePO);
            }
        }
        if(productEntity.getProductDicts()!=null){
            List<ProductDictPO> productDictPOList = ProductDOConvert.convert2ProductDictDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductDicts());
            for (ProductDictPO productDictPO : productDictPOList) {
                this.productDictMapper.insert(productDictPO);
            }
        }
        ProductAttributePO productAttributePO = ProductDOConvert.convert2ProductAttributeDO(productEntity.getId().id()
                , productEntity.getTenantId().id(), productEntity.getProductAttributeEntity());
        this.productAttributeMapper.insert(productAttributePO);

        if(productEntity.getDetail()!=null){
            //商品详情
            ProductDetailPO productDetailPO = ProductDOConvert.convert2ProductDetailDO(
                        productEntity.getDetail().getId()
                    , productEntity.getId().id()
                    , productEntity.getTenantId().id()
                    , productEntity.getDetail().getValue());
            this.productDetailMapper.insert(productDetailPO);
        }
    }

    @Override
    public void updateProductEntity(Product productEntity) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productEntity.getId().id());
        ProductPO productPO = ProductDOConvert.convert2ProductDO(productEntity);
        this.productMapper.update(productPO,wrapper);
        if(productEntity.getProductImages()!=null){
            List<ProductImagePO> productImagePOS = ProductDOConvert.convert2ProductImageDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductImages());
            for (ProductImagePO productImagePO : productImagePOS) {
               updateProductImageEntity(productImagePO);
            }
        }
        if(productEntity.getProductDicts()!=null){
            removeProductDictEntityByProductId(productEntity.getId().id());
            List<ProductDictPO> productDictPOList = ProductDOConvert.convert2ProductDictDO(productEntity.getId().id()
                    , productEntity.getTenantId().id(), productEntity.getProductDicts());
            for (ProductDictPO productDictPO : productDictPOList) {
               updateProductDictEntity(productDictPO);
            }
        }
        //商品属性
        ProductAttributePO productAttributePO = ProductDOConvert.convert2ProductAttributeDO(
                productEntity.getId().id()
                , productEntity.getTenantId().id(), productEntity.getProductAttributeEntity());
        updateProductAttributeEntity(productAttributePO);
        if(productEntity.getDetail()!=null){
            //商品详情
            ProductDetailPO productDetailPO = ProductDOConvert.convert2ProductDetailDO(
                    productEntity.getDetail().getId()
                    , productEntity.getId().id()
                    , productEntity.getTenantId().id()
                    , productEntity.getDetail().getValue());
            updateProductDetailEntity(productDetailPO);
        }

    }


    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return convert2Product(this.productMapper.selectOne(wrapper)) ;
    }

    public boolean exist(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return this.productMapper.exists(wrapper);
    }


    @Override
    public  PageResult<Product> getProductPageList(ProductListQueryDTO productListQueryDTO){

        PageResult<Product> productPageResult=new PageResult<>();

        PageResult<ProductPO> productDOPageResult = productMapper.selectPage(productListQueryDTO);
        productPageResult.setTotal(productDOPageResult.getTotal());
        List<ProductPO> list = productDOPageResult.getList();
        List<Product> productList=new ArrayList<>();
        for (ProductPO productPO : list) {
            Product product= convert2Product(productPO);
            productList.add(product);
        }
        productPageResult.setList(productList);
        return productPageResult;
    }

    @Override
    public List<ProductDictEntity> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictPO>();
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return convert2ProductDict(this.productDictMapper.selectList(wrapper));
    }

    private List<ProductDictEntity> convert2ProductDict(List<ProductDictPO> productDictPOList){
        List<ProductDictEntity> arr=new ArrayList<>();
        for (ProductDictPO productDictPO : productDictPOList) {

            ProductDictEntity productDictEntity=new ProductDictEntity(productDictPO.getId());
            productDictEntity.setKey(productDictPO.getKey());
            productDictEntity.setValue(productDictPO.getValue());
            productDictEntity.setSort(productDictPO.getSortId());
            arr.add(productDictEntity);
        }
        return arr;

    }

    @Override
    public List<ProductImageEntity> getProductImageByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductImagePO>();
        LambdaQueryWrapperX<ProductImagePO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductImagePO::getProductId, productId.id());
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


    private List<ProductImageEntity> convert2ProductImage(List<ProductImagePO> productImagePOList){
        List<ProductImageEntity> urls=new ArrayList<>();
        for (ProductImagePO productImage : productImagePOList) {

            ProductImageEntity productImageEntity=new ProductImageEntity(productImage.getId());
            productImageEntity.setUrl(productImage.getUrl());
            productImageEntity.setSort(productImage.getSortId());
            urls.add(productImageEntity);
        }
        return urls;
    }

    /**
     * ProductDO 转领域模型
     * @param productPO
     * @return
     */
    private Product convert2Product(ProductPO productPO){
        ProductId productId=new ProductId(productPO.getId());
        TenantId tenantId = new TenantId(productPO.getTenantId());
        Product product=new Product(productId,tenantId,new Name(productPO.getName()));
        //设置相册
        List<ProductImageEntity> urls = getProductImageByProductId(productId);
        product.setProductImages(urls);

        product.setId(productId);

        Remark remark = new Remark(productPO.getRemark());
        Tag tag = new Tag(productPO.getTag());
        BrandId brandId = new BrandId(productPO.getBrandId());
        CategoryName firstCategory = new CategoryName(productPO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productPO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productPO.getThirdCategory());
        CustomClassificationId customClassificationId = new CustomClassificationId(productPO.getCustomClassificationId());
        Newest newest = new Newest(productPO.getNewFlag());
        Explosives explosives = null;
        if(productPO.getExplosivesFlag()){
            explosives= new Explosives(productPO.getExplosivesFlag(), productPO.getExplosivesImage());
        }

        Recommend recommend = new Recommend(productPO.getRecommendFlag());
        Open open = new Open(productPO.getPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(productPO.getOnshelfStatus());
        Enable enable = new Enable(productPO.getEnabledFlag());
        Video video = new Video(productPO.getVideoUrl(), productPO.getVideoImg()
                , productPO.getInstallVideoUrl(), productPO.getInstallVideoImg());

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




