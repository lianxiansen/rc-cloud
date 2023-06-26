package com.rc.cloud.app.product.domain.product;

import com.rc.cloud.app.product.domain.common.DomainEventPublisher;
import com.rc.cloud.app.product.domain.common.Entity;
import com.rc.cloud.app.product.domain.product.event.ProductCreatedEvent;
import com.rc.cloud.app.product.domain.product.valobj.*;
import com.rc.cloud.app.product.domain.tenant.valobj.TenantId;

import java.util.List;

/**
 * @ClassName: ProductEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 商品
 */
public class ProductEntity extends Entity {
    private Id id;
    /**
     * 所属租户
     */
    private TenantId tenantId;
    /**
     * 商品名
     */
    private Name name;
    /**
     * 商品简介
     */
    private Remark remark;

    /**
     * 商品标签
     */
    private Tag tag;

    /**
     * 主图
     */
    private MasterImage masterImage;

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
     * 状态 1-正常状态，0-未启用
     */
    private Enable enable;

    /**
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    private OnshelfStatus onshelfStatus;

    /**
     * 品牌ID
     */
    private BrandId brandId;

    /**
     * 1级类目
     */
    private FirstCategory firstCategory;

    /**
     * 2级类目
     */
    private SecondCategory secondCategory;

    /**
     * 3级类目
     */
    private ThirdCategory thirdCategory;

    /**
     * 视频
     */
    private Video video;

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
     * 新品
     */
    private Newest newest;

    /**
     * 超级单品
     */
    private Explosives explosives;

    /**
     * 公开
     */
    private Open open;

    /**
     * 推荐
     */
    private Recommend recommend;

    /**
     * 排序
     */
    private Sort sort;

    private List<ProductImageEntity> productImages;
    protected ProductEntity(Id id, Type productType){
        this.id = id;
        this.type =productType;
        DomainEventPublisher.instance().publish(new ProductCreatedEvent(tenantId, "test"));
    }

    public void setProductImages(List<ProductImageEntity> list){
        this.productImages = list;
    }

    public Id getId(){
        return id;
    }

    public void disable(){
        this.enable =new Enable(false);
    }
    public void enable(){
        this.enable =new Enable(true);
    }

    public boolean isEnable(){
        return this.enable.result();
    }

    public void setProductName(Name productName){
        this.name = productName;
    }

    public void setProductRemark(Remark productRemark){
        this.remark = productRemark;
    }

    public void addProductImage(ProductImageEntity productImageEntry){
        productImages.add(productImageEntry);
    }
}
