package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductImageId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AggregateRoot;
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

    /**
     * 产品唯一标识
     */
    private ProductId id;

    /**
     * 所属租户
     */
    private TenantId tenantId;
    /**
     * 产品名
     */
    private Name name;

    /**
     * 产品分类标识
     */
    private CategoryName firstCategory;

    private CategoryName secondCategory;

    private CategoryName thirdCategory;


    private String productListImage;

    public String getProductListImage() {
        return productListImage;
    }

    public void setProductListImage(String productListImage) {
        if(StringUtils.isNotEmpty(productListImage)&&!StringUtils.ishttp(productListImage)){
            throw new IllegalArgumentException("http地址无效");
        }
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

    /**
     * 新品
     */
    private Boolean newFlag;
    private Boolean publicFlag;


    public Boolean getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Boolean newFlag) {
        this.newFlag = newFlag;
    }

    public Boolean getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(Boolean publicFlag) {
        this.publicFlag = publicFlag;
    }

    /**
     * 超级单品
     */
    private Explosives explosives;

    /**
     * 推荐
     */
    private Recommend recommend;


    public void setExplosives(Explosives explosives){
        this.explosives = explosives;
    }

    public void setRecommend(Recommend recommend){
        this.recommend = recommend;
    }


    /**
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    private OnshelfStatus onshelfStatus;

    public void setOnshelfStatus(OnshelfStatus onshelfStatus){

        this.onshelfStatus = onshelfStatus;

    }




    /**
     * 视频
     */
    private Video video;

    public void setVideo(Video video){

        this.video = video;

    }


    /**
     * 商品code
     */
    private SpuCode spuCode;

    /**
     * 商品来源，0：ffcat
     */
    private Origin origin;

    /**
     * 外部id
     */
    private OutId outid;

    /**
     * 商品类型，0：普通商品
     */
    private Type type;

    /**
     * 是否包邮 0不包邮，1包邮
     */
    private FreeShipping freeShipping;

    /**
     * 运费
     */
    private Freight freight;

    /**
     * 最低起购量
     */
    private PackingLowestBuy packingLowestBuy;

    /**
     * 推广
     */
    private Popularization popularization;

    /**
     * 分销
     */
    private Distribution distribution;

    /**
     * 秒杀
     */
    private Seckill seckill;

    /**
     * 排序
     */
    private Sort sort;



    public Product(ProductId id, TenantId tenantId, Name name){
        init();
        setId(id);
        setTenantId(tenantId);
        setName(name);
        this.type=new Type(0);
    }

    private void init(){
        this.sort=new Sort(99);
    }

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
            throw new IllegalArgumentException("上架失败，状态异常");
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
            throw new IllegalArgumentException("下架失败，状态异常");
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

//    public void addAttributes(ProductAttributeEntity attribute){
//        if(attributes!=null){
//            attributes.add(attribute);
//        }
//    }


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

    public Recommend getRecommend() {
        return recommend;
    }


    public OnshelfStatus getOnshelfStatus() {
        return onshelfStatus;
    }


    public Video getVideo() {
        return video;
    }


    public SpuCode getSpuCode() {
        return spuCode;
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

    public FreeShipping getFreeShipping() {
        return freeShipping;
    }

    public Freight getFreight() {
        return freight;
    }

    public PackingLowestBuy getLowestBuy() {
        return packingLowestBuy;
    }

    public Popularization getPopularization() {
        return popularization;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public Seckill getSeckill() {
        return seckill;
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


    public List<ProductImage> getProductImages() {
        return null;
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
}
