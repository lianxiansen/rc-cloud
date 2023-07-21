package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.util.StringUtils;

/**
 * 安装信息
 */
public class InstallInformation {

    private String installVideoUrl;
    private String installVideoImg;
    private String installDetail;

    public String getInstallVideoUrl() {
        return installVideoUrl;
    }

    public String getInstallVideoImg() {
        return installVideoImg;
    }

    public void setInstallVideoUrl(String installVideoUrl) {
        if(StringUtils.isNotEmpty(installVideoUrl)&&!StringUtils.ishttp(installVideoUrl)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.installVideoUrl = installVideoUrl;
    }

    public void setInstallVideoImg(String installVideoImg) {
        if(StringUtils.isNotEmpty(installVideoImg)&&!StringUtils.ishttp(installVideoImg)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.installVideoImg = installVideoImg;
    }

    public String getInstallDetail() {
        return installDetail;
    }

    public void setInstallDetail(String installDetail) {
        this.installDetail = installDetail;
    }


}
