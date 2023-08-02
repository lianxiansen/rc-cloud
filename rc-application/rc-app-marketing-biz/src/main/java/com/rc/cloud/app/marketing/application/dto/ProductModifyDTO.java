package com.rc.cloud.app.marketing.application.dto;

import lombok.Data;

@Data
public class ProductModifyDTO {


    public static final String RECOMMEND="RECOMMEND";
    public static final String PUBLIC="PUBLIC";
    public static final String NEW="NEW";
    public static final String ENABLED="ENABLED";
    public static final String ONSHELF="ONSHELF";

    /**
     *
     RECOMMEND
     PUBLIC
     NEW
     ENABLED
     ONSHELF
     */

    public String productId;

    public String modifyField;

    public String modifyValue;



}
