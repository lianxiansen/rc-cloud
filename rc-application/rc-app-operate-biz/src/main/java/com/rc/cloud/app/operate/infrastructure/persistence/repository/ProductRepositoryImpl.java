package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.dto.ProductListQueryDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductDictId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductAttributeConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.convert.ProductImageConvert;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductAttributeMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductDictMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductImageMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.mapper.ProductMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDictPO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImagePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductPO;
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
    private ProductAttributeMapper productAttributeMapper;
    @Autowired
    private ProductDictMapper productDictMapper;


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
    public int batchSaveProductImage(Product product) {
        List<ProductImage> productImageList =product.getProductImages();
        if(productImageList!=null && productImageList.size()>0){
            productImageList.forEach(
                    x-> {
                        ProductImagePO productImagePO = ProductImageConvert.INSTANCE.convert(x);
                        productImagePO.setTenantId(product.getTenantId().id());
                        productImagePO.setProductId(product.getId().id());
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

    @Override
    public int insertProduct(Product product) {
        if(exist(product.getId())){
            throw new IllegalArgumentException("该商品已存在");
        }
        ProductPO productPO = ProductConvert.convert(product);
        return this.productMapper.insert(productPO);
    }

    @Override
    public int updateProduct(Product product) {
        if(!exist(product.getId())){
            throw new IllegalArgumentException("该商品不存在");
        }
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, product.getId().id());
        ProductPO productPO = ProductConvert.convert(product);
        return this.productMapper.update(productPO,wrapper);
    }

    @Override
    public Product findById(ProductId productId) {
        LambdaQueryWrapperX<ProductPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductPO::getId, productId.id());
        return ProductConvert.convert(this.productMapper.selectOne(wrapper));
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
        productPageResult.setList(productList);
        return productPageResult;
    }
    @Override
    public List<ProductDict> getProductDictByProductId(ProductId productId){
        LambdaQueryWrapperX wrapperX=new LambdaQueryWrapperX<ProductDictPO>();
        LambdaQueryWrapperX<ProductDictPO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(ProductDictPO::getProductId, productId.id());
        return convert2ProductDict(this.productDictMapper.selectList(wrapper));
    }

    private List<ProductDict> convert2ProductDict(List<ProductDictPO> productDictPOList){
        List<ProductDict> arr=new ArrayList<>();
        for (ProductDictPO productDictPO : productDictPOList) {

            ProductDict ProductDict=new ProductDict(new ProductDictId(productDictPO.getId()));
            ProductDict.setKey(productDictPO.getKey());
            ProductDict.setValue(productDictPO.getValue());
            ProductDict.setSort(productDictPO.getSortId());
            arr.add(ProductDict);
        }
        return arr;

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
    public void insertProductEntity(Product product) {

    }

    @Override
    public void updateProductEntity(Product product) {

    }


    private List<ProductImage> convert2ProductImage(List<ProductImagePO> productImagePOList){
        List<ProductImage> urls=new ArrayList<>();
        for (ProductImagePO productImage : productImagePOList) {

            ProductImage ProductImage=new ProductImage(new ProductImageId(productImage.getId()));
            ProductImage.setUrl(productImage.getUrl());
            ProductImage.setSort(productImage.getSortId());
            urls.add(ProductImage);
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
        List<ProductImage> urls = getProductImageByProductId(productId);
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




