package com.rc.cloud.app.operate.domain.model.productdetail;

import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;


public class ProductDetail extends Entity
{

    private TenantId tenantId;
    private ProductId productId;
    private Detail detail;
    private Url installVideoUrl;
    private Url installVideoImg;
    private Detail installDetail;

    public ProductDetail(TenantId tenantId, ProductId productId, Detail detail, Url installVideoUrl, Url installVideoImg, Detail installDetail) {
        this.tenantId = tenantId;
        this.productId = productId;
        this.detail = detail;
        this.installVideoUrl = installVideoUrl;
        this.installVideoImg = installVideoImg;
        this.installDetail = installDetail;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public ProductId getProductId() {
        return productId;
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
    public AbstractId getId() {
        return null;
    }
}
