package com.rc.cloud.app.operate.domain.model.product.valobj;

import com.rc.cloud.common.core.domain.ValueObject;
import com.rc.cloud.common.core.util.AssertUtils;
import com.rc.cloud.common.core.util.StringUtils;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
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
        AssertUtils.assertArgumentNotNull(videoUrl, "videoUrl must not be null");
        this.videoUrl = videoUrl;
    }

    public void setVideoImg(Url videoImg) {
        AssertUtils.assertArgumentNotNull(videoImg, "videoImg must not be null");
        this.videoImg = videoImg;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}
