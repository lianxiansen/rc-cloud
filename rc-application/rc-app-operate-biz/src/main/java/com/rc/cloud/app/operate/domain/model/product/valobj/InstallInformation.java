package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.util.StringUtils;

/**
 * 安装信息
 */
public class InstallInformation {

    private Url installVideoUrl;
    private Url installVideoImg;
    private String installDetail;

    public Url getInstallVideoUrl() {
        return installVideoUrl;
    }

    public void setInstallVideoUrl(Url installVideoUrl) {
        this.installVideoUrl = installVideoUrl;
    }

    public Url getInstallVideoImg() {
        return installVideoImg;
    }

    public void setInstallVideoImg(Url installVideoImg) {
        this.installVideoImg = installVideoImg;
    }

    public String getInstallDetail() {
        return installDetail;
    }

    public void setInstallDetail(String installDetail) {
        this.installDetail = installDetail;
    }


}
