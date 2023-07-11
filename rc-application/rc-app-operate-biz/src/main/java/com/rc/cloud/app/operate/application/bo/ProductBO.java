package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttributeEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductDictEntity;
import com.rc.cloud.app.operate.domain.model.product.ProductImageEntity;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.*;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.persistence.po.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductBO {


    private String Id;

    private String tenantId;

    private String brandId;

    private String name;

    private String remark;

    private String tag;

    private Integer productType;

    private String spuCode;

    private String masterImage;

    private String productOrigin;

    private String outId;

    private Boolean enabledFlag;

    private Integer onshelfStatus;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String videoUrl;

    private String videoImg;

    private String installVideoUrl;

    private String installVideoImg;

    private Boolean freeShippingFlag;

    private Integer freightType;

    private Long freightTemplateId;

    private BigDecimal freightPrice;

    private Integer lowestBuy;

    private BigDecimal popularizationAmountRate;

    private BigDecimal getIntegral;

    private Boolean distributionFlag;

    private Boolean refundFlag;

    private Boolean seckillFlag;
    
    private String seckillName;

    private String seckillMasterImage;

    private LocalDateTime seckillBeginTime;

    private LocalDateTime seckillEndTime;

    private Boolean newFlag;

    private Boolean explosivesFlag;

    private String explosivesImage;

    private Boolean publicFlag;

    private Boolean recommendFlag;

    private int sort;

    private String customClassificationId;

    private String detail;


    private List<AttributeBO> attributes;


    private List<ProductSkuBO> skus;
    
    
    private List<ProductImageBO> images;


    private List<ProductDictBO> dicts;

    public static ProductBO convert2ProductBO(Product product) {
        ProductBO target=new ProductBO();

        target.setId(product.getId().id());
        target.setTenantId(product.getTenantId().id());
        if(product.getEnable()!=null){
            target.setEnabledFlag(product.getEnable().result());
        }
        if(product.getName()!=null){
            target.setName(product.getName().getValue());
        }
        if(product.getRemark()!=null){
            target.setRemark(product.getRemark().getValue());
        }
        if(product.getTag()!=null){
            target.setTag(product.getTag().getValue());
        }
        if(product.getBrandId()!=null){
            target.setBrandId(product.getBrandId().id());
        }
        //类别
        if(product.getFirstCategory()!=null){
            target.setFirstCategory(product.getFirstCategory().getValue());
        }
        if(product.getSecondCategory()!=null){
            target.setSecondCategory(product.getSecondCategory().getValue());
        }
        if(product.getThirdCategory()!=null){
            target.setThirdCategory(product.getThirdCategory().getValue());
        }
        //自定义分类
        if(product.getCustomClassificationId()!=null){
            target.setCustomClassificationId(product.getCustomClassificationId().id());
        }
        if(product.getNewest()!=null){
            target.setNewFlag(product.getNewest().getValue());
        }
        if(product.getRecommend()!=null){
            target.setRecommendFlag(product.getRecommend().getValue());
        }
        if(product.getExplosives()!=null){
            target.setExplosivesFlag(product.getExplosives().isFlag());
            target.setExplosivesImage(product.getExplosives().getImage());
        }
        if(product.getOpen()!=null){
            target.setPublicFlag(product.getOpen().getValue());
        }
        //上架状态
        if(product.getOnshelfStatus()!=null){
            target.setOnshelfStatus(product.getOnshelfStatus().getValue());
        }
        //启用状态
        if(product.getEnable()!=null){
            target.setEnabledFlag(product.getEnable().result());
        }
        if(product.getVideo()!=null){
            target.setVideoImg(product.getVideo().getVideoImg());
            target.setVideoUrl(product.getVideo().getVideoUrl());
            target.setInstallVideoImg(product.getVideo().getInstallVideoImg());
            target.setInstallVideoUrl(product.getVideo().getInstallVideoUrl());
        }
        //详情
        if(product.getDetail()!=null){
            target.setDetail(product.getDetail().getValue());
        }
        //属性
        target.setAttributes(convert2AttributeBO(product.getProductAttributeEntity()));
        //图片
        target.setImages(convert2ProductImageBO(product.getProductImages()));
        //字典
        target.setDicts(convert2ProductDictBO(product.getProductDicts()));



        return target;
    }



    public static List<AttributeBO> convert2AttributeBO(ProductAttributeEntity productAttributeEntity){
        String attr = JSON.toJSONString(productAttributeEntity.getAttributes());
        List<AttributeBO> arr= JSON.parseArray(attr, AttributeBO.class);
        return arr;
    }


    public static List<ProductImageBO> convert2ProductImageBO(List<ProductImageEntity> productImageEntityList){
        List<ProductImageBO> resList =new ArrayList<>();
        if(productImageEntityList!=null){
            for (ProductImageEntity item : productImageEntityList) {
                ProductImageBO bo=new ProductImageBO();
                bo.setUrl(item.getUrl());
                bo.setSort(item.getSort());
                resList.add(bo);
            }
        }
        return resList;
    }


    public static List<ProductDictBO> convert2ProductDictBO(List<ProductDictEntity> productDictEntityList){
        List<ProductDictBO> resList =new ArrayList<>();
        if(productDictEntityList!=null){
            for (ProductDictEntity item : productDictEntityList) {
                ProductDictBO bo=new ProductDictBO();
                bo.setKey(item.getKey());
                bo.setValue(item.getValue());
                bo.setSort(item.getSort());
                resList.add(bo);
            }
        }
        return resList;
    }



}
