package com.rc.cloud.app.operate.application.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rc.cloud.common.core.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductListQueryDTO extends PageParam {


    private String name;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;
    //租户id
    private Long tenantId;



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
