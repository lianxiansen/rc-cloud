package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.common.core.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductListQueryDTO extends PageParam {



    private String name;

    private String spuCode;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String productIds;

    private Integer onshelfStatus;



    private boolean needBrandName;


}
