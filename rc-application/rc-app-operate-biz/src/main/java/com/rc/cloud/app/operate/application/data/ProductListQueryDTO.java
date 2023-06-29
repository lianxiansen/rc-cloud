package com.rc.cloud.app.operate.application.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class ProductListQueryDTO {


    private int pageNo;

    private int pageSize;

    private Long categoryId;

    private String startModifyTime;

    private String endModifyTime;

    private String productIds;

    /**
     * 排序条件：CREATE_DATE,POST_DATE,MODIFY_DATE,APPROVED_DATE,EXPIRE_DATE,STATUS,ID,GROUP_ID,PRICE,SALE_QUANTITY
     */
    private String orderByCondition;


    private Boolean needProductImages;

    private Boolean needProductDetail;


}
