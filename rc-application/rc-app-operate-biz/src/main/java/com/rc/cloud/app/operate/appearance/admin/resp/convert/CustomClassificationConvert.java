package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.CustomClassificationResponse;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;
import com.rc.cloud.common.core.util.date.LocalDateTimeUtils;

public class CustomClassificationConvert {

    public  static CustomClassificationResponse convertToCustomClassificationResponse(CustomClassificationBO bo){
        if(bo==null){
            return null;
        }
        CustomClassificationResponse response= new CustomClassificationResponse();
        if(bo.getCreateTime()!=null){
            response.setCreateTime(LocalDateTimeUtils.format(bo.getCreateTime()));
        }
        response.setId(bo.getId());
        response.setCustomClassificationPoster(bo.getCustomClassificationPoster());
        response.setCustomClassificationImage(bo.getCustomClassificationImage());
        response.setProductPoster(bo.getProductPoster());
        response.setName(bo.getName());
        response.setEnabled(bo.isEnabled());
        response.setParentId(bo.getParentId());
        response.setSort(bo.getSort());

        return  response;
    }
}
