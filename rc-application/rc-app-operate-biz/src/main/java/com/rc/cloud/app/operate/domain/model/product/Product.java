package com.rc.cloud.app.operate.domain.model.product;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.common.Entity;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductEntry
 * @Author: liandy
 * @Date: 2023/6/23 13:09
 * @Description: 产品
 */
public class Product extends Entity {

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

    /**
     * 如果只有两
     * @param firstCategory
     * @param secondCategory
     * @param thirdCategory
     */
    public Product setCategory(CategoryName firstCategory, CategoryName secondCategory,CategoryName thirdCategory){
        this.assertArgumentNotNull(firstCategory, "firstCategory must not be null");
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
        this.assertArgumentNotNull(remark, "remark must not be null");
        this.remark= remark;
        return this;
    }

    /**
     * 产品标签
     * 多个标签可以用逗号分割
     */
    private Tag tag;

    public Product setTag(Tag tag){
        this.assertArgumentNotNull(tag, "tag must not be null");
        this.tag = tag;
        return this;
    }
    /**
     * 品牌ID
     */
    private BrandId brandId;


    public Product setBrandId(BrandId brandId){
        this.assertArgumentNotNull(brandId, "brandId must not be null");
        this.brandId = brandId;
        return this;
    }

    /**
     * 自定义标识
     */
    private CustomClassification customClassification;



    public Product setCustomClassification(CustomClassification customClassification){
        this.assertArgumentNotNull(customClassification, "customClassification must not be null");
        this.customClassification = customClassification;
        return this;
    }


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
     * 上架状态 0-上架初始，1-上架中，2-下架中
     */
    private OnshelfStatus onshelfStatus;

    public void setOnshelfStatus(OnshelfStatus onshelfStatus){

        this.onshelfStatus = onshelfStatus;

    }



    /**
     * 状态 1-正常状态，0-未启用
     */
    private Enable enable;

    public void setEnable(Enable enable){
        this.enable = enable;
    }


    /**
     * 视频
     */
    private Video video;

    public void setVideo(Video video){

        this.video = video;

    }


    /**
     * 产品相册
     */
    private MasterImage masterImage;

    private List<ProductImageEntity> productImages;

    /**
     * 添加相册
     * @param urls 图片地址
     * @return
     */
    public Product setProductImage(List<String> urls){
        if(productImages==null || productImages.size()<= 0){
            throw new IllegalArgumentException("productImages must not be null");
        }
        this.customClassification = customClassification;
        this.masterImage= new MasterImage(urls.get(0));
        int pos=1;
        productImages =new ArrayList<>();
//        for (String url : urls) {
//            ProductImageEntity entity=new ProductImageEntity(this.getId());
//            entity.setUrl(url)
//                    .setDefaultFlag(pos==1?true:false)
//                    .setSort(pos);
//            pos++;
//            productImages.add(entity);
//        }
        return this;
    }



    /**
     * K-V
     */
    private List<ProductDictEntity> productDicts;

    public Product setProductDict(List<ProductDictEntity> productDicts){
        if(productDicts==null || productDicts.size()<= 0){
            throw new IllegalArgumentException("productDicts must not be null");
        }
        this.productDicts=productDicts;
        return this;
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

    public Product setDetail(Detail detail) {
        this.assertArgumentNotNull(detail, "detail must not be null");
        this.detail = detail;
        return this;
    }



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



}
