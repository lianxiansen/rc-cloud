package com.rc.cloud.app.operate.appearance.admin.res.convert;

import com.rc.cloud.app.operate.appearance.admin.res.ProductListResponse;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {

    public static ProductListResponse convert(ProductBO bo) {
        ProductListResponse response=new ProductListResponse();
        response.setId(bo.getId());
        response.setName(bo.getName());
        response.setListImage(bo.getProductListImage());
        response.setNewFlag(bo.isNewFlag());
        response.setBrandName(bo.getName());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        response.setPublicFlag(bo.isPublicFlag());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setCategoryName(format(bo.getFirstCategory(), bo.getSecondCategory(), bo.getThirdCategory()));
        return response;
    }


    public static String format(String firstCategory,String secondCategory, String thirdCategory){
        String s=firstCategory+"-"+secondCategory;
        if(StringUtils.isNotEmpty(thirdCategory)){
            s+= "-"+thirdCategory;
        }
        return s;
    }


}
