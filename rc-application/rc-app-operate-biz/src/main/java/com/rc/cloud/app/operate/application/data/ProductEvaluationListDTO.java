package com.rc.cloud.app.operate.application.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductEvaluationListDTO {

    private Integer Id;

    private Timestamp createTime;

    private Integer merchantID;

    private Integer orderID;

    private Integer orderItemID;

    private Integer productID;

    private Integer userID;

    private String headImageUrl;

    private String nickName;

    private String content;

    private String reply;

    private Integer uses;

    private String albums;

    private Integer descriptionTally;

    private Integer serviceAttitude;

    private Integer deliverySpeed;

    private Integer reputation;

    private String itemSpecification;

    private Integer mCNProductID;

    private String brandCommunityProductID;

    private String brandCommunityBrandName;

    private Integer orderType;

    private Boolean isFromAlibaba;

    private Boolean isFromWeApplet;

    private Integer isSystem;

    private Boolean isTop;

    private Integer albumsOrderBy;
}
