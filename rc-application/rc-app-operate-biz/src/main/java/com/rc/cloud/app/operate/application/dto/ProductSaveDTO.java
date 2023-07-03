package com.rc.cloud.app.operate.application.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSaveDTO {

    private int id;

    private int productType;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private int brandId;

    private String name;

    private String remark;

    private String tag;


    @Valid
    private List<ProductImageSaveDTO> albums;

    /**
     * 存放
     * 材质 石头
     * 尺寸 xx
     * 承重 xx
     * 等一些K-V参数
     */
    private List<ProductDictSaveDTO> dicts;

    private String price;

    private String popularizationAmountRate;

    private int lowestBuy;

    private int getIntegral;

    private boolean  newFlag;

    private boolean  explosivesFlag;

    private boolean  publicFlag;

    private boolean  recommendFlag;

    private int tenantId;

    private int sortId;

    private List<ProductAttributeSaveDTO> attributes;

    private List<ProductSkuSaveDTO> skus;


}
