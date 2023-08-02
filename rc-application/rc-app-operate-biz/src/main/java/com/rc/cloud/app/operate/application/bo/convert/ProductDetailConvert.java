package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Url;

public class ProductDetailConvert {


    public static ProductDetail convertDomain(ProductId productId,boolean isCreate,  String detail,
                                              String installVideoUrl, String installVideoImg, String installDetail
                                            , ProductDetail productDetail) {
        if(isCreate && productDetail!=null){
            throw new IllegalArgumentException("参数错误，内部错误");
        }
        if(isCreate){
            productDetail =new ProductDetail(new ProductDetailId(productId));
        }

        productDetail = setDetail(detail,isCreate,productDetail);
        productDetail = setInstallVideoUrl(installVideoUrl,isCreate,productDetail);
        productDetail = setInstallVideoImg(installVideoImg,isCreate,productDetail);
        productDetail = setInstallDetail(installDetail,isCreate,productDetail);

        return productDetail;
    }


    private static ProductDetail setDetail(String detail, boolean isCreate, ProductDetail productDetail){
        if(isCreate){
            productDetail.setDetail(new Detail(detail));
        }else{
            if (detail != null) {
                productDetail.setDetail(new Detail(detail));
            }
        }
        return productDetail;
    }

    private static ProductDetail setInstallVideoUrl(String installVideoUrl, boolean isCreate, ProductDetail productDetail){
        if(isCreate){
            productDetail.setInstallVideoUrl(new Url(installVideoUrl));
        }else{
            if (installVideoUrl != null) {
                productDetail.setInstallVideoUrl(new Url(installVideoUrl));
            }
        }
        return productDetail;
    }

    private static ProductDetail setInstallVideoImg(String installVideoImg, boolean isCreate, ProductDetail productDetail){
        if(isCreate){
            productDetail.setInstallVideoImg(new Url(installVideoImg));
        }else{
            if (installVideoImg != null) {
                productDetail.setInstallVideoImg(new Url(installVideoImg));
            }
        }
        return productDetail;
    }


    private static ProductDetail setInstallDetail(String installDetail, boolean isCreate, ProductDetail productDetail){
        if(isCreate){
            productDetail.setInstallDetail(new Detail(installDetail));
        }else{
            if (installDetail != null) {
                productDetail.setInstallDetail(new Detail(installDetail));
            }
        }
        return productDetail;
    }


}
