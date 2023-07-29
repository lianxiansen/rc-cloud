package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import lombok.Data;

@Data
public class ProductDetailBO {

    private String detail;
    private String installVideoUrl;
    private String installVideoImg;
    private String installDetail;
}
