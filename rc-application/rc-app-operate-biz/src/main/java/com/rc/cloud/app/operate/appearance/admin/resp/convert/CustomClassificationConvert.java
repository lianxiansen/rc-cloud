package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.CustomClassificationResponse;
import com.rc.cloud.app.operate.application.bo.CustomClassificationBO;

public class CustomClassificationConvert {

    public  static CustomClassificationResponse convertToCustomClassificationResponse(CustomClassificationBO bo){
        return new CustomClassificationResponse();
    }
}
