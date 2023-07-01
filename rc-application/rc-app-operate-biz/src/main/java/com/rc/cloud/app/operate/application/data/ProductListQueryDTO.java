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
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductListQueryDTO extends PageParam {



    public static final String ASC  = "ASC";
    public static final String DESC = "DESC";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String SORT_ID = "SORT_ID";

    private String name;

    private String spuCode;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;
    //租户id
    private Long tenantId;

    private Long categoryId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String productIds;

    private Long BrandId;

    private Integer onshelfStatus;

    /**
     * 排序条件：CREATE_TIME,SORT_ID
     */
    private String orderByCondition;

    private String orderByType;



    private Boolean needProductImages;

    private Boolean needProductDetail;


}
