package com.rc.cloud.app.operate.application.bo;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
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

    private boolean freeShippingFlag;

    private int freightType;

    private String freightTemplateId;

    private BigDecimal freightPrice;

    private int lowestBuy;

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

    private List<ProductImageBO> images;

    private List<ProductDictBO> dicts;

}
