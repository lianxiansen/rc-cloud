package com.rc.cloud.app.operate.appearance.request;

import lombok.Data;

import java.util.List;

@Data
public class SecKillProductListVO extends PageVO {

    private List<Integer> productIds;

    private Integer isDuring;

    private Integer activityId;

    private Integer productStatus;

    private String keywords;

    private Integer secKillProductId;

    private List<Integer> ids;

    private Integer secKillActivityId;
}
