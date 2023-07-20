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
    private String videoUrl;
    private String videoImg;

    public Video(String videoUrl, String videoImg, String installVideoUrl, String installVideoImg){
        if(StringUtils.isNotEmpty(videoUrl)&&!StringUtils.ishttp(videoUrl)){
            throw new IllegalArgumentException("http地址无效");
        }
        if(StringUtils.isNotEmpty(videoImg)&&!StringUtils.ishttp(videoImg)){
            throw new IllegalArgumentException("http地址无效");
        }
        if(StringUtils.isNotEmpty(installVideoUrl)&&!StringUtils.ishttp(installVideoUrl)){
            throw new IllegalArgumentException("http地址无效");
        }
        if(StringUtils.isNotEmpty(installVideoImg)&&!StringUtils.ishttp(installVideoImg)){
            throw new IllegalArgumentException("http地址无效");
        }
        this.videoUrl = videoUrl;
        this.videoImg = videoImg;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoImg() {
        return videoImg;
    }


    @Override
    public boolean equals(Object other) {
        return false;
    }
}
