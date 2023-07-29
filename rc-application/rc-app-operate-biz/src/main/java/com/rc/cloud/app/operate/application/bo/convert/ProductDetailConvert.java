package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductDetailBO;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;

public class ProductDetailConvert {


    public static ProductDetail convertDomain(ProductId productId, TenantId tenantId
            , Detail detail, Url installVideoUrl, Url installVideoImg, Detail installDetail){
        ProductDetail productDetail=

                new ProductDetail(
                        tenantId, productId,detail,installVideoUrl,installVideoImg,installDetail
                );

        return productDetail;
    }

    public static ProductDetailBO convertProductDetailBO(ProductDetail productDetail){
        ProductDetailBO bo =new ProductDetailBO();
        bo.setDetail(productDetail.getDetail().getValue());
        bo.setInstallDetail(productDetail.getInstallDetail().getValue());
        bo.setInstallVideoImg(productDetail.getInstallVideoImg().getValue());
        bo.setInstallVideoUrl(productDetail.getInstallVideoUrl().getValue());
        return bo;
    }

}
