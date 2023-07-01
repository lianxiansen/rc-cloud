package com.rc.cloud.app.operate.domain.product;

import com.rc.cloud.app.operate.domain.product.valobj.*;
import com.rc.cloud.app.operate.domain.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.app.operate.domain.common.DomainEventPublisher;
import com.rc.cloud.app.operate.domain.common.Entity;

import com.rc.cloud.app.operate.domain.product.event.ProductCreatedEvent;
import com.rc.cloud.app.operate.domain.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.tenant.valobj.TenantId;

import java.util.List;

/**
 * @ClassName: ProductEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 产品
 */
public class ProductAggregation extends Entity {

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
    private ProductCategoryId productCategoryId;

    /**
     * 产品简介
     */
    private Remark remark;

    /**
     * 产品标签
     */
    private Tag tag;

    /**
     * 品牌ID
     */
    private BrandId brandId;

    /**
     * 自定义标识
     */
    private CustomClassification customClassification;

    /**
     * 新品
     */
    private Newest newest;

    /**
     * 超级单品
     */
    private Explosives explosives;

    /**
     * 推荐
     */
    private Recommend recommend;

    /**
     * 公开
     */
    private Open open;

    /**
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    private OnshelfStatus onshelfStatus;

    /**
     * 状态 1-正常状态，0-未启用
     */
    private Enable enable;

    /**
     * 视频
     */
    private Video video;

    /**
     * 主图
     */
    private MasterImage masterImage;

    /**
     * 产品相册
     */
    private List<ProductImageEntity> productImages;

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
    private LowestBuy lowestBuy;

    /**
     * 推广
     */
    private Popularization popularization;

    /**
     * 分销
     */
    private Distribution distribution;

    /**
     * 退款
     */
    private Refund refund;

    /**
     * 秒杀
     */
    private Seckill seckill;

    /**
     * 排序
     */
    private Sort sort;

    private Detail detail;



    protected ProductAggregation(ProductId id, TenantId tenantId, Name name, ProductCategoryId productCategoryId){
        setId(id);
        setTenantId(tenantId);
        setName(name);
        setProductCategoryId(productCategoryId);
        DomainEventPublisher.instance().publish(new ProductCreatedEvent(tenantId, "test"));
    }

    public void setId(ProductId id){
        this.assertArgumentNotNull(id,"ProductId must not be null");
        this.id=id;
    }
    public void setTenantId(TenantId tenantId){
        this.assertArgumentNotNull(tenantId,"TenantId must not be null");
        this.tenantId = tenantId;
    }

    public void setName(Name name){
        this.assertArgumentNotNull(name,"Name must not be null");
        this.name = name;
    }

    public Name getName(){
        return this.name;
    }


    public void setProductCategoryId(ProductCategoryId productCategoryId){
        this.assertArgumentNotNull(productCategoryId,"ProductCategoryId must not be null");
        this.productCategoryId = productCategoryId;
    }
    public void setRemark(Remark remark){
        this.remark = remark;
    }


    public void setTag(Tag tag){
        this.tag = tag;
    }

    public void setBrandId(BrandId brandId){
        this.brandId = brandId;
    }

    public void setCustomClassification(CustomClassification customClassification){
        this.customClassification = customClassification;
    }

    public void setNewest(Newest newest){
        this.newest = newest;
    }

    public void setExplosives(Explosives explosives){
        this.explosives = explosives;
    }

    public void setRecommend(Recommend recommend){
        this.recommend = recommend;
    }

    public void setOpen(Open open){
        this.open = open;
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

    /**
     * 禁用
     */
    public void disable(){
        this.enable =new Enable(false);
    }

    /**
     * 启用
     */
    public void enable(){
        this.enable =new Enable(true);
    }

    public void setVideo(Video video){
        this.video = video;
    }

    public void setMasterImage(MasterImage masterImage){
        this.masterImage = masterImage;
    }


    public void setType(Type type){
        this.assertArgumentNotNull(type,"Type must not be null");
        this.type = type;
    }

    public void setProductImages(List<ProductImageEntity> list){
        this.productImages = list;
    }

    public ProductId getId(){
        return id;
    }



    public boolean isEnable(){
        return this.enable.result();
    }
    public boolean isNotEnable(){
        return !this.enable.result();
    }


    public void addProductImage(ProductImageEntity productImageEntry){
        productImages.add(productImageEntry);
    }
}
