package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Url;
import com.rc.cloud.common.core.domain.Entity;

/**
 * 一对一，商品Id和租户Id联合
 */
public class ProductDetail extends Entity
{
    private ProductDetailId id;
    private Detail detail;
    private Url installVideoUrl;
    private Url installVideoImg;
    private Detail installDetail;

    public ProductDetail(ProductDetailId id) {
        this.id = id;
    }

    public Detail getDetail() {
        return detail;
    }

    public Url getInstallVideoUrl() {
        return installVideoUrl;
    }

    public Url getInstallVideoImg() {
        return installVideoImg;
    }

    public Detail getInstallDetail() {
        return installDetail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public void setInstallVideoUrl(Url installVideoUrl) {
        this.installVideoUrl = installVideoUrl;
    }

    public void setInstallVideoImg(Url installVideoImg) {
        this.installVideoImg = installVideoImg;
    }

    public void setInstallDetail(Detail installDetail) {
        this.installDetail = installDetail;
    }

    @Override
    public ProductDetailId getId() {
        return id;
    }
}
