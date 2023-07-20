package com.rc.cloud.app.operate.appearance.admin.res.convert;

import com.rc.cloud.app.operate.appearance.admin.res.ProductListResponse;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {

    public static ProductListResponse convert(ProductBO bo) {
        ProductListResponse response=new ProductListResponse();
        response.setId(bo.getId());
        response.setName(bo.getName());
        response.setNewFlag(bo.isNewFlag());
        response.setBrandName(bo.getName());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        response.setPublicFlag(bo.isPublicFlag());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setCategoryName(bo.getFirstCategory()+"-"+bo.getSecondCategory()
        +"-"+bo.getThirdCategory());
        return response;
    }



}