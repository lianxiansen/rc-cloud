package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductBO {


    private String id;

    private String tenantId;

    private String brandId;

    private String name;

    private String brandName;

    private String remark;

    private String tag;

    private int productType;

    private String spuCode;

    private String masterImage;

    private int productOrigin;

    private String outId;

    private boolean enabledFlag;

    private int onshelfStatus;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String videoUrl;

    private String videoImg;

    private String installVideoUrl;

    private String installVideoImg;

    private String installDetail;

    private boolean freeShippingFlag;

    private int freightType;

    private String freightTemplateId;

    private BigDecimal freightPrice;

    private boolean packingLowestBuyFlag;

    private BigDecimal popularizationAmountRate;

    private BigDecimal getIntegral;

    private boolean distributionFlag;

    private boolean refundFlag;

    private boolean seckillFlag;

    private String seckillName;

    private String seckillMasterImage;

    private LocalDateTime seckillBeginTime;

    private LocalDateTime seckillEndTime;

    private boolean newFlag;

    private boolean explosivesFlag;

    private String explosivesImage;

    private boolean publicFlag;

    private boolean recommendFlag;

    private int sort;

    private String customClassificationId;

    private String detail;


    private List<AttributeBO> attributes;


    private List<ProductSkuBO> skus;

    private List<ProductImageBO> masterImages;

    private List<ProductImageBO> sizeImages;

    private List<ProductDictBO> dicts;

    private String productListImage;

    private LocalDateTime createTime;

}
