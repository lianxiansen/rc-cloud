package com.rc.cloud.app.operate.domain.product.valobj;

import com.rc.cloud.app.operate.domain.common.ValueObject;

/**
 * @ClassName:
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description:
 */
public class Video extends ValueObject {
    private String videoUrl;
    private String videoImg;
    private String installVideoUrl;
    private String installVideoImg;
    public Video(String videoUrl, String videoImg, String installVideoUrl, String installVideoImg){
        this.videoUrl = videoUrl;
        this.videoImg = videoImg;
        this.installVideoUrl=installVideoUrl;
        this.installVideoImg = installVideoImg;
    }


}
