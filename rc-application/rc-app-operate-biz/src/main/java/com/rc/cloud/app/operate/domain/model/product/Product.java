package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.common.ProductOriginEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.domain.AggregateRoot;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 产品
 */
public class Product extends AggregateRoot {


    public Product(ProductId id, TenantId tenantId, Name name){
        init();
        setId(id);
        setTenantId(tenantId);
        setName(name);

    }

    private void init(){
        this.sort=new Sort(99);
        this.type=new Type(0);
        this.outid=new OutId(null);
        this.firstCategory=new CategoryName("");
        this.secondCategory=new CategoryName("");
        this.thirdCategory=new CategoryName("");
        this.productListImage=new Url("");
        this.remark=new Remark("");
        this.tag =new Tag("");
        this.newFlag=false;
        this.publicFlag=false;
        this.onshelfStatus= new OnshelfStatus(ProductShelfStatusEnum.InitShelf.value);
        this.video=new Video(null);
        this.spuCode=new SpuCode(null);
        this.origin =new Origin(ProductOriginEnum.Self.value);
        this.packingLowestBuy=new PackingLowestBuy(false);
    }

    private ProductId id;
    private TenantId tenantId;
    private Name name;
    private CategoryName firstCategory;
    private CategoryName secondCategory;
    private CategoryName thirdCategory;
    private Url productListImage;

    public Url getProductListImage() {
        return productListImage;
    }

    public void setProductListImage(Url productListImage) {
        AssertUtils.assertArgumentNotNull(productListImage, "productListImage must not be null");
        this.productListImage = productListImage;
    }

    /**
     * 如果只有两
     * @param firstCategory
     * @param secondCategory
     * @param thirdCategory
     */
    public Product setCategory(CategoryName firstCategory, CategoryName secondCategory,CategoryName thirdCategory){
        AssertUtils.assertArgumentNotNull(firstCategory, "firstCategory must not be null");
        AssertUtils.assertArgumentNotNull(secondCategory, "secondCategory must not be null");
        AssertUtils.assertArgumentNotNull(thirdCategory, "thirdCategory must not be null");
        this.firstCategory=firstCategory;
        this.secondCategory=secondCategory;
        this.thirdCategory=thirdCategory;
        return this;
    }

    /**
     * 产品简介
     */
    private Remark remark;

    public Product setRemark(Remark remark){
        AssertUtils.assertArgumentNotNull(remark, "remark must not be null");
        this.remark= remark;
        return this;
    }

    /**
     * 产品标签
     * 多个标签可以用逗号分割
     */
    private Tag tag;

    public Product setTag(Tag tag){
        AssertUtils.assertArgumentNotNull(tag, "tag must not be null");
        this.tag = tag;
        return this;
    }
    /**
     * 品牌ID
     */
    private BrandId brandId;


    public Product setBrandId(BrandId brandId){
        AssertUtils.assertArgumentNotNull(brandId, "brandId must not be null");
        this.brandId = brandId;
        return this;
    }

    /**
     * 自定义标识
     */
    private CustomClassificationId customClassificationId;



    public Product setCustomClassificationId(CustomClassificationId customClassificationId){
        AssertUtils.assertArgumentNotNull(customClassificationId, "customClassificationId must not be null");
        this.customClassificationId = customClassificationId;
        return this;
    }


    private Boolean newFlag;
    private Boolean publicFlag;
    private Recommend recommendFlag;


