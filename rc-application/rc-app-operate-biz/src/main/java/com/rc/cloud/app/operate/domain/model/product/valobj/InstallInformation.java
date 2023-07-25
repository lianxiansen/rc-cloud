package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * 安装信息
 */
public class InstallInformation  extends Entity {

    private Url installVideoUrl;
    private Url installVideoImg;
    private String installDetail;

    public Url getInstallVideoUrl() {
        return installVideoUrl;
    }

    public void setInstallVideoUrl(Url installVideoUrl) {
        AssertUtils.assertArgumentNotNull(installVideoUrl,"installVideoUrl must not be null");
        this.installVideoUrl = installVideoUrl;
    }

    public Url getInstallVideoImg() {
        return installVideoImg;
    }

    public void setInstallVideoImg(Url installVideoImg) {
        AssertUtils.assertArgumentNotNull(installVideoImg,"installVideoImg must not be null");
        this.installVideoImg = installVideoImg;
    }

    public String getInstallDetail() {
        return installDetail;
    }

    public void setInstallDetail(String installDetail) {
        this.installDetail = installDetail;
    }


    @Override
    public AbstractId getId() {
        return null;
    }
}
