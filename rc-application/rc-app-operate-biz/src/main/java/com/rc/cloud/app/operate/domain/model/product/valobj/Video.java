package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @ClassName:
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Video extends ValueObject {
    private Url videoUrl;
    private Url videoImg;

    public Video(Url videoUrl){
        this.videoUrl = videoUrl;
    }

    public Url getVideoUrl() {
        return videoUrl;
    }

    public Url getVideoImg() {
        return videoImg;
    }

    public void setVideoUrl(Url videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoImg(Url videoImg) {
        this.videoImg = videoImg;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