    public Boolean getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Boolean newFlag) {
        AssertUtils.assertArgumentNotNull(newFlag, "newFlag must not be null");
        this.newFlag = newFlag;
    }

    public Boolean getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(Boolean publicFlag) {
        AssertUtils.assertArgumentNotNull(publicFlag, "publicFlag must not be null");
        this.publicFlag = publicFlag;
    }

    public Recommend getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(Recommend recommendFlag) {
        AssertUtils.assertArgumentNotNull(recommendFlag, "recommendFlag must not be null");
        this.recommendFlag = recommendFlag;
    }

    /**
     * 超级单品
     */
    private Explosives explosives;

    public void setExplosives(Explosives explosives){
        this.explosives = explosives;
    }



    /**
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    private OnshelfStatus onshelfStatus;

    public void setOnshelfStatus(OnshelfStatus onshelfStatus){
        AssertUtils.assertArgumentNotNull(onshelfStatus, "onshelfStatus must not be null");
        this.onshelfStatus = onshelfStatus;
    }


    /**
     * 视频
     */
    private Video video;

    public void setVideo(Video video){
        AssertUtils.assertArgumentNotNull(video, "video must not be null");
        this.video = video;

    }


    /**
     * 商品code
     */
    private SpuCode spuCode;

    public SpuCode getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(SpuCode spuCode) {
        AssertUtils.assertArgumentNotNull(spuCode, "spuCode must not be null");
        this.spuCode = spuCode;
    }


    private Origin origin;
    private OutId outid;
    private Type type;

    /**
     * 最低起购量
     */
    private PackingLowestBuy packingLowestBuy;


    /**
     * 排序
     */
    private Sort sort;



    public void setId(ProductId id){
        AssertUtils.assertArgumentNotNull(id,"ProductId must not be null");
        this.id=id;
    }
    public void setTenantId(TenantId tenantId){
        AssertUtils.assertArgumentNotNull(tenantId,"TenantId must not be null");
        this.tenantId = tenantId;
    }

    public void setName(Name name){
        AssertUtils.assertArgumentNotNull(name,"Name must not be null");
        this.name = name;
    }

    public Name getName(){
        return this.name;
    }


    /**
     * 上架
     */
    public void Onshelf(){
        if(this.onshelfStatus.getValue() == 0){
            this.onshelfStatus.setValue(1);
        }
        else{
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_ONSHELF_ERROR);
        }
    }

    /**
     * 下架
     */
    public void Offshelf(){
        if(this.onshelfStatus.getValue() == 1){
            this.onshelfStatus.setValue(0);
        }
        else{
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_OFFSHELF_ERROR);
        }
    }



    public void setType(Type type){
        AssertUtils.assertArgumentNotNull(type,"Type must not be null");
        this.type = type;
    }


    @Override
    public ProductId getId(){
        return id;
    }


    private ProductAttribute productAttribute;


    public TenantId getTenantId() {
        return tenantId;
    }

    public CategoryName getFirstCategory() {
        return firstCategory;
    }

    public CategoryName getSecondCategory() {
        return secondCategory;
    }

    public CategoryName getThirdCategory() {
        return thirdCategory;
    }

    public Remark getRemark() {
        return remark;
    }

    public Tag getTag() {
        return tag;
    }

    public BrandId getBrandId() {
        return brandId;
    }

    public CustomClassificationId getCustomClassificationId() {
        return customClassificationId;
    }

    public Explosives getExplosives() {
        return explosives;
    }



    public OnshelfStatus getOnshelfStatus() {
        return onshelfStatus;
    }


    public Video getVideo() {
        return video;
    }



    public Origin getOrigin() {
        return origin;
    }

    public OutId getOutid() {
        return outid;
    }

    public Type getType() {
        return type;
    }


    public PackingLowestBuy getLowestBuy() {
        return packingLowestBuy;
    }


    public Sort getSort() {
        return sort;
    }


    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }


    public PackingLowestBuy getPackingLowestBuy() {
        return packingLowestBuy;
    }

    public void setPackingLowestBuy(PackingLowestBuy packingLowestBuy) {
        this.packingLowestBuy = packingLowestBuy;
    }

    private List<ProductImage> masterImages;
    private List<ProductImage> sizeImages;

    private void addMasterImages(ProductImageId id, Url url,Sort sort) {
        if(masterImages==null){
            masterImages=new ArrayList<>();
        }
        ProductImage productImage=new ProductImage(id,url,sort,ProductImageTypeEnum.MasterImage);
        this.masterImages.add(productImage);
    }

    private void addSizeImages(ProductImageId id, Url url,Sort sort) {
        if(sizeImages==null){
            sizeImages=new ArrayList<>();
        }
        ProductImage productImage=new ProductImage(id,url,sort,ProductImageTypeEnum.SizeImage);
        this.sizeImages.add(productImage);
    }

    public void addProductImageList(List<ProductImage> productImageList){
        for (ProductImage productImage : productImageList) {
            if(!existProductImage(productImage)){
                if(productImage.getType()==ProductImageTypeEnum.MasterImage){
                    addMasterImages(productImage.getId(),productImage.getUrl(),productImage.getSort());
                }else  if(productImage.getType()==ProductImageTypeEnum.SizeImage){
                    addSizeImages(productImage.getId(),productImage.getUrl(),productImage.getSort());
                }
            }
        }
    }

    public boolean existProductImage(ProductImage productImage){
        boolean exits=false;
        if(masterImages!=null){
            exits= masterImages.stream().anyMatch(x -> x.equals(productImage));
        }
        if(!exits){
            if(sizeImages!=null){
                exits= sizeImages.stream().anyMatch(x -> x.equals(productImage));
            }
        }
        return exits;
    }

    public List<ProductImage> getMasterImages() {
        return masterImages;
    }

    public List<ProductImage> getSizeImages() {
        return sizeImages;
    }

    private InstallInformation installInformation;

    public InstallInformation getInstallInformation() {
        return installInformation;
    }

    public void setInstallInformation(InstallInformation installInformation) {
        this.installInformation = installInformation;
    }
}
