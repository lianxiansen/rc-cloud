package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    private int productOrigin;

    private String outId;

    private int onshelfStatus;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String videoUrl;

    private String videoImg;

    private boolean packingLowestBuyFlag;

    private boolean newFlag;

    private boolean explosivesFlag;

    private String explosivesImage;

    private boolean publicFlag;

    private boolean recommendFlag;

    private boolean recycleFlag;

    private int sort;

    private String customClassificationId;


    private String detail;
    private String installVideoUrl;
    private String installVideoImg;
    private String installDetail;


    private List<AttributeBO> attributes;


    private List<ProductSkuBO> skus;

    private List<ProductImageBO> masterImages;

    private List<ProductImageBO> sizeImages;

    private Map<String,String> dicts;

    private String productListImage;

    private LocalDateTime createTime;

}
